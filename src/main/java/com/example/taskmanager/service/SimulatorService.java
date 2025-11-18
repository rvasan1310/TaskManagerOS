package com.example.taskmanager.service;

import com.example.taskmanager.model.ProcessInfo;
import com.example.taskmanager.model.SystemStats;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SimulatorService {

    private final Map<Integer, ProcessInfo> processes = new ConcurrentHashMap<>();
    private volatile SystemStats systemStats = new SystemStats(
            0,0,0,0,0,
            16384,0,8,0,0
    );

    private static class ProcSeed {
        final String name; final String type; final int avgCpu; final int avgMem;
        ProcSeed(String name, String type, int avgCpu, int avgMem) {
            this.name=name; this.type=type; this.avgCpu=avgCpu; this.avgMem=avgMem;
        }
    }
    private final List<ProcSeed> seeds = List.of(
            new ProcSeed("chrome.exe","browser",8,800),
            new ProcSeed("code.exe","editor",5,600),
            new ProcSeed("node.exe","runtime",12,400),
            new ProcSeed("python.exe","runtime",10,300),
            new ProcSeed("explorer.exe","system",2,200),
            new ProcSeed("system","kernel",1,100),
            new ProcSeed("svchost.exe","service",3,150),
            new ProcSeed("discord.exe","app",6,500),
            new ProcSeed("spotify.exe","app",4,350),
            new ProcSeed("steam.exe","app",3,400),
            new ProcSeed("firefox.exe","browser",7,700),
            new ProcSeed("docker.exe","container",15,1200),
            new ProcSeed("postgres.exe","database",8,800),
            new ProcSeed("java.exe","runtime",14,1500),
            new ProcSeed("mysql.exe","database",6,600),
            new ProcSeed("nginx.exe","webserver",2,100),
            new ProcSeed("mongod.exe","database",7,900),
            new ProcSeed("redis-server.exe","cache",3,200),
            new ProcSeed("Teams.exe","app",9,700),
            new ProcSeed("Slack.exe","app",5,450)
    );

    public SimulatorService() {
        init(); // avoid @PostConstruct to remove jakarta.annotation dependency
    }

    private double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private double jtr(double min, double max) {
        return min + (max - min) * ThreadLocalRandom.current().nextDouble();
    }

    private void init() {
        int basePid = 1000;
        for (int i = 0; i < seeds.size(); i++) {
            ProcSeed s = seeds.get(i);
            Integer parentPid = (i > 5) ? basePid + ThreadLocalRandom.current().nextInt(5) : null;
            double cpu = clamp(s.avgCpu + jtr(-5, 5), 0, 100);
            double mem = Math.max(0, s.avgMem + jtr(-100, 100));
            long startOffset = Math.abs(ThreadLocalRandom.current().nextLong(10_000_000L));
            ProcessInfo p = new ProcessInfo(
                    basePid + i,
                    parentPid,
                    s.name, s.type,
                    cpu,
                    mem,
                    Math.max(0, ThreadLocalRandom.current().nextDouble() * 50),
                    Math.max(0, ThreadLocalRandom.current().nextDouble() * 1000),
                    Math.max(0, ThreadLocalRandom.current().nextDouble() * 1000),
                    ThreadLocalRandom.current().nextDouble() > 0.1 ? "Running" : "Sleeping",
                    ThreadLocalRandom.current().nextDouble() > 0.7 ? "High" : (ThreadLocalRandom.current().nextDouble() > 0.3 ? "Normal" : "Low"),
                    ThreadLocalRandom.current().nextInt(1, 21),
                    ThreadLocalRandom.current().nextInt(50, 551),
                    List.of("SYSTEM","Administrator","User").get(ThreadLocalRandom.current().nextInt(3)),
                    System.currentTimeMillis() - startOffset,
                    ThreadLocalRandom.current().nextDouble() * 3600
            );
            processes.put(p.getPid(), p);
        }
        recalcSystem();
    }

    @Scheduled(fixedRate = 1000)
    public void tick() {
        for (ProcessInfo p : processes.values()) {
            double cpuSpike = ThreadLocalRandom.current().nextDouble() > 0.95 ? 20.0 : 0.0;
            double newCpu = clamp(p.getCpu() + jtr(-5, 5) + cpuSpike, 0, 100);
            double newMem = Math.max(0, p.getMemory() + jtr(-50, 50));
            p.setCpu(newCpu);
            p.setMemory(newMem);
            p.setDisk(Math.max(0, p.getDisk() + jtr(-20, 20)));
            p.setNetworkIn(Math.max(0, p.getNetworkIn() + jtr(-200, 200)));
            p.setNetworkOut(Math.max(0, p.getNetworkOut() + jtr(-200, 200)));
            p.setCpuTime(p.getCpuTime() + (newCpu / 100.0));
        }
        systemStats.setUptime(systemStats.getUptime() + 1);
        recalcSystem();
    }

    private void recalcSystem() {
        double totalCpu = processes.values().stream().mapToDouble(ProcessInfo::getCpu).average().orElse(0);
        double totalMem = processes.values().stream().mapToDouble(ProcessInfo::getMemory).sum();
        double totalNetIn = processes.values().stream().mapToDouble(ProcessInfo::getNetworkIn).sum();
        double totalNetOut = processes.values().stream().mapToDouble(ProcessInfo::getNetworkOut).sum();
        double totalDisk = processes.values().stream().mapToDouble(ProcessInfo::getDisk).sum();
        int threads = processes.values().stream().mapToInt(ProcessInfo::getThreads).sum();

        systemStats.setCpuUsage(Math.min(100, totalCpu));
        systemStats.setUsedMemory(totalMem);
        systemStats.setMemoryUsage(Math.min(100, (totalMem / systemStats.getTotalMemory()) * 100));
        systemStats.setDiskUsage(Math.min(100, totalDisk / 10.0));
        systemStats.setNetworkIn(totalNetIn / 1024.0);
        systemStats.setNetworkOut(totalNetOut / 1024.0);
        systemStats.setActiveThreads(threads);
    }

    public List<ProcessInfo> getProcesses() {
        return new ArrayList<>(processes.values());
    }

    public SystemStats getSystemStats() {
        return systemStats;
    }

    public boolean killProcess(int pid) {
        ProcessInfo p = processes.get(pid);
        if (p == null) return false;
        if ("system".equalsIgnoreCase(p.getType()) || "kernel".equalsIgnoreCase(p.getType())) {
            return false;
        }
        Set<Integer> toRemove = new HashSet<>();
        toRemove.add(pid);
        for (ProcessInfo pi : processes.values()) {
            if (pi.getParentPid() != null && pi.getParentPid() == pid) {
                toRemove.add(pi.getPid());
            }
        }
        for (Integer id : toRemove) {
            processes.remove(id);
        }
        recalcSystem();
        return true;
    }
}

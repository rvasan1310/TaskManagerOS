package com.example.taskmanager.model;

public class SystemStats {
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private double networkIn;
    private double networkOut;
    private int totalMemory;
    private double usedMemory;
    private int cpuCores;
    private int activeThreads;
    private long uptime;

    public SystemStats() {}

    public SystemStats(double cpuUsage, double memoryUsage, double diskUsage, double networkIn, double networkOut,
                       int totalMemory, double usedMemory, int cpuCores, int activeThreads, long uptime) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.networkIn = networkIn;
        this.networkOut = networkOut;
        this.totalMemory = totalMemory;
        this.usedMemory = usedMemory;
        this.cpuCores = cpuCores;
        this.activeThreads = activeThreads;
        this.uptime = uptime;
    }

    public double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }
    public double getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(double memoryUsage) { this.memoryUsage = memoryUsage; }
    public double getDiskUsage() { return diskUsage; }
    public void setDiskUsage(double diskUsage) { this.diskUsage = diskUsage; }
    public double getNetworkIn() { return networkIn; }
    public void setNetworkIn(double networkIn) { this.networkIn = networkIn; }
    public double getNetworkOut() { return networkOut; }
    public void setNetworkOut(double networkOut) { this.networkOut = networkOut; }
    public int getTotalMemory() { return totalMemory; }
    public void setTotalMemory(int totalMemory) { this.totalMemory = totalMemory; }
    public double getUsedMemory() { return usedMemory; }
    public void setUsedMemory(double usedMemory) { this.usedMemory = usedMemory; }
    public int getCpuCores() { return cpuCores; }
    public void setCpuCores(int cpuCores) { this.cpuCores = cpuCores; }
    public int getActiveThreads() { return activeThreads; }
    public void setActiveThreads(int activeThreads) { this.activeThreads = activeThreads; }
    public long getUptime() { return uptime; }
    public void setUptime(long uptime) { this.uptime = uptime; }
}

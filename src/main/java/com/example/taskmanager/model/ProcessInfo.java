package com.example.taskmanager.model;

public class ProcessInfo {
    private int pid;
    private Integer parentPid;
    private String name;
    private String type;
    private double cpu;
    private double memory;
    private double disk;
    private double networkIn;
    private double networkOut;
    private String status;
    private String priority;
    private int threads;
    private int handles;
    private String user;
    private long startTime;
    private double cpuTime;

    public ProcessInfo() {}

    public ProcessInfo(int pid, Integer parentPid, String name, String type, double cpu, double memory, double disk,
                       double networkIn, double networkOut, String status, String priority, int threads, int handles,
                       String user, long startTime, double cpuTime) {
        this.pid = pid;
        this.parentPid = parentPid;
        this.name = name;
        this.type = type;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.networkIn = networkIn;
        this.networkOut = networkOut;
        this.status = status;
        this.priority = priority;
        this.threads = threads;
        this.handles = handles;
        this.user = user;
        this.startTime = startTime;
        this.cpuTime = cpuTime;
    }

    public int getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }
    public Integer getParentPid() { return parentPid; }
    public void setParentPid(Integer parentPid) { this.parentPid = parentPid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getCpu() { return cpu; }
    public void setCpu(double cpu) { this.cpu = cpu; }
    public double getMemory() { return memory; }
    public void setMemory(double memory) { this.memory = memory; }
    public double getDisk() { return disk; }
    public void setDisk(double disk) { this.disk = disk; }
    public double getNetworkIn() { return networkIn; }
    public void setNetworkIn(double networkIn) { this.networkIn = networkIn; }
    public double getNetworkOut() { return networkOut; }
    public void setNetworkOut(double networkOut) { this.networkOut = networkOut; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public int getThreads() { return threads; }
    public void setThreads(int threads) { this.threads = threads; }
    public int getHandles() { return handles; }
    public void setHandles(int handles) { this.handles = handles; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
    public double getCpuTime() { return cpuTime; }
    public void setCpuTime(double cpuTime) { this.cpuTime = cpuTime; }
}

package model;

public class CPUProcess {
    private String processId;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;
    private int remainingTime;

    public CPUProcess(String processId, int arrivalTime, int burstTime, int priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }

    public CPUProcess(CPUProcess p) {
        this.processId = p.processId;
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.priority = p.priority;
        this.completionTime = 0;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.remainingTime = p.burstTime;
    }

    public String getProcessId() {
        return processId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
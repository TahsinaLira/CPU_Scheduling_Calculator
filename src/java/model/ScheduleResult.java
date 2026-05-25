package model;

import java.util.List;

public class ScheduleResult {
    private String algorithmName;
    private List<CPUProcess> processList;
    private List<GanttChart> ganttChart;
    private double averageWaitingTime;
    private double averageTurnaroundTime;

    public ScheduleResult(String algorithmName,
                          List<CPUProcess> processList,
                          List<GanttChart> ganttChart,
                          double averageWaitingTime,
                          double averageTurnaroundTime) {
        this.algorithmName = algorithmName;
        this.processList = processList;
        this.ganttChart = ganttChart;
        this.averageWaitingTime = averageWaitingTime;
        this.averageTurnaroundTime = averageTurnaroundTime;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public List<CPUProcess> getProcessList() {
        return processList;
    }

    public List<GanttChart> getGanttChart() {
        return ganttChart;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }
}
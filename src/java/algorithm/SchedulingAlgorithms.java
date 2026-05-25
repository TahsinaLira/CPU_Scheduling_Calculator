package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.CPUProcess;
import model.GanttChart;
import model.ScheduleResult;

public class SchedulingAlgorithms {

    private static List<CPUProcess> copyProcessList(List<CPUProcess> originalList) {
        List<CPUProcess> copiedList = new ArrayList<>();

        for (CPUProcess p : originalList) {
            copiedList.add(new CPUProcess(p));
        }

        return copiedList;
    }

    private static ScheduleResult buildResult(String algorithmName,
                                              List<CPUProcess> processList,
                                              List<GanttChart> ganttChart) {
        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (CPUProcess p : processList) {
            totalWaitingTime += p.getWaitingTime();
            totalTurnaroundTime += p.getTurnaroundTime();
        }

        double averageWaitingTime = totalWaitingTime / processList.size();
        double averageTurnaroundTime = totalTurnaroundTime / processList.size();

        return new ScheduleResult(
                algorithmName,
                processList,
                ganttChart,
                averageWaitingTime,
                averageTurnaroundTime
        );
    }

    private static void addGanttBlock(List<GanttChart> ganttChart,
                                      String processId,
                                      int startTime,
                                      int endTime) {
        if (startTime == endTime) {
            return;
        }

        ganttChart.add(new GanttChart(processId, startTime, endTime));
    }

    // 1. First Come First Serve
    public static ScheduleResult fcfs(List<CPUProcess> originalList) {
        List<CPUProcess> processList = copyProcessList(originalList);
        List<GanttChart> ganttChart = new ArrayList<>();

        processList.sort(Comparator.comparingInt(CPUProcess::getArrivalTime));

        int currentTime = 0;

        for (CPUProcess p : processList) {
            if (currentTime < p.getArrivalTime()) {
                addGanttBlock(ganttChart, "Idle", currentTime, p.getArrivalTime());
                currentTime = p.getArrivalTime();
            }

            int startTime = currentTime;
            currentTime += p.getBurstTime();

            int completionTime = currentTime;
            int turnaroundTime = completionTime - p.getArrivalTime();
            int waitingTime = turnaroundTime - p.getBurstTime();

            p.setCompletionTime(completionTime);
            p.setTurnaroundTime(turnaroundTime);
            p.setWaitingTime(waitingTime);

            addGanttBlock(ganttChart, p.getProcessId(), startTime, completionTime);
        }

        return buildResult("FCFS", processList, ganttChart);
    }

    // 2. Shortest Job First - Non-preemptive
    public static ScheduleResult sjfNonPreemptive(List<CPUProcess> originalList) {
        List<CPUProcess> processList = copyProcessList(originalList);
        List<GanttChart> ganttChart = new ArrayList<>();

        int totalProcess = processList.size();
        int completedProcess = 0;
        int currentTime = 0;

        boolean[] completed = new boolean[totalProcess];

        while (completedProcess < totalProcess) {
            int selectedIndex = -1;

            for (int i = 0; i < totalProcess; i++) {
                CPUProcess p = processList.get(i);

                if (!completed[i] && p.getArrivalTime() <= currentTime) {
                    if (selectedIndex == -1) {
                        selectedIndex = i;
                    } else {
                        CPUProcess selected = processList.get(selectedIndex);

                        if (p.getBurstTime() < selected.getBurstTime()) {
                            selectedIndex = i;
                        } else if (p.getBurstTime() == selected.getBurstTime()
                                && p.getArrivalTime() < selected.getArrivalTime()) {
                            selectedIndex = i;
                        }
                    }
                }
            }

            if (selectedIndex == -1) {
                int idleStart = currentTime;
                currentTime++;

                while (true) {
                    boolean processAvailable = false;

                    for (int i = 0; i < totalProcess; i++) {
                        CPUProcess p = processList.get(i);

                        if (!completed[i] && p.getArrivalTime() <= currentTime) {
                            processAvailable = true;
                            break;
                        }
                    }

                    if (processAvailable || completedProcess == totalProcess) {
                        break;
                    }

                    currentTime++;
                }

                addGanttBlock(ganttChart, "Idle", idleStart, currentTime);
                continue;
            }

            CPUProcess selectedProcess = processList.get(selectedIndex);

            int startTime = currentTime;
            currentTime += selectedProcess.getBurstTime();

            int completionTime = currentTime;
            int turnaroundTime = completionTime - selectedProcess.getArrivalTime();
            int waitingTime = turnaroundTime - selectedProcess.getBurstTime();

            selectedProcess.setCompletionTime(completionTime);
            selectedProcess.setTurnaroundTime(turnaroundTime);
            selectedProcess.setWaitingTime(waitingTime);

            addGanttBlock(ganttChart, selectedProcess.getProcessId(), startTime, completionTime);

            completed[selectedIndex] = true;
            completedProcess++;
        }

        return buildResult("SJF", processList, ganttChart);
    }

    // 3. Shortest Remaining Time First - Preemptive
    // Unit-by-unit Gantt chart, including Idle time.
    public static ScheduleResult srtf(List<CPUProcess> originalList) {
        List<CPUProcess> processList = copyProcessList(originalList);
        List<GanttChart> ganttChart = new ArrayList<>();

        int totalProcess = processList.size();
        int completedProcess = 0;
        int currentTime = 0;

        while (completedProcess < totalProcess) {
            CPUProcess selectedProcess = null;

            for (CPUProcess p : processList) {
                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0) {
                    if (selectedProcess == null) {
                        selectedProcess = p;
                    } else if (p.getRemainingTime() < selectedProcess.getRemainingTime()) {
                        selectedProcess = p;
                    } else if (p.getRemainingTime() == selectedProcess.getRemainingTime()
                            && p.getArrivalTime() < selectedProcess.getArrivalTime()) {
                        selectedProcess = p;
                    }
                }
            }

            if (selectedProcess == null) {
                addGanttBlock(ganttChart, "Idle", currentTime, currentTime + 1);
                currentTime++;
                continue;
            }

            addGanttBlock(ganttChart, selectedProcess.getProcessId(), currentTime, currentTime + 1);

            selectedProcess.setRemainingTime(selectedProcess.getRemainingTime() - 1);
            currentTime++;

            if (selectedProcess.getRemainingTime() == 0) {
                completedProcess++;

                int completionTime = currentTime;
                int turnaroundTime = completionTime - selectedProcess.getArrivalTime();
                int waitingTime = turnaroundTime - selectedProcess.getBurstTime();

                selectedProcess.setCompletionTime(completionTime);
                selectedProcess.setTurnaroundTime(turnaroundTime);
                selectedProcess.setWaitingTime(waitingTime);
            }
        }

        return buildResult("SRTF", processList, ganttChart);
    }

    // 4. Priority Scheduling - Non-preemptive
    // Lower priority number means higher priority.
    public static ScheduleResult priorityScheduling(List<CPUProcess> originalList) {
        List<CPUProcess> processList = copyProcessList(originalList);
        List<GanttChart> ganttChart = new ArrayList<>();

        int totalProcess = processList.size();
        int completedProcess = 0;
        int currentTime = 0;

        boolean[] completed = new boolean[totalProcess];

        while (completedProcess < totalProcess) {
            int selectedIndex = -1;

            for (int i = 0; i < totalProcess; i++) {
                CPUProcess p = processList.get(i);

                if (!completed[i] && p.getArrivalTime() <= currentTime) {
                    if (selectedIndex == -1) {
                        selectedIndex = i;
                    } else {
                        CPUProcess selected = processList.get(selectedIndex);

                        if (p.getPriority() < selected.getPriority()) {
                            selectedIndex = i;
                        } else if (p.getPriority() == selected.getPriority()
                                && p.getArrivalTime() < selected.getArrivalTime()) {
                            selectedIndex = i;
                        }
                    }
                }
            }

            if (selectedIndex == -1) {
                int idleStart = currentTime;
                currentTime++;

                while (true) {
                    boolean processAvailable = false;

                    for (int i = 0; i < totalProcess; i++) {
                        CPUProcess p = processList.get(i);

                        if (!completed[i] && p.getArrivalTime() <= currentTime) {
                            processAvailable = true;
                            break;
                        }
                    }

                    if (processAvailable || completedProcess == totalProcess) {
                        break;
                    }

                    currentTime++;
                }

                addGanttBlock(ganttChart, "Idle", idleStart, currentTime);
                continue;
            }

            CPUProcess selectedProcess = processList.get(selectedIndex);

            int startTime = currentTime;
            currentTime += selectedProcess.getBurstTime();

            int completionTime = currentTime;
            int turnaroundTime = completionTime - selectedProcess.getArrivalTime();
            int waitingTime = turnaroundTime - selectedProcess.getBurstTime();

            selectedProcess.setCompletionTime(completionTime);
            selectedProcess.setTurnaroundTime(turnaroundTime);
            selectedProcess.setWaitingTime(waitingTime);

            addGanttBlock(ganttChart, selectedProcess.getProcessId(), startTime, completionTime);

            completed[selectedIndex] = true;
            completedProcess++;
        }

        return buildResult("Priority Scheduling", processList, ganttChart);
    }

    // 5. Round Robin Scheduling
    public static ScheduleResult roundRobin(List<CPUProcess> originalList, int timeQuantum) {
        List<CPUProcess> processList = copyProcessList(originalList);
        List<GanttChart> ganttChart = new ArrayList<>();

        processList.sort(Comparator.comparingInt(CPUProcess::getArrivalTime));

        Queue<CPUProcess> readyQueue = new LinkedList<>();
        boolean[] addedToQueue = new boolean[processList.size()];

        int totalProcess = processList.size();
        int completedProcess = 0;
        int currentTime = 0;

        while (completedProcess < totalProcess) {

            for (int i = 0; i < totalProcess; i++) {
                CPUProcess p = processList.get(i);

                if (!addedToQueue[i] && p.getArrivalTime() <= currentTime) {
                    readyQueue.add(p);
                    addedToQueue[i] = true;
                }
            }

            if (readyQueue.isEmpty()) {
                int idleStart = currentTime;
                currentTime++;

                while (readyQueue.isEmpty() && completedProcess < totalProcess) {
                    for (int i = 0; i < totalProcess; i++) {
                        CPUProcess p = processList.get(i);

                        if (!addedToQueue[i] && p.getArrivalTime() <= currentTime) {
                            readyQueue.add(p);
                            addedToQueue[i] = true;
                        }
                    }

                    if (readyQueue.isEmpty()) {
                        currentTime++;
                    }
                }

                addGanttBlock(ganttChart, "Idle", idleStart, currentTime);
                continue;
            }

            CPUProcess currentProcess = readyQueue.poll();

            int startTime = currentTime;
            int executionTime = Math.min(timeQuantum, currentProcess.getRemainingTime());

            currentProcess.setRemainingTime(currentProcess.getRemainingTime() - executionTime);
            currentTime += executionTime;

            addGanttBlock(ganttChart, currentProcess.getProcessId(), startTime, currentTime);

            for (int i = 0; i < totalProcess; i++) {
                CPUProcess p = processList.get(i);

                if (!addedToQueue[i] && p.getArrivalTime() <= currentTime) {
                    readyQueue.add(p);
                    addedToQueue[i] = true;
                }
            }

            if (currentProcess.getRemainingTime() > 0) {
                readyQueue.add(currentProcess);
            } else {
                completedProcess++;

                int completionTime = currentTime;
                int turnaroundTime = completionTime - currentProcess.getArrivalTime();
                int waitingTime = turnaroundTime - currentProcess.getBurstTime();

                currentProcess.setCompletionTime(completionTime);
                currentProcess.setTurnaroundTime(turnaroundTime);
                currentProcess.setWaitingTime(waitingTime);
            }
        }

        return buildResult("Round Robin", processList, ganttChart);
    }

    public static ScheduleResult findBestAlgorithm(List<ScheduleResult> resultList) {
        ScheduleResult bestResult = resultList.get(0);

        for (ScheduleResult result : resultList) {
            if (result.getAverageWaitingTime() < bestResult.getAverageWaitingTime()) {
                bestResult = result;
            } else if (result.getAverageWaitingTime() == bestResult.getAverageWaitingTime()
                    && result.getAverageTurnaroundTime() < bestResult.getAverageTurnaroundTime()) {
                bestResult = result;
            }
        }

        return bestResult;
    }
}
<%@page import="java.util.List"%>
<%@page import="model.ScheduleResult"%>
<%@page import="model.CPUProcess"%>
<%@page import="model.GanttChart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>CPU Scheduling Result</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="page-wrapper">

    <%
        List<ScheduleResult> allResults = (List<ScheduleResult>) request.getAttribute("allResults");
        ScheduleResult bestResult = (ScheduleResult) request.getAttribute("bestResult");
        Integer timeQuantum = (Integer) request.getAttribute("timeQuantum");
    %>

    <div class="hero-section result-hero">
        <div class="hero-content">
            <div class="badge">Analysis Completed</div>
            <h1>CPU Scheduling Result Dashboard</h1>
            <p>
                Performance comparison of FCFS, SJF, SRTF,
                Priority Scheduling and Round Robin.
            </p>
        </div>
    </div>

    <div class="best-result-card">
        <div>
            <span class="small-title">Most Suitable Algorithm</span>
            <h2><%= bestResult.getAlgorithmName() %></h2>
            <p>
                This algorithm is selected because it gives the lowest average waiting time.
                If average waiting time is equal, average turnaround time is used as the second comparison factor.
            </p>
        </div>

        <div class="best-stats">
            <div>
                <span>Average WT</span>
                <strong><%= String.format("%.2f", bestResult.getAverageWaitingTime()) %></strong>
            </div>

            <div>
                <span>Average TAT</span>
                <strong><%= String.format("%.2f", bestResult.getAverageTurnaroundTime()) %></strong>
            </div>
        </div>
    </div>

    <div class="main-card">
        <div class="section-header">
            <h2>Algorithm Comparison</h2>
            <p>
                Lower average waiting time indicates better scheduling performance.
                Round Robin Time Quantum: <strong><%= timeQuantum %></strong>
            </p>
        </div>

        <table class="result-table">
            <tr>
                <th>Algorithm</th>
                <th>Average Waiting Time</th>
                <th>Average Turnaround Time</th>
                <th>Status</th>
            </tr>

            <% for (ScheduleResult result : allResults) { %>
                <tr>
                    <td><strong><%= result.getAlgorithmName() %></strong></td>
                    <td><%= String.format("%.2f", result.getAverageWaitingTime()) %></td>
                    <td><%= String.format("%.2f", result.getAverageTurnaroundTime()) %></td>
                    <td>
                        <% if (result.getAlgorithmName().equals(bestResult.getAlgorithmName())) { %>
                            <span class="status-best">Best</span>
                        <% } else { %>
                            <span class="status-normal">Compared</span>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </table>
    </div>

    <% for (ScheduleResult result : allResults) { %>

        <div class="algorithm-card">
            <div class="algorithm-title">
                <div>
                    <span class="algorithm-label">Algorithm</span>
                    <h2><%= result.getAlgorithmName() %></h2>
                </div>

                <div class="mini-stat-row">
                    <div>
                        <span>Avg WT</span>
                        <strong><%= String.format("%.2f", result.getAverageWaitingTime()) %></strong>
                    </div>

                    <div>
                        <span>Avg TAT</span>
                        <strong><%= String.format("%.2f", result.getAverageTurnaroundTime()) %></strong>
                    </div>
                </div>
            </div>

            <h3>Gantt Chart</h3>

            <div class="gantt-container">
                <% for (GanttChart g : result.getGanttChart()) { %>
                   <div class="gantt-box <%= g.getProcessId().equals("Idle") ? "idle-box" : "" %>">
    <div class="process-name"><%= g.getProcessId() %></div>
    <div class="time"><%= g.getStartTime() %> - <%= g.getEndTime() %></div>
</div>
                <% } %>
            </div>

            <h3>Process Calculation Table</h3>

            <table class="result-table">
                <tr>
                    <th>Process</th>
                    <th>Arrival Time</th>
                    <th>Burst Time</th>
                    <th>Priority</th>
                    <th>Completion Time</th>
                    <th>Turnaround Time</th>
                    <th>Waiting Time</th>
                </tr>

                <% for (CPUProcess p : result.getProcessList()) { %>
                    <tr>
                        <td><strong><%= p.getProcessId() %></strong></td>
                        <td><%= p.getArrivalTime() %></td>
                        <td><%= p.getBurstTime() %></td>
                        <td><%= p.getPriority() %></td>
                        <td><%= p.getCompletionTime() %></td>
                        <td><%= p.getTurnaroundTime() %></td>
                        <td><%= p.getWaitingTime() %></td>
                    </tr>
                <% } %>
            </table>
        </div>

    <% } %>

    <div class="center">
        <a href="index.jsp" class="back-btn">Analyze Again</a>
    </div>

    <footer>
        CPU Scheduling Algorithm Calculator
    </footer>

</div>

</body>
</html>
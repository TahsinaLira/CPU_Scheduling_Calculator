package controller;

import algorithm.SchedulingAlgorithms;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CPUProcess;
import model.ScheduleResult;

public class SchedulingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] processIdArray = request.getParameterValues("processId");
        String[] arrivalTimeArray = request.getParameterValues("arrivalTime");
        String[] burstTimeArray = request.getParameterValues("burstTime");
        String[] priorityArray = request.getParameterValues("priority");

        int timeQuantum = Integer.parseInt(request.getParameter("timeQuantum"));

        List<CPUProcess> processList = new ArrayList<>();

        for (int i = 0; i < processIdArray.length; i++) {
            String processId = processIdArray[i];
            int arrivalTime = Integer.parseInt(arrivalTimeArray[i]);
            int burstTime = Integer.parseInt(burstTimeArray[i]);
            int priority = Integer.parseInt(priorityArray[i]);

            CPUProcess process = new CPUProcess(
                    processId,
                    arrivalTime,
                    burstTime,
                    priority
            );

            processList.add(process);
        }

        ScheduleResult fcfsResult = SchedulingAlgorithms.fcfs(processList);
        ScheduleResult sjfResult = SchedulingAlgorithms.sjfNonPreemptive(processList);
        ScheduleResult srtfResult = SchedulingAlgorithms.srtf(processList);
        ScheduleResult priorityResult = SchedulingAlgorithms.priorityScheduling(processList);
        ScheduleResult roundRobinResult = SchedulingAlgorithms.roundRobin(processList, timeQuantum);

        List<ScheduleResult> allResults = new ArrayList<>();
        allResults.add(fcfsResult);
        allResults.add(sjfResult);
        allResults.add(srtfResult);
        allResults.add(priorityResult);
        allResults.add(roundRobinResult);

        ScheduleResult bestResult = SchedulingAlgorithms.findBestAlgorithm(allResults);

        request.setAttribute("allResults", allResults);
        request.setAttribute("bestResult", bestResult);
        request.setAttribute("timeQuantum", timeQuantum);

        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
        dispatcher.forward(request, response);
    }
}
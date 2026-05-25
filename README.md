# CPU Scheduling Algorithm Calculator

A web-based CPU Scheduling Algorithm Calculator developed using Java, JSP, Servlet, HTML, CSS, JavaScript, and Apache Tomcat.

The project calculates and compares different CPU scheduling algorithms using process details given by the user.

## Algorithms Included

- First Come First Serve (FCFS)
- Shortest Job First (SJF)
- Shortest Remaining Time First (SRTF)
- Priority Scheduling
- Round Robin Scheduling

## Features

- Dynamic process input table
- Calculates Completion Time (CT)
- Calculates Turnaround Time (TT)
- Calculates Waiting Time (WT)
- Calculates Average TT and Average WT
- Displays Gantt chart
- Shows Idle time
- Compares all algorithms
- Recommends the best algorithm based on lowest Average WT

## Technologies Used

- Java
- JSP
- Servlet
- HTML
- CSS
- JavaScript
- Apache Tomcat
- NetBeans IDE

## Formulas Used

TT = CT - AT
WT = TT - BT
Average TT = Total TT / Number of Processes
Average WT = Total WT / Number of Processes

Where:

AT = Arrival Time
BT = Burst Time
CT = Completion Time
TT = Turnaround Time
WT = Waiting Time

Best Algorithm Selection:

The best algorithm is selected based on the lowest Average Waiting Time.
If two algorithms have the same Average Waiting Time, the lower Average Turnaround Time is used as the second comparison factor.


Project Notes

SJF is implemented as non-preemptive.

SRTF is the preemptive version of SJF.

Priority Scheduling is non-preemptive.

Lower priority number means higher priority.

Round Robin uses user-given time quantum.

<img width="1912" height="859" alt="Screenshot 2026-05-25 144346" src="https://github.com/user-attachments/assets/e9abfd4b-adf4-4dfb-832a-3927538b4581" />

<img width="1401" height="813" alt="Screenshot 2026-05-25 144623" src="https://github.com/user-attachments/assets/0a9948c5-251c-4504-8469-d8648d447664" />

<img width="1920" height="4788" alt="screencapture-localhost-9595-CPUSchedulingCalculator-SchedulingServlet-2026-05-25-14_47_14" src="https://github.com/user-attachments/assets/d499fd4c-2b0e-4caf-8c7f-42515d754b6d" />


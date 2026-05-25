<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>CPU Scheduling Algorithm Analyzer</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="page-wrapper">

    <div class="hero-section">
        <div class="hero-content">
            <div class="badge">Operating System Project</div>
            <h1>CPU Scheduling Algorithm Analyzer</h1>
            <p>
                Compare FCFS, SJF, SRTF, Priority Scheduling and Round Robin
                using average waiting time and turnaround time.
            </p>
        </div>
    </div>

    <div class="feature-row">
        <div class="feature-card">
            <h3>5</h3>
            <p>Algorithms</p>
        </div>

        <div class="feature-card">
            <h3>WT</h3>
            <p>Waiting Time</p>
        </div>

        <div class="feature-card">
            <h3>TT</h3>
            <p>Turnaround Time</p>
        </div>

        <div class="feature-card">
            <h3>Best</h3>
            <p>Auto Selection</p>
        </div>
    </div>

    <div class="main-card">
        <div class="section-header">
            <h2>Process Input Panel</h2>
            <p>Enter process details to analyze scheduling performance.</p>
        </div>

        <form action="SchedulingServlet" method="post" onsubmit="return validateForm()">

            <div class="input-row">
                <div class="input-group">
                    <label>Number of Processes</label>
                    <input type="number"
                           id="processCount"
                           name="processCount"
                           min="1"
                           max="15"
                           placeholder="Example: 4"
                           required>
                </div>

                <div class="button-group">
                    <button type="button" class="secondary-btn" onclick="generateTable()">
                        Generate Process Table
                    </button>
                </div>
            </div>

            <div id="processInputArea"></div>

            <div class="input-row quantum-row">
                <div class="input-group">
                    <label>Time Quantum for Round Robin</label>
                    <input type="number"
                           name="timeQuantum"
                           id="timeQuantum"
                           min="1"
                           placeholder="Example: 3"
                           required>
                </div>
            </div>

            <div class="formula-box">
                <h3>Formula Used</h3>

                <div class="formula-grid">
                    <div>
                        <span>Turnaround Time</span>
                        <strong>CT - AT</strong>
                    </div>

                    <div>
                        <span>Waiting Time</span>
                        <strong>TT - BT</strong>
                    </div>

                    <div>
                        <span>Best Algorithm</span>
                        <strong>Lowest Avg WT</strong>
                    </div>
                </div>
            </div>

            <div class="algorithm-info">
                <h3>Algorithms Included</h3>

                <div class="algorithm-info-grid">
                    <div>
                        <h4>FCFS</h4>
                        <p>Executes processes according to arrival time.</p>
                    </div>

                    <div>
                        <h4>SJF</h4>
                        <p>Selects the process with the shortest burst time. It is non-preemptive.</p>
                    </div>

                    <div>
                        <h4>SRTF</h4>
                        <p>Preemptive version of SJF. It selects the process with the shortest remaining time.</p>
                    </div>

                    <div>
                        <h4>Priority</h4>
                        <p>Selects the process with the highest priority. Lower number means higher priority.</p>
                    </div>

                    <div>
                        <h4>Round Robin</h4>
                        <p>Each process gets CPU for a fixed time quantum.</p>
                    </div>
                </div>
            </div>

            <button type="submit" class="submit-btn">
                Analyze Scheduling Performance
            </button>
        </form>
    </div>

    <footer>
        Developed for Operating System Lab Project
    </footer>

</div>

<script>
function generateTable() {
    let count = document.getElementById("processCount").value;
    let area = document.getElementById("processInputArea");

    if (count <= 0 || count > 15) {
        alert("Please enter process number between 1 and 15.");
        return;
    }

    let html = "";

    html += "<div class='table-card'>";
    html += "<h3>Process Details</h3>";
    html += "<table class='input-table'>";
    html += "<tr>";
    html += "<th>Process ID</th>";
    html += "<th>Arrival Time</th>";
    html += "<th>Burst Time</th>";
    html += "<th>Priority</th>";
    html += "</tr>";

    for (let i = 1; i <= count; i++) {
        html += "<tr>";

        html += "<td>";
        html += "<input type='text' name='processId' value='P" + i + "' required>";
        html += "</td>";

        html += "<td>";
        html += "<input type='number' name='arrivalTime' min='0' placeholder='0' required>";
        html += "</td>";

        html += "<td>";
        html += "<input type='number' name='burstTime' min='1' placeholder='5' required>";
        html += "</td>";

        html += "<td>";
        html += "<input type='number' name='priority' min='1' placeholder='1' required>";
        html += "</td>";

        html += "</tr>";
    }

    html += "</table>";
    html += "<p class='hint'>Note: Lower priority number means higher priority.</p>";
    html += "</div>";

    area.innerHTML = html;
}

function validateForm() {
    let tableArea = document.getElementById("processInputArea").innerHTML;
    let quantum = document.getElementById("timeQuantum").value;

    if (tableArea.trim() === "") {
        alert("Please generate the process table first.");
        return false;
    }

    if (quantum <= 0) {
        alert("Time quantum must be greater than 0.");
        return false;
    }

    return true;
}
</script>

</body>
</html>
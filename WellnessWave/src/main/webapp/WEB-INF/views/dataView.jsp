<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WellnessWave Data View</title>
    <style><%@include file="/../../css/format.css"%></style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.3.0"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns@3.0.0"></script>
</head>
<body>
    <h2>WellnessWave Data View</h2>
    <a href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a>
    
    <h3>Select Metric:</h3>
    <select id="metricSelector">
        <option value="sleep">Sleep</option>
        <option value="food">Food</option>
        <option value="stress">Stress</option>
        <option value="mood">Mood</option>
        <option value="nutrition">Nutrition</option>
    </select>
    
    <h3>Line Graph:</h3>
    <canvas id="lineChart"></canvas>

    <script>
        document.getElementById('metricSelector').addEventListener('change', function() {
            var selectedMetric = this.value;
            fetch('${pageContext.request.contextPath}/dataGraph?metric=' + selectedMetric)
                .then(response => response.json())
                .then(data => {
                    updateChart(data);
                });
        });

        var ctx = document.getElementById('lineChart').getContext('2d');
        var lineChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: [{
                    label: 'Wellness Data',
                    data: [],
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            },
            options: {
                scales: {
                    x: {
                        type: 'time',
                        time: {
                            unit: 'day',
                            parser: 'yyyy-MM-dd',
                            tooltipFormat: 'll'
                        }
                    },
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        function updateChart(data) {
            lineChart.data.labels = data.map(item => item.date);
            lineChart.data.datasets[0].data = data.map(item => item.value);
            lineChart.update();
        }
    </script>
</body>
</html>




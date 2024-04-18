<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WellnessWave Dashboard</title>
</head>
<body>
    <h2>WellnessWave Dashboard</h2>
    <a href="insertWellnessData">Insert Wellness Data</a>
 <h3>Wellness Data:</h3>
    <table>
        <thead>
            <tr>
                <th>Metric Name</th>
                <th>Value</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="data" items="${wellnessData}">
                <tr>
                    <td>${data.metricName}</td>
                    <td>${data.value}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
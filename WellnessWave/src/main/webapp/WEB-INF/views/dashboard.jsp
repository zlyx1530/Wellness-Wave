<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <a href="dataView">Data View</a>
    <a href="deleteData">Delete Data</a>
    <a href="deleteMetric">Delete Row</a>
    <h3>Wellness Data:</h3>
    <table>
        <thead>
            <tr>
            	<th>Metric Id</th>
                <th>Metric Name</th>
                <th>Value</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="data" items="${wellnessData}">
                <tr>
                	<td>${data.metricId}</td>
                    <td>${data.metricName}</td>
                    <td>${data.value}</td>
                    <td>${data.date}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <form action="${pageContext.request.contextPath}/exportCsv" method="post">
        <input type="submit" value="Export to Excel (CSV)" />
    </form>
</body>
</html>






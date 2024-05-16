<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Row</title>
</head>
<body>
    <h2>Delete Row</h2>
    <a href="dashboard">Go Back to Dashboard</a>
    <h3>Wellness Data:</h3>
    <table>
        <thead>
            <tr>
            	<th>Metric Id</th>
                <th>Metric Name</th>
                <th>Value</th>
                <th>Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="data" items="${wellnessData}">
                <tr>
                	<td>${data.metricId}</td>
                    <td>${data.metricName}</td>
                    <td>${data.value}</td>
                    <td>${data.date}</td>
                    <td>
                        <form action="deleteMetric" method="post">
                            <input type="hidden" name="metricId" value="${data.metricId}">
                            <button type="submit">Delete</button>
                            <div id="message-${data.metricId}" style="color: red;"></div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <hr>
    <h3>Delete Row by Metric ID:</h3>
    <form action="deleteMetric" method="post">
        <label for="metricId">Enter Metric ID to Delete:</label>
        <input type="text" id="metricId" name="metricId">
        <button type="submit">Delete</button>
        <div id="message" style="color: red;"></div>
    </form>
</body>
</html>

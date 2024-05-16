<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Data</title>
    <style><%@include file="/../../css/format.css"%></style>
</head>
<body>
<a href="${pageContext.request.contextPath}/dashboard">Back to Dashboard</a>
    <h2>Delete Data</h2>
    <form action="${pageContext.request.contextPath}/deleteData" method="post">
        <label for="metric">Enter Metric Name to Delete:</label>
        <input type="text" id="metric" name="metric" required>
        <button type="submit">Delete</button>
    </form>
    <div id="message">${message}</div>
</body>
</html>

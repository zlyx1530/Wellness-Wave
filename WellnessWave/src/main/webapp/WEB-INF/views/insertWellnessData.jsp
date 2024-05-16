<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert Wellness Data</title>
    <style><%@include file="/../../css/format.css"%></style>
</head>
<body>
    <h2>Insert Wellness Data</h2>
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="error-message">
            <%= request.getAttribute("errorMessage") %>
        </div>
    <% } %>
    <form action="insertWellnessData" method="post">
        <label for="metricName">Metric Name:</label>
        <select id="metricName" name="metricName">
            <option value="sleep">Sleep</option>
            <option value="food">Food</option>
            <option value="stress">Stress</option>
            <option value="mood">Mood</option>
            <option value="nutrition">Nutrition</option>    
        </select><br>
        <label for="value">Value:</label>
        <input type="text" id="value" name="value" required><br>
        <label for="date">Date:</label> <!-- Add date input -->
        <input type="date" id="date" name="date" required><br> <!-- Date input field -->
        <br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>





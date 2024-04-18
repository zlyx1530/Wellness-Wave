<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert Wellness Data</title>
</head>
<body>
    <h2>Insert Wellness Data</h2>
    <form action="insertWellnessData" method="post">
        <label for="metricName">Metric Name:</label>
        <input type="text" id="metricName" name="metricName" required><br><br>
        <label for="value">Value:</label>
        <input type="text" id="value" name="value" required><br><br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>

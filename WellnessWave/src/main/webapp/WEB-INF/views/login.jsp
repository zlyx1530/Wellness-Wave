<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WellnessWave Login Page</title>
</head>
<body>
    <h2>WellnessWave Login Page</h2>
    <%-- Display error message if present --%>
    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>
    <%-- Display success message if present --%>
    <c:if test="${not empty successMessage}">
        <p style="color: green;">${successMessage}</p>
    </c:if>
    <form action="login" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Login</button>
    </form>
    <br>
    <form action="register" method="get">
        <button type="submit">Create New User</button>
    </form>
</body>
</html>

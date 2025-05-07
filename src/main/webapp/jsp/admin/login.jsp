<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Admin Login</h1>
    </header>
    <div class="container">
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="login" method="post">
            <label for="admin_name">Name:</label>
            <input type="text" id="admin_name" name="admin_name" required>
            <label for="admin_password">Password:</label>
            <input type="password" id="admin_password" name="admin_password" required>
            <button type="submit">Login</button>
        </form>
        <p><a href="signup">Don't have an account? Signup</a></p>
        <p><a href="/TeacherManagementSystem/teacher/login">Login as Teacher</a>
    </div>
</body>
</html>
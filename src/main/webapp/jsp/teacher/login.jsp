<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teacher Login</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Teacher Login</h1>
    </header>
    <div class="container">
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="login" method="post">
            <label for="auth_code">Authentication Code:</label>
            <input type="password" id="auth_code" name="auth_code" required>
            <button type="submit">Login</button>
        </form>
         <p><a href="/TeacherManagementSystem/admin/login">Login as Admin</a>
    </div>
</body>
</html>

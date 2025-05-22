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
        <% 
            String rememberedEmail = "";
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("adminEmail".equals(cookie.getName())) {
                        rememberedEmail = cookie.getValue();
                        break;
                    }
                }
            }
        %>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <form action="login" method="post">
            <label for="admin_email">Email:</label>
            <input type="email" id="admin_email" name="admin_email" value="<%= rememberedEmail %>" required>
            <label for="admin_password">Password:</label>
            <input type="password" id="admin_password" name="admin_password" required>
            <div class="remember-me">
                <input type="checkbox" id="rememberMe" name="rememberMe" <%= rememberedEmail.isEmpty() ? "" : "checked" %>>
                <label for="rememberMe">Remember Me</label>
            </div>
            <button type="submit">Login</button>
        </form>
        <p><a href="signup">Don't have an account? Signup</a></p>
        <p><a href="/TeacherManagementSystem/teacher/login">Login as Teacher</a></p>
    </div>
</body>
</html>

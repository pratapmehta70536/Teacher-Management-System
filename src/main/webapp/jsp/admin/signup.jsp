<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Signup</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Admin Signup</h1>
    </header>
    <div class="container">
        <form action="signup" method="post">
            <label for="admin_name">Name:</label>
            <input type="text" id="admin_name" name="admin_name" required>
            <label for="admin_password">Password:</label>
            <input type="password" id="admin_password" name="admin_password" required>
            <label for="admin_email">Email:</label>
            <input type="email" id="admin_email" name="admin_email" required>
            <label for="admin_phone">Phone:</label>
            <input type="text" id="admin_phone" name="admin_phone">
            <button type="submit">Signup</button>
        </form>
        <p><a href="login">Already have an account? Login</a></p>
    </div>
</body>
</html>
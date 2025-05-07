<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Profile</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Admin Profile</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="manage_teachers">Manage Teachers</a>
        <a href="manage_schedules">Manage Schedules</a>
        <a href="manage_attendance">Manage Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Profile Details</h2>
        <p>Name: <%= ((com.teachermanagement.model.Admin) session.getAttribute("admin")).getAdminName() %></p>
        <p>Email: <%= ((com.teachermanagement.model.Admin) session.getAttribute("admin")).getAdminEmail() %></p>
        <p>Phone: <%= ((com.teachermanagement.model.Admin) session.getAttribute("admin")).getAdminPhone() %></p>
    </div>
</body>
</html>
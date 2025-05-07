<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teacher Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Teacher Dashboard</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="view_schedules">View Schedules</a>
        <a href="view_attendance">View Attendance</a>
        <a href="mark_attendance">Mark Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Welcome, <%= ((com.teachermanagement.model.Teacher) session.getAttribute("teacher")).getTeacherName() %></h2>
        <p>Use the navigation links to view schedules, attendance, or mark attendance.</p>
    </div>
</body>
</html>
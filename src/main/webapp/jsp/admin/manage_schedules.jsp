<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Teacher, com.teachermanagement.model.Schedule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Schedules</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Manage Schedules</h1>
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
        <h2>Add Schedule</h2>
        <form action="add_schedule" method="post">
            <label for="teacher_id">Teacher:</label>
            <select id="teacher_id" name="teacher_id" required>
                <% List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
                   if (teachers != null) {
                       for (Teacher teacher : teachers) { %>
                           <option value="<%= teacher.getTeacherId() %>"><%= teacher.getTeacherName() %></option>
                       <% }
                   } %>
            </select>
            <label for="department">Department:</label>
            <input type="text" id="department" name="department" required>
            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject" required>
            <label for="year">Year:</label>
            <input type="number" id="year" name="year" required>
            <label for="semester">Semester:</label>
            <input type="number" id="semester" name="semester" required>
            <label for="day">Day:</label>
            <input type="text" id="day" name="day" required>
            <label for="start_time">Start Time:</label>
            <input type="text" id="start_time" name="start_time" required>
            <label for="end_time">End Time:</label>
            <input type="text" id="end_time" name="end_time" required>
            <label for="room_name">Room:</label>
            <input type="text" id="room_name" name="room_name" required>
            <button type="submit">Add Schedule</button>
        </form>
    </div>
</body>
</html>
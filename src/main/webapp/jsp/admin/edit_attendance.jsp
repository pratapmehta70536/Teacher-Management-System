<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.teachermanagement.model.Attendance" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Attendance</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Edit Attendance</h1>
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
        <h2>Edit Attendance Information</h2>
        <% 
            Attendance attendance = (Attendance) request.getAttribute("attendance");
            if (attendance != null) { 
        %>
            <form action="update_attendance" method="post">
            <input type="hidden" name="attendance_id" value="<%= attendance.getAttendanceId() %>">
             <label for="day">Day:</label>
                <input type="text" id="day" name="day" value="<%= attendance.getDay() %>" required>
                <label for="date">Date:</label>
                <input type="date" id="date" name="date" value="<%= attendance.getDate() %>" required>
                <label for="status">Status:</label>
				<select id="status" name="status">
    				<option value="Present" <%= "Present".equals(attendance.getStatus()) ? "selected" : "" %>>Present</option>
    				<option value="Absent" <%= "Absent".equals(attendance.getStatus()) ? "selected" : "" %>>Absent</option>
    				<option value="Leave" <%= "Leave".equals(attendance.getStatus()) ? "selected" : "" %>>Leave</option>
				</select>
                <label for="salary">Remarks:</label>
                <textarea id="remarks" name="remarks"><%= attendance.getRemarks() %></textarea>
                <button type="submit">Update Attendance</button>
            </form>
            
            <a href="manage_attendance"><button>Cancel</button></a>
        <% } else { %>
            <p class="error">Attendance not found.</p>
            <a href="manage_attendance">Back to Manage Teachers</a>
        <% } %>
    </div>
</body>
</html>

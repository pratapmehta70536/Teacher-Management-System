<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.teachermanagement.model.Teacher" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Teacher</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Edit Teacher</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="manage_teachers">Manage Teachers</a>
        <a href="manage_attendance">Manage Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Update Teacher Information</h2>
        <% 
            Teacher teacher = (Teacher) request.getAttribute("teacher");
            if (teacher != null) { 
        %>
            <form action="update_teacher" method="post">
                <input type="hidden" name="teacher_id" value="<%= teacher.getTeacherId() %>">
                <label for="teacher_name">Name:</label>
                <input type="text" id="teacher_name" name="teacher_name" value="<%= teacher.getTeacherName() %>">
                <label for="teacher_email">Email:</label>
                <input type="email" id="teacher_email" name="teacher_email" value="<%= teacher.getTeacherEmail() %>">
                <label for="teacher_phone">Phone:</label>
                <input type="text" id="teacher_phone" name="teacher_phone" value="<%= teacher.getTeacherPhone() %>">
                <label for="teacher_salary">Salary:</label>
                <input type="number" id="teacher_salary" name="teacher_salary" step="0.01" value="<%= teacher.getTeacherSalary() %>">
                <label for="teacher_authentication_code">Authentication Code:</label>
                <input type="text" id="teacher_authentication_code" name="teacher_authentication_code" value="<%= teacher.getTeacherAuthenticationCode() %>" required>
                <label for="teacher_subject">Subject:</label>
                <input type="text" id="teacher_subject" name="teacher_subject" value="<%= teacher.getTeacherTeachingSubject() %>">
                <label for="teacher_start_time">Start Time:</label>
                <input type="text" id="teacher_start_time" name="teacher_start_time" value="<%= teacher.getTeacherStartTime() %>">
                <label for="teacher_end_time">End Time:</label>
                <input type="text" id="teacher_end_time" name="teacher_end_time" value="<%= teacher.getTeacherEndTime() %>">
                <button type="submit">Update Teacher</button>
            </form>
            <a href="manage_teachers"><button>Cancel</button></a>
        <% } else { %>
            <p class="error">Teacher not found.</p>
            <a href="manage_teachers">Back to Manage Teachers</a>
        <% } %>
    </div>
</body>
</html>

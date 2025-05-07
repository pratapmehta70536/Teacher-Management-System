<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Schedule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Schedules</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>View Schedules</h1>
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
        <h2>Your Schedules</h2>
        <table>
            <tr>
                <th>Department</th>
                <th>Subject</th>
                <th>Year</th>
                <th>Semester</th>
                <th>Day</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Room</th>
            </tr>
            <% List<Schedule> schedules = (List<Schedule>) request.getAttribute("schedules");
               if (schedules != null) {
                   for (Schedule schedule : schedules) { %>
                       <tr>
                           <td><%= schedule.getDepartment() %></td>
                           <td><%= schedule.getSubject() %></td>
                           <td><%= schedule.getYear() %></td>
                           <td><%= schedule.getSemester() %></td>
                           <td><%= schedule.getDay() %></td>
                           <td><%= schedule.getStartTime() %></td>
                           <td><%= schedule.getEndTime() %></td>
                           <td><%= schedule.getRoomName() %></td>
                       </tr>
                   <% }
               } %>
        </table>
    </div>
</body>
</html>
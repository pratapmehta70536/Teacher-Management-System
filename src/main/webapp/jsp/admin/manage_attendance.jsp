<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Teacher, com.teachermanagement.model.Attendance,com.teachermanagement.dao.AttendanceDAO,com.teachermanagement.dao.TeacherDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Attendance</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Manage Attendance</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="manage_teachers">Manage Teachers</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Attendance List</h2>
        <table>
            <tr>
                <th>Day</th>
                <th>Date</th>
                <th>Status</th>
                <th>Remarks</th>
                <th>Actions</th>
            </tr>
            <% List<Attendance> attendances = (List<Attendance>) request.getAttribute("attendances");
               if (attendances != null) {
                   for (Attendance attendance : attendances) { %>
                       <tr>
                            <td><%= attendance.getDay() %></td>
                            <td><%= attendance.getDate() %></td>
                            <td><%= attendance.getStatus() %></td>
                            <td><%= attendance.getRemarks() != null ? attendance.getRemarks() : "" %></td>
                             <td>
                               <a href="edit_attendance?attendance_id=<%= attendance.getAttendanceId() %>">
                                   <button>Edit</button>
                               </a>
                               <form action="delete_attendance" method="post" style="display:inline;">
                                   <input type="hidden" name="attendance_id" value="<%= attendance.getAttendanceId() %>">
                                   <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                               </form>
                           </td>
                       </tr>
                   <% }
               } %>
        </table> 
        
    </div>   
</body>
</html>

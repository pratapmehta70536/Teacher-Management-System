<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Teacher, com.teachermanagement.model.Attendance,com.teachermanagement.dao.AttendanceDAO" %>
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
        <a href="manage_schedules">Manage Schedules</a>
        <a href="manage_attendance">Manage Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Add Attendance</h2>
        <form action="add_attendance" method="post">
            <label for="teacher_id">Teacher:</label>
            <select id="teacher_id" name="teacher_id" required>
                <% List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
                   if (teachers != null) {
                       for (Teacher teacher : teachers) { %>
                           <option value="<%= teacher.getTeacherId() %>"><%= teacher.getTeacherName() %></option>
                       <% }
                   } %>
            </select>
            <label for="day">Day:</label>
            <input type="text" id="day" name="day" required>
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>
            <label for="status">Status:</label>
            <select id="status" name="status" required>
                <option value="Present">Present</option>
                <option value="Absent">Absent</option>
                <option value="Leave">Leave</option>
            </select>
            <label for="remarks">Remarks:</label>
            <textarea id="remarks" name="remarks"></textarea>
            <button type="submit">Add Attendance</button>
        </form> 
        
         <h2>Your Attendance</h2>
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
                           <td><%= attendance.getRemarks() %></td>
                           <td>
                               <a href="edit_attendance?attendance_id=<%= attendance.getAttendanceId() %>">
                                   <button>Edit</button>
                               </a>
                               <form action="attendance" method="post" style="display:inline;">
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
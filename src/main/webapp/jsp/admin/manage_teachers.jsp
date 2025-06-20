<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Teacher" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Teachers</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Manage Teachers</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="manage_teachers">Manage Teachers</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Add Teacher</h2>
        <form action="add_teacher" method="post">
            <label for="teacher_name">Name:</label>
            <input type="text" id="teacher_name" name="teacher_name" required>
            <label for="teacher_email">Email:</label>
            <input type="email" id="teacher_email" name="teacher_email" required>
            <label for="teacher_phone">Phone:</label>
            <input type="text" id="teacher_phone" name="teacher_phone">
            <label for="salary">Salary:</label>
            <input type="number" id="teacher_salary" name="teacher_salary" step="0.01" required>
            <label for="teacher_authentication_code">Authentication Code:</label>
            <input type="text" id="teacher_authentication_code" name="teacher_authentication_code" required>
            <label for="teacher_subject">Subject:</label>
            <input type="text" id="teacher_subject" name="teacher_subject" required>
            <label for="teacher_start_time">Start Time:</label>
            <input type="text" id="teacher_start_time" name="teacher_start_time" required>
            <label for="teacher_end_time">End Time:</label>
            <input type="text" id="teacher_end_time" name="teacher_end_time" required>
            
            <button type="submit">Add Teacher</button>
        </form>

        <h2>Teacher List</h2>
        <table>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Salary</th>
                <th>Auth Code</th>
                <th>Subject</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Actions</th>
            </tr>
            <% List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
               if (teachers != null) {
                   for (Teacher teacher : teachers) { %>
                       <tr>
                           <td><%= teacher.getTeacherName() %></td>
                           <td><%= teacher.getTeacherEmail() %></td>
                           <td><%= teacher.getTeacherPhone() %></td>
                           <td><%= teacher.getTeacherSalary() %></td>
                           <td><%= teacher.getTeacherAuthenticationCode() %></td>
                           <td><%= teacher.getTeacherTeachingSubject() %></td>
                           <td><%= teacher.getTeacherStartTime() %></td>
                           <td><%= teacher.getTeacherEndTime() %></td>
                           <td>
                               <a href="edit_teacher?teacher_id=<%= teacher.getTeacherId() %>">
                                   <button>Edit</button>
                               </a>
                               <form action="delete_teacher" method="post" style="display:inline;">
                                   <input type="hidden" name="teacher_id" value="<%= teacher.getTeacherId() %>">
                                   <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                               </form>
                               
                               <a href="/TeacherManagementSystem/admin/manage_attendance">
                                   <button>Manage Attendance</button>
                               </a>
                               
                           </td>
                       </tr>
                   <% }
               } %>
        </table>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.teachermanagement.model.Teacher, com.teachermanagement.dao.AdminDAO, com.teachermanagement.model.Admin, com.teachermanagement.util.DatabaseConnection" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Teacher Profile</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Teacher Profile</h1>
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
        <h2>Profile Details</h2>
        <%
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            if (teacher != null) {
        %>
                <p>Name: <%= teacher.getTeacherName() %></p>
                <p>Email: <%= teacher.getTeacherEmail() %></p>
                <p>Phone: <%= teacher.getTeacherPhone() %></p>
                <p>Salary: <%= teacher.getSalary() %></p>
                <p>Authentication Code: <%= teacher.getTeacherAuthenticationCode() %></p>

                <h3>Admin Contact</h3>
                <%
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(
                             "SELECT a.admin_name, a.admin_email, a.admin_phone " +
                             "FROM admin a JOIN teacher_admin ta ON a.admin_id = ta.admin_id " +
                             "WHERE ta.teacher_id = ?")) {
                        stmt.setInt(1, teacher.getTeacherId());
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                %>
                            <p>Name: <%= rs.getString("admin_name") %></p>
                            <p>Email: <%= rs.getString("admin_email") %></p>
                            <p>Phone: <%= rs.getString("admin_phone") %></p>
                <%
                        } else {
                %>
                            <p>No admin assigned.</p>
                <%
                        }
                    } catch (SQLException e) {
                %>
                        <p class="error">Error retrieving admin contact: <%= e.getMessage() %></p>
                <%
                    }
                %>
        <%
            } else {
        %>
                <p class="error">No teacher data available. Please log in again.</p>
        <%
            }
        %>
    </div>
</body>
</html>
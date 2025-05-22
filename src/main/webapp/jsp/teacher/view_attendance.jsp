<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.teachermanagement.model.Attendance, com.teachermanagement.dao.AttendanceDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Attendance</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>View Attendance</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="view_attendance">View Attendance</a>
        <a href="mark_attendance">Mark Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
        <h2>Your Attendance</h2>
        <table>
            <tr>
                <th>Day</th>
                <th>Date</th>
                <th>Status</th>
                <th>Remarks</th>
                <th>Actions</th>
            </tr>
            <% 
                List<Attendance> attendances = (List<Attendance>) request.getAttribute("attendances");
                AttendanceDAO attendanceDAO = new AttendanceDAO();
                if (attendances != null) {
                    for (Attendance attendance : attendances) { 
                        boolean canEdit = false;
                        try {
                            canEdit = attendanceDAO.canEditAttendance(attendance.getAttendanceId());
                        } catch (Exception e) {
                            // Handle exception silently or log it
                        }
            %>
                        <tr>
                            <td><%= attendance.getDay() %></td>
                            <td><%= attendance.getDate() %></td>
                            <td><%= attendance.getStatus() %></td>
                            <td><%= attendance.getRemarks() != null ? attendance.getRemarks() : "" %></td>
                            <td>
                                <% if (canEdit) { %>
                                    <form action="update_attendance" method="post" style="display:inline;">
                                        <input type="hidden" name="attendance_id" value="<%= attendance.getAttendanceId() %>">
                                        <select name="day" required>
                                            <option value="Sunday" <%= attendance.getDay().equals("Sunday") ? "selected" : "" %>>Sunday</option>
                                            <option value="Monday" <%= attendance.getDay().equals("Monday") ? "selected" : "" %>>Monday</option>
                                            <option value="Tuesday" <%= attendance.getDay().equals("Tuesday") ? "selected" : "" %>>Tuesday</option>
                                            <option value="Wednesday" <%= attendance.getDay().equals("Wednesday") ? "selected" : "" %>>Wednesday</option>
                                            <option value="Thursday" <%= attendance.getDay().equals("Thursday") ? "selected" : "" %>>Thursday</option>
                                            <option value="Friday" <%= attendance.getDay().equals("Friday") ? "selected" : "" %>>Friday</option>
                                            <option value="Saturday" <%= attendance.getDay().equals("Saturday") ? "selected" : "" %>>Saturday</option>
                                        </select>
                                        <input type="date" name="date" value="<%= attendance.getDate() %>" required>
                                        <select name="status" required>
                                            <option value="Present" <%= attendance.getStatus().equals("Present") ? "selected" : "" %>>Present</option>
                                            <option value="Absent" <%= attendance.getStatus().equals("Absent") ? "selected" : "" %>>Absent</option>
                                            <option value="Leave" <%= attendance.getStatus().equals("Leave") ? "selected" : "" %>>Leave</option>
                                        </select>
                                        <textarea name="remarks"><%= attendance.getRemarks() != null ? attendance.getRemarks() : "" %></textarea>
                                        <button type="submit">Update</button>
                                    </form>
                                    <form action="delete_attendance" method="post" style="display:inline;">
                                        <input type="hidden" name="attendance_id" value="<%= attendance.getAttendanceId() %>">
                                        <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                                    </form>
                                <% } else { %>
                                    <span>Editing time expired</span>
                                <% } %>
                            </td>
                        </tr>
                    <% } %>
                <% } %>
        </table>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mark Attendance</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
    <header>
        <h1>Mark Attendance</h1>
    </header>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="view_attendance">View Attendance</a>
        <a href="mark_attendance">Mark Attendance</a>
        <a href="profile">Profile</a>
        <a href="logout">Logout</a>
    </nav>
    <div class="container">
        <h2>Mark Your Attendance</h2>
        <form action="mark_attendance" method="post">
            <label for="day">Day:</label>
            <select id="day" name="day" required>
                <option value="Sunday">Sunday</option>
                <option value="Monday">Monday</option>
                <option value="Tuesday">Tuesday</option>
                <option value="Wednesday">Wednesday</option>
                <option value="Thursday">Thursday</option>
                <option value="Friday">Friday</option>
                <option value="Saturday">Saturday</option>
            </select>
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
            <button type="submit">Mark Attendance</button>
        </form>
    </div>
</body>
</html>

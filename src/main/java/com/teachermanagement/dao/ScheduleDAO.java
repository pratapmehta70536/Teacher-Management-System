package com.teachermanagement.dao;

import com.teachermanagement.model.Schedule;
import com.teachermanagement.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    public void addSchedule(Schedule schedule, int teacherId) throws SQLException {
        String sql = "INSERT INTO schedule (department, subject, year, semester, day, start_time, end_time, room_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, schedule.getDepartment());
            stmt.setString(2, schedule.getSubject());
            stmt.setInt(3, schedule.getYear());
            stmt.setInt(4, schedule.getSemester());
            stmt.setString(5, schedule.getDay());
            stmt.setString(6, schedule.getStartTime());
            stmt.setString(7, schedule.getEndTime());
            stmt.setString(8, schedule.getRoomName());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int scheduleId = rs.getInt(1);
                String linkSql = "INSERT INTO teacher_schedule (teacher_id, schedule_id) VALUES (?, ?)";
                try (PreparedStatement linkStmt = conn.prepareStatement(linkSql)) {
                    linkStmt.setInt(1, teacherId);
                    linkStmt.setInt(2, scheduleId);
                    linkStmt.executeUpdate();
                }
            }
        }
    }

    public List<Schedule> getSchedulesByTeacher(int teacherId) throws SQLException {
        String sql = "SELECT s.* FROM schedule s JOIN teacher_schedule ts ON s.schedule_id = ts.schedule_id WHERE ts.teacher_id = ?";
        List<Schedule> schedules = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setDepartment(rs.getString("department"));
                schedule.setSubject(rs.getString("subject"));
                schedule.setYear(rs.getInt("year"));
                schedule.setSemester(rs.getInt("semester"));
                schedule.setDay(rs.getString("day"));
                schedule.setStartTime(rs.getString("start_time"));
                schedule.setEndTime(rs.getString("end_time"));
                schedule.setRoomName(rs.getString("room_name"));
                schedules.add(schedule);
            }
            return schedules;
        }
    }

    public void updateSchedule(Schedule schedule) throws SQLException {
        String sql = "UPDATE schedule SET department = ?, subject = ?, year = ?, semester = ?, day = ?, start_time = ?, end_time = ?, room_name = ? WHERE schedule_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schedule.getDepartment());
            stmt.setString(2, schedule.getSubject());
            stmt.setInt(3, schedule.getYear());
            stmt.setInt(4, schedule.getSemester());
            stmt.setString(5, schedule.getDay());
            stmt.setString(6, schedule.getStartTime());
            stmt.setString(7, schedule.getEndTime());
            stmt.setString(8, schedule.getRoomName());
            stmt.setInt(9, schedule.getScheduleId());
            stmt.executeUpdate();
        }
    }

    public void deleteSchedule(int scheduleId) throws SQLException {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scheduleId);
            stmt.executeUpdate();
        }
    }
}
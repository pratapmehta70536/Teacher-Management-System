package com.teachermanagement.dao;

import com.teachermanagement.model.Attendance;
import com.teachermanagement.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public void markAttendance(Attendance attendance, int teacherId) throws SQLException {
        String sql = "INSERT INTO attendance (day, date, status, remarks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, attendance.getDay());
            stmt.setString(2, attendance.getDate());
            stmt.setString(3, attendance.getStatus());
            stmt.setString(4, attendance.getRemarks());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int attendanceId = rs.getInt(1);
                String linkSql = "INSERT INTO teacher_attendance (teacher_id, attendance_id) VALUES (?, ?)";
                try (PreparedStatement linkStmt = conn.prepareStatement(linkSql)) {
                    linkStmt.setInt(1, teacherId);
                    linkStmt.setInt(2, attendanceId);
                    linkStmt.executeUpdate();
                }
            }
        }
    }
    
    
    

    public List<Attendance> getAttendanceByTeacher(int teacherId) throws SQLException {
        String sql = "SELECT a.* FROM attendance a JOIN teacher_attendance ta ON a.attendance_id = ta.attendance_id WHERE ta.teacher_id = ?";
        List<Attendance> attendances = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setDay(rs.getString("day"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setRemarks(rs.getString("remarks"));
                attendance.setCreatedAt(rs.getTimestamp("created_at"));
                attendances.add(attendance);
            }
            return attendances;
        }
    }
    
    public List<Attendance> getAttendanceByAdmin(int adminId) throws SQLException {
        String sql = "SELECT a.attendance_id, a.day, a.date, a.status, a.remarks, a.created_at FROM teacher t INNER JOIN teacher_attendance ta ON t.teacher_id = ta.teacher_id INNER JOIN attendance a ON ta.attendance_id = a.attendance_id INNER JOIN teacher_admin ta_admin ON t.teacher_id = ta_admin.teacher_id WHERE ta_admin.admin_id = ?;";
        List<Attendance> attendances = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setDay(rs.getString("day"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setRemarks(rs.getString("remarks"));
                attendance.setCreatedAt(rs.getTimestamp("created_at"));
                attendances.add(attendance);
            }
            return attendances;
        }
    }

    public void updateAttendance(Attendance attendance) throws SQLException {
        String sql = "UPDATE attendance SET day = ?, date = ?, status = ?, remarks = ? WHERE attendance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, attendance.getDay());
            stmt.setString(2, attendance.getDate());
            stmt.setString(3, attendance.getStatus());
            stmt.setString(4, attendance.getRemarks());
            stmt.setInt(5, attendance.getAttendanceId());
            stmt.executeUpdate();
        }
    }

    public void deleteAttendance(int attendanceId) throws SQLException {
        String sql = "DELETE FROM attendance WHERE attendance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            stmt.executeUpdate();
        }
    }
    
    

    public boolean canEditAttendance(int attendanceId) throws SQLException {
        String sql = "SELECT created_at FROM attendance WHERE attendance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp createdAt = rs.getTimestamp("created_at");
                long diff = System.currentTimeMillis() - createdAt.getTime();
                return diff <= 2 * 60 * 1000; // 2 minutes
            }
            return false;
        }
    }
    
    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE attendance_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, attendanceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(rs.getInt("attendance_id"));
                attendance.setDay(rs.getString("day"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("status"));
                attendance.setRemarks(rs.getString("remarks"));
                return attendance;
            }
            return null;
        }
    }
    
   }

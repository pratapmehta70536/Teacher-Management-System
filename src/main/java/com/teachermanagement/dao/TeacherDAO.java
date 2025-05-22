package com.teachermanagement.dao;

import com.teachermanagement.model.Teacher;
import com.teachermanagement.util.DatabaseConnection;
import com.teachermanagement.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TeacherDAO 
{
    public void addTeacher(Teacher teacher, int adminId) throws SQLException 
    {
        String sql = "INSERT INTO teacher (teacher_name, teacher_email, teacher_phone, teacher_salary, teacher_authentication_code, teacher_subject, teacher_start_time, teacher_end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) 
        {
            stmt.setString(1, teacher.getTeacherName());
            stmt.setString(2, teacher.getTeacherEmail());
            stmt.setString(3, teacher.getTeacherPhone());
            stmt.setDouble(4, teacher.getTeacherSalary());
            stmt.setString(5, PasswordUtil.encrypt(teacher.getTeacherAuthenticationCode())); // Encrypt auth code
            stmt.setString(6, teacher.getTeacherTeachingSubject());
            stmt.setString(7, teacher.getTeacherStartTime());
            stmt.setString(8, teacher.getTeacherEndTime());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int teacherId = rs.getInt(1);
                String linkSql = "INSERT INTO teacher_admin (admin_id, teacher_id) VALUES (?, ?)";
                try (PreparedStatement linkStmt = conn.prepareStatement(linkSql)) {
                    linkStmt.setInt(1, adminId);
                    linkStmt.setInt(2, teacherId);
                    linkStmt.executeUpdate();
                }
            }
        }
    }

    public Teacher login(String authCode) throws SQLException 
    {
        String sql = "SELECT * FROM teacher WHERE teacher_authentication_code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, PasswordUtil.encrypt(authCode)); // Compare encrypted auth code
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setTeacherName(rs.getString("teacher_name"));
                teacher.setTeacherEmail(rs.getString("teacher_email"));
                teacher.setTeacherPhone(rs.getString("teacher_phone"));
                teacher.setTeacherSalary(rs.getDouble("teacher_salary"));
                teacher.setTeacherAuthenticationCode(authCode); // Store plain auth code in model for display
                teacher.setTeacherTeachingSubject(rs.getString("teacher_subject"));
                teacher.setTeacherStartTime(rs.getString("teacher_start_time"));
                teacher.setTeacherEndTime(rs.getString("teacher_end_time"));
                return teacher;
            }
            return null;
        }
    }

    public List<Teacher> getAllTeachers(int adminId) throws SQLException {
        String sql = "SELECT t.* FROM teacher t JOIN teacher_admin ta ON t.teacher_id = ta.teacher_id WHERE ta.admin_id = ?";
        List<Teacher> teachers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setTeacherName(rs.getString("teacher_name"));
                teacher.setTeacherEmail(rs.getString("teacher_email"));
                teacher.setTeacherPhone(rs.getString("teacher_phone"));
                teacher.setTeacherSalary(rs.getDouble("teacher_salary"));
                teacher.setTeacherTeachingSubject(rs.getString("teacher_subject"));
                teacher.setTeacherStartTime(rs.getString("teacher_start_time"));
                teacher.setTeacherEndTime(rs.getString("teacher_end_time"));
                String encryptedAuthCode = rs.getString("teacher_authentication_code");
                String authCode = "";
                if (encryptedAuthCode != null) {
                    authCode = new String(Base64.getDecoder().decode(encryptedAuthCode));
                }
                teacher.setTeacherAuthenticationCode(authCode);
                teachers.add(teacher);
            }
            	
            return teachers;
        }
    }

    public Teacher getTeacherById(int teacherId) throws SQLException {
        String sql = "SELECT * FROM teacher WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("teacher_id"));
                teacher.setTeacherName(rs.getString("teacher_name"));
                teacher.setTeacherEmail(rs.getString("teacher_email"));
                teacher.setTeacherPhone(rs.getString("teacher_phone"));
                teacher.setTeacherSalary(rs.getDouble("teacher_salary"));
                teacher.setTeacherTeachingSubject(rs.getString("teacher_subject"));
                teacher.setTeacherStartTime(rs.getString("teacher_start_time"));
                teacher.setTeacherEndTime(rs.getString("teacher_end_time"));
                String encryptedAuthCode = rs.getString("teacher_authentication_code");
                String authCode = "";
                if (encryptedAuthCode != null) {
                    authCode = new String(Base64.getDecoder().decode(encryptedAuthCode));
                }
                teacher.setTeacherAuthenticationCode(authCode);
                return teacher;
            }
            return null;
        }
    }
    
    
    

    public void updateTeacher(Teacher teacher) throws SQLException {
        String sql = "UPDATE teacher SET teacher_name = ?, teacher_email = ?, teacher_phone = ?, teacher_salary = ?, teacher_authentication_code = ?, teacher_subject = ?, teacher_start_time = ?, teacher_end_time = ? WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacher.getTeacherName());
            stmt.setString(2, teacher.getTeacherEmail());
            stmt.setString(3, teacher.getTeacherPhone());
            stmt.setDouble(4, teacher.getTeacherSalary());
            stmt.setString(5, PasswordUtil.encrypt(teacher.getTeacherAuthenticationCode())); // Encrypt auth code
            stmt.setString(6, teacher.getTeacherTeachingSubject());
            stmt.setString(7, teacher.getTeacherStartTime());
            stmt.setString(8, teacher.getTeacherEndTime());
            stmt.setInt(9, teacher.getTeacherId());
            stmt.executeUpdate();
        }
    }

    public void deleteTeacher(int teacherId) throws SQLException {
        String sql = "DELETE FROM teacher WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            stmt.executeUpdate();
        }
    }
    
    
}

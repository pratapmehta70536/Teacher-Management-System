package com.teachermanagement.dao;

import com.teachermanagement.model.Admin;

import com.teachermanagement.util.DatabaseConnection;
import com.teachermanagement.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public void signup(Admin admin) throws SQLException {
        String sql = "INSERT INTO admin (admin_name, admin_password, admin_email, admin_phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, PasswordUtil.encrypt(admin.getAdminPassword())); // Updated to use encrypt
            stmt.setString(3, admin.getAdminEmail());
            stmt.setString(4, admin.getAdminPhone());
            stmt.executeUpdate();
        }
    }

    public Admin login(String adminName, String password) throws SQLException {
        String sql = "SELECT * FROM admin WHERE admin_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adminName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && PasswordUtil.verify(password, rs.getString("admin_password"))) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setAdminEmail(rs.getString("admin_email"));
                admin.setAdminPhone(rs.getString("admin_phone"));
                return admin;
            }
            return null;
        }
    }

    public Admin getAdminById(int adminId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setAdminEmail(rs.getString("admin_email"));
                admin.setAdminPhone(rs.getString("admin_phone"));
                return admin;
            }
            return null;
        }
    }
    
   

}

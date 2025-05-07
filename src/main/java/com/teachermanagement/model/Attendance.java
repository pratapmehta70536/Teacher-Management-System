package com.teachermanagement.model;

import java.sql.Timestamp;

public class Attendance {
    private int attendanceId;
    private String day;
    private String date;
    private String status;
    private String remarks;
    private Timestamp createdAt;

    // Getters and Setters
    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
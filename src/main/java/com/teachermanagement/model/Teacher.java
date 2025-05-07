package com.teachermanagement.model;

public class Teacher {
    private int teacherId;
    private String teacherName;
    private String teacherEmail;
    private String teacherPhone;
    private double salary;
    private byte[] teacherPicture;
    private String teacherAuthenticationCode;

    // Getters and Setters
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getTeacherEmail() { return teacherEmail; }
    public void setTeacherEmail(String teacherEmail) { this.teacherEmail = teacherEmail; }
    public String getTeacherPhone() { return teacherPhone; }
    public void setTeacherPhone(String teacherPhone) { this.teacherPhone = teacherPhone; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public byte[] getTeacherPicture() { return teacherPicture; }
    public void setTeacherPicture(byte[] teacherPicture) { this.teacherPicture = teacherPicture; }
    public String getTeacherAuthenticationCode() { return teacherAuthenticationCode; }
    public void setTeacherAuthenticationCode(String teacherAuthenticationCode) { this.teacherAuthenticationCode = teacherAuthenticationCode; }
}
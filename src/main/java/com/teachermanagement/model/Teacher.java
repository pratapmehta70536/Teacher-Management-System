package com.teachermanagement.model;

public class Teacher 

{
    private int teacherId;
    private String teacherName;
    private String teacherEmail;
    private String teacherPhone;
    private double teachersalary;
    private String teacherAuthenticationCode;
    private String teacherTeachingSubject;
    private String teacherStartTime;
    private String teacherEndTime;
    
 // Getters and Setters
	public int getTeacherId() 
	{
		return teacherId;
	}
	public void setTeacherId(int teacherId) 
	{
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) 
	{
		this.teacherName = teacherName;
	}
	public String getTeacherEmail() 
	{
		return teacherEmail;
	}
	public void setTeacherEmail(String teacherEmail) 
	{
		this.teacherEmail = teacherEmail;
	}
	public String getTeacherPhone() 
	{
		return teacherPhone;
	}
	public void setTeacherPhone(String teacherPhone) 
	{
		this.teacherPhone = teacherPhone;
	}
	public double getTeacherSalary() 
	{
		return teachersalary;
	}
	public void setTeacherSalary(double salary) 
	{
		this.teachersalary = salary;
	}
	public String getTeacherAuthenticationCode() 
	{
		return teacherAuthenticationCode;
	}
	public void setTeacherAuthenticationCode(String teacherAuthenticationCode) 
	{
		this.teacherAuthenticationCode = teacherAuthenticationCode;
	}
	public String getTeacherTeachingSubject() 
	{
		return teacherTeachingSubject;
	}
	public void setTeacherTeachingSubject(String teacherTeachingSubject) 
	{
		this.teacherTeachingSubject = teacherTeachingSubject;
	}
	public String getTeacherStartTime() 
	{
		return teacherStartTime;
	}
	public void setTeacherStartTime(String teacherStartTime) 
	{
		this.teacherStartTime = teacherStartTime;
	}
	public String getTeacherEndTime() 
	{
		return teacherEndTime;
	}
	public void setTeacherEndTime(String teacherEndTime) 
	{
		this.teacherEndTime = teacherEndTime;
	}
    
}

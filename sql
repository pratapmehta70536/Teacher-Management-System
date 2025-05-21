-- Create the database
CREATE DATABASE IF NOT EXISTS teacher_management;
USE teacher_management;

-- Create the admin table
CREATE TABLE IF admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_name VARCHAR(30) NOT NULL UNIQUE,
    admin_password VARCHAR(50) NOT NULL,
    admin_email VARCHAR(50) NOT NULL UNIQUE,
    admin_phone VARCHAR(15)
);

-- Create the teacher table
CREATE TABLE teacher (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_name VARCHAR(30) NOT NULL,
    teacher_email VARCHAR(50) NOT NULL UNIQUE,
    teacher_phone VARCHAR(15),
    teacher_salary DECIMAL(10, 2),
    teacher_authentication_code VARCHAR(15) NOT NULL UNIQUE,
    ADD COLUMN teacher_subject VARCHAR(30) NOT NULL,
    ADD COLUMN teacher_start_time VARCHAR(20) NOT NULL,
    ADD COLUMN teacher_end_time VARCHAR(20) NOT NULL;
);

-- Junction table for admin and teacher relationship
CREATE TABLE teacher_admin (
    admin_id INT,
    teacher_id INT,
    PRIMARY KEY (admin_id, teacher_id),
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE
);

-- Create the attendance table
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    day VARCHAR(10) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Junction table for teacher and attendance relationship
CREATE TABLE teacher_attendance (
    teacher_id INT,
    attendance_id INT,
    PRIMARY KEY (teacher_id, attendance_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE,
    FOREIGN KEY (attendance_id) REFERENCES attendance(attendance_id) ON DELETE CASCADE
);

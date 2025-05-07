-- Create the database
CREATE DATABASE IF NOT EXISTS teacher_management;
USE teacher_management;

-- Create the admin table
CREATE TABLE IF admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_name VARCHAR(50) NOT NULL UNIQUE,
    admin_password VARCHAR(255) NOT NULL,
    admin_email VARCHAR(100) NOT NULL UNIQUE,
    admin_phone VARCHAR(15)
);

-- Create the teacher table
CREATE TABLE teacher (
    teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_name VARCHAR(100) NOT NULL,
    teacher_email VARCHAR(100) NOT NULL UNIQUE,
    teacher_phone VARCHAR(15),
    salary DECIMAL(10, 2),
    teacher_picture LONGBLOB,
    teacher_authentication_code VARCHAR(50) NOT NULL UNIQUE
);

-- Junction table for admin and teacher relationship
CREATE TABLE teacher_admin (
    admin_id INT,
    teacher_id INT,
    PRIMARY KEY (admin_id, teacher_id),
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE
);

-- Create the schedule table
CREATE TABLE IF schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    department VARCHAR(50) NOT NULL,
    subject VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    semester INT NOT NULL,
    day VARCHAR(10) NOT NULL,
    start_time VARCHAR(20) NOT NULL,
    end_time VARCHAR(20) NOT NULL,
    room_name VARCHAR(50) NOT NULL
);

-- Junction table for teacher and schedule relationship
CREATE TABLE teacher_schedule (
    teacher_id INT,
    schedule_id INT,
    PRIMARY KEY (teacher_id, schedule_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE CASCADE,
    FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id) ON DELETE CASCADE
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

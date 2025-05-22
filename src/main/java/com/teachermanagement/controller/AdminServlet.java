package com.teachermanagement.controller;

import com.teachermanagement.dao.AdminDAO;

import com.teachermanagement.dao.TeacherDAO;
import com.teachermanagement.dao.AttendanceDAO;
import com.teachermanagement.model.Admin;
import com.teachermanagement.model.Teacher;
import com.teachermanagement.model.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private AdminDAO adminDAO;
    private TeacherDAO teacherDAO;
    private AttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException 
    {
        adminDAO = new AdminDAO();
        teacherDAO = new TeacherDAO();
        attendanceDAO = new AttendanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getPathInfo();
        try {
            switch (action == null ? "/" : action) 
            {
                case "/signup":
                    request.getRequestDispatcher("/jsp/admin/signup.jsp").forward(request, response);
                    break;
                case "/login":
                    request.getRequestDispatcher("/jsp/admin/login.jsp").forward(request, response);
                    break;
                case "/dashboard":
                    showDashboard(request, response);
                    break;
                case "/manage_teachers":
                    showTeachers(request, response);
                    break;
                case "/manage_attendance":
                    showAttendance(request, response);
                    break;
                case "/profile":
                    showProfile(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                case "/edit_teacher":
                    editTeacher(request, response);
                    break;
                case "/edit_attendance":
                    editAttendance(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } 
        catch (SQLException e) 
        {
            throw new ServletException("Database error", e);
        }
    }


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action == null ? "/" : action) 
            {
                case "/signup":
                    signup(request, response);
                    break;
                case "/login":
                    login(request, response);
                    break;
                case "/add_teacher":
                    addTeacher(request, response);
                    break;
                case "/update_teacher":
                    updateTeacher(request, response);
                    break;
                case "/delete_teacher":
                    deleteTeacher(request, response);
                    break;
                case "/add_attendance":
                    addAttendance(request, response);
                    break;
                case "/update_attendance":
                    updateAttendance(request, response);
                    break;
                case "/delete_attendance":
                    deleteAttendance(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } 
        catch (SQLException e) 
        {
            throw new ServletException("Database error", e);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException 
    {
        Admin admin = new Admin();
        admin.setAdminName(request.getParameter("admin_name"));
        admin.setAdminPassword(request.getParameter("admin_password"));
        admin.setAdminEmail(request.getParameter("admin_email"));
        admin.setAdminPhone(request.getParameter("admin_phone"));
        adminDAO.signup(admin);
        response.sendRedirect("login");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException 
    {
        String adminEmail = request.getParameter("admin_email");
        String password = request.getParameter("admin_password");
        String rememberMe = request.getParameter("rememberMe"); // Get checkbox value
        Admin admin = adminDAO.login(adminEmail, password);
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            
            // Check if "Remember Me" is selected
            if ("on".equals(rememberMe)) {
                // Create cookie for admin ID
                Cookie adminIdCookie = new Cookie("adminId", String.valueOf(admin.getAdminId()));
                adminIdCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                adminIdCookie.setHttpOnly(true); // Enhance security
                response.addCookie(adminIdCookie);
                
                // Create cookie for admin email
                Cookie emailCookie = new Cookie("adminEmail", adminEmail);
                emailCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                emailCookie.setHttpOnly(true); // Enhance security
                response.addCookie(emailCookie);
                
                // Debug: Log cookie creation
                System.out.println("Cookies set: adminId=" + admin.getAdminId() + ", adminEmail=" + adminEmail);
            } 
            
            else 
            {
                // Debug: Log when cookies are not set
                System.out.println("Remember Me not checked, no cookies set.");
            }
            
            response.sendRedirect("dashboard");
        } 
        
        else 
        {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/jsp/admin/login.jsp").forward(request, response);
        }
    }
    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException 
    {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) 
        {
            request.getRequestDispatcher("/jsp/admin/dashboard.jsp").forward(request, response);
        } 
        
        else 
        {
            response.sendRedirect("login");
        }
    }

    private void showTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) 
        {
            Admin admin = (Admin) session.getAttribute("admin");
            List<Teacher> teachers = teacherDAO.getAllTeachers(admin.getAdminId());
            request.setAttribute("teachers", teachers);
            request.getRequestDispatcher("/jsp/admin/manage_teachers.jsp").forward(request, response);
        } 
        else 
        {
            response.sendRedirect("login");
        }
    }
    

    private void addTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) 
        {
            Admin admin = (Admin) session.getAttribute("admin");
            Teacher teacher = new Teacher();
            teacher.setTeacherName(request.getParameter("teacher_name"));
            teacher.setTeacherEmail(request.getParameter("teacher_email"));
            teacher.setTeacherPhone(request.getParameter("teacher_phone"));
            teacher.setTeacherSalary(Double.parseDouble(request.getParameter("teacher_salary")));
            teacher.setTeacherAuthenticationCode(request.getParameter("teacher_authentication_code"));
            teacher.setTeacherTeachingSubject(request.getParameter("teacher_subject"));
            teacher.setTeacherStartTime(request.getParameter("teacher_start_time"));
            teacher.setTeacherEndTime(request.getParameter("teacher_end_time"));
            teacherDAO.addTeacher(teacher, admin.getAdminId());
            response.sendRedirect("manage_teachers");
        } 
        else 
        {
            response.sendRedirect("login");
        }
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException 
    {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            Teacher teacher = new Teacher();
            teacher.setTeacherId(Integer.parseInt(request.getParameter("teacher_id")));
            teacher.setTeacherName(request.getParameter("teacher_name"));
            teacher.setTeacherEmail(request.getParameter("teacher_email"));
            teacher.setTeacherPhone(request.getParameter("teacher_phone"));
            teacher.setTeacherSalary(Double.parseDouble(request.getParameter("teacher_salary")));
            teacher.setTeacherAuthenticationCode(request.getParameter("teacher_authentication_code"));
            teacher.setTeacherTeachingSubject(request.getParameter("teacher_subject"));
            teacher.setTeacherStartTime(request.getParameter("teacher_start_time"));
            teacher.setTeacherEndTime(request.getParameter("teacher_end_time"));
            teacherDAO.updateTeacher(teacher);
            response.sendRedirect("manage_teachers");
        } 
        
        else 
        {
            response.sendRedirect("login");
        }
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException 
    {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) 
        {
            int teacherId = Integer.parseInt(request.getParameter("teacher_id"));
            teacherDAO.deleteTeacher(teacherId);
            response.sendRedirect("manage_teachers");
        } 
        
        else 
        {
            response.sendRedirect("login");
        }
    }

    
   
     
    
    private void addAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            Attendance attendance = new Attendance();
            attendance.setDay(request.getParameter("day"));
            attendance.setDate(request.getParameter("date"));
            attendance.setStatus(request.getParameter("status"));
            attendance.setRemarks(request.getParameter("remarks"));
            int teacherId = Integer.parseInt(request.getParameter("teacher_id"));
            attendanceDAO.markAttendance(attendance, teacherId);
            response.sendRedirect("manage_attendance");
        } else {
            response.sendRedirect("login");
        }
    }


    private void showAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
        	 Admin admin = (Admin) session.getAttribute("admin");
             List<Attendance> attendances = attendanceDAO.getAttendanceByAdmin(admin.getAdminId());
            request.setAttribute("attendances", attendances);
            request.getRequestDispatcher("/jsp/admin/manage_attendance.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    
    private void updateAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            Attendance attendance = new Attendance();
            attendance.setAttendanceId(Integer.parseInt(request.getParameter("attendance_id")));
            attendance.setDay(request.getParameter("day"));
            attendance.setDate(request.getParameter("date"));
            attendance.setStatus(request.getParameter("status"));
            attendance.setRemarks(request.getParameter("remarks"));
            attendanceDAO.updateAttendance(attendance);
            response.sendRedirect("manage_attendance");
        } else {
            response.sendRedirect("login");
        }
    }
    
    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            int attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
            attendanceDAO.deleteAttendance(attendanceId);
            response.sendRedirect("manage_attendance");
        } else {
            response.sendRedirect("login");
        }
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            request.getRequestDispatcher("/jsp/admin/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie("adminId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("login");
    }
    
    
    

    private void editTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            int teacherId = Integer.parseInt(request.getParameter("teacher_id"));
            Teacher teacher = teacherDAO.getTeacherById(teacherId); // Assuming getTeacherById is implemented in TeacherDAO
            if (teacher != null) {
                request.setAttribute("teacher", teacher);
                request.getRequestDispatcher("/jsp/admin/edit_teacher.jsp").forward(request, response);
            } else {
                response.sendRedirect("manage_teachers");
            }
        } else {
            response.sendRedirect("login");
        }
    }
    
    private void editAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            int attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
            Attendance attendance = attendanceDAO.getAttendanceById(attendanceId); // Assuming getTeacherById is implemented in TeacherDAO
            if (attendance != null) {
                request.setAttribute("attendance", attendance);
                request.getRequestDispatcher("/jsp/admin/edit_attendance.jsp").forward(request, response);
            } else {
                response.sendRedirect("manage_attendance");
            }
        } else {
            response.sendRedirect("login");
        }
    }
    
   

}

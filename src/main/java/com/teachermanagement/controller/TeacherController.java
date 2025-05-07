package com.teachermanagement.controller;

import com.teachermanagement.dao.TeacherDAO;
import com.teachermanagement.dao.ScheduleDAO;
import com.teachermanagement.dao.AttendanceDAO;
import com.teachermanagement.model.Teacher;
import com.teachermanagement.model.Schedule;
import com.teachermanagement.model.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/*")
public class TeacherController extends HttpServlet {
    private TeacherDAO teacherDAO;
    private ScheduleDAO scheduleDAO;
    private AttendanceDAO attendanceDAO;

    @Override
    public void init() throws ServletException {
        teacherDAO = new TeacherDAO();
        scheduleDAO = new ScheduleDAO();
        attendanceDAO = new AttendanceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action == null ? "/" : action) {
                case "/login":
                    request.getRequestDispatcher("/jsp/teacher/login.jsp").forward(request, response);
                    break;
                case "/dashboard":
                    showDashboard(request, response);
                    break;
                case "/view_schedules":
                    showSchedules(request, response);
                    break;
                case "/view_attendance":
                    showAttendance(request, response);
                    break;
                case "/mark_attendance":
                    showMarkAttendance(request, response);
                    break;
                case "/profile":
                    showProfile(request, response);
                    break;
                case "/logout":
                    logout(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            switch (action == null ? "/" : action) {
                case "/login":
                    login(request, response);
                    break;
                case "/mark_attendance":
                    markAttendance(request, response);
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
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String authCode = request.getParameter("auth_code");
        Teacher teacher = teacherDAO.login(authCode);
        if (teacher != null) {
            HttpSession session = request.getSession();
            session.setAttribute("teacher", teacher);
            Cookie cookie = new Cookie("teacherId", String.valueOf(teacher.getTeacherId()));
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            response.sendRedirect("dashboard");
        } else {
            request.setAttribute("error", "Invalid authentication code");
            request.getRequestDispatcher("/jsp/teacher/login.jsp").forward(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            request.getRequestDispatcher("/jsp/teacher/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void showSchedules(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            List<Schedule> schedules = scheduleDAO.getSchedulesByTeacher(teacher.getTeacherId());
            request.setAttribute("schedules", schedules);
            request.getRequestDispatcher("/jsp/teacher/view_schedules.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void showAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            List<Attendance> attendances = attendanceDAO.getAttendanceByTeacher(teacher.getTeacherId());
            request.setAttribute("attendances", attendances);
            request.getRequestDispatcher("/jsp/teacher/view_attendance.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void showMarkAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            request.getRequestDispatcher("/jsp/teacher/mark_attendance.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void markAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            Teacher teacher = (Teacher) session.getAttribute("teacher");
            Attendance attendance = new Attendance();
            attendance.setDay(request.getParameter("day"));
            attendance.setDate(request.getParameter("date"));
            attendance.setStatus(request.getParameter("status"));
            attendance.setRemarks(request.getParameter("remarks"));
            attendanceDAO.markAttendance(attendance, teacher.getTeacherId());
            response.sendRedirect("view_attendance");
        } else {
            response.sendRedirect("login");
        }
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            int attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
            if (attendanceDAO.canEditAttendance(attendanceId)) {
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(attendanceId);
                attendance.setDay(request.getParameter("day"));
                attendance.setDate(request.getParameter("date"));
                attendance.setStatus(request.getParameter("status"));
                attendance.setRemarks(request.getParameter("remarks"));
                attendanceDAO.updateAttendance(attendance);
                response.sendRedirect("view_attendance");
            } else {
                request.setAttribute("error", "Editing time expired");
                showAttendance(request, response);
            }
        } else {
            response.sendRedirect("login");
        }
    }

    private void deleteAttendance(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            int attendanceId = Integer.parseInt(request.getParameter("attendance_id"));
            if (attendanceDAO.canEditAttendance(attendanceId)) {
                attendanceDAO.deleteAttendance(attendanceId);
                response.sendRedirect("view_attendance");
            } else {
                request.setAttribute("error", "Editing time expired");
                showAttendance(request, response);
            }
        } else {
            response.sendRedirect("login");
        }
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacher") != null) {
            request.getRequestDispatcher("/jsp/teacher/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie("teacherId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("login");
    }
}
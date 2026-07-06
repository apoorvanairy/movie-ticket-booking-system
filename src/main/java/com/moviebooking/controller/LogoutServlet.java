package com.moviebooking.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * LogoutServlet handles logging the user out.
 * It destroys the current session and sends the user back to the login page.
 */
@WebServlet("/LogoutServlet")   // Maps URL "/LogoutServlet" to this class
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the current session (false = don't create a new one if none exists)
        HttpSession session = request.getSession(false);

        // If a session exists, destroy it completely
        if (session != null) {
            session.invalidate();
        }

        // Redirect back to the login page
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
    }
}
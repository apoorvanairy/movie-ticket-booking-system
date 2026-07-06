package com.moviebooking.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.moviebooking.dao.UserDao;
import com.moviebooking.dao.impl.UserDaoImpl;
import com.moviebooking.model.User;

/**
 * RegisterServlet handles new user registration.
 * It reads form data from register.jsp, checks if the email is already used,
 * and if not, creates a new User in the database with role = CUSTOMER.
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Read form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        // Step 2: Check if a user with this email already exists
        User existingUser = userDao.getUserByEmail(email);

        if (existingUser != null) {
            // Email already registered — show error and go back to register page
            request.setAttribute("errorMessage", "An account with this email already exists!");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            return; // stop here, don't continue further
        }

        // Step 3: Create a new User object (role is always CUSTOMER for self-registration)
        User newUser = new User(fullName, email, password, phone, "CUSTOMER");

        // Step 4: Save to database
        boolean isRegistered = userDao.addUser(newUser);

        if (isRegistered) {
            // Registration successful — send them to login page to sign in
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        } else {
            // Something went wrong while saving — show error
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("/views/register.jsp").forward(request, response);
        }
    }

    // If someone visits /RegisterServlet directly via GET, just show the register page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }
}
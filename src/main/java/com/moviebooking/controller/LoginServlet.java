package com.moviebooking.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.UserDao;
import com.moviebooking.dao.impl.UserDaoImpl;
import com.moviebooking.model.User;

/**
 * LoginServlet handles the login form submission.
 * It checks the entered email/password against the database
 * and either logs the user in (creates a session) or shows an error.
 */
@WebServlet("/LoginServlet")   // Maps the URL "/LoginServlet" to this class
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // We use the interface type (UserDao) but create the actual object (UserDaoImpl)
    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Step 1: Read form data sent from login.jsp
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Step 2: Look up the user in the database by email
        User user = userDao.getUserByEmail(email);

        // Step 3: Check if user exists AND password matches
        if (user != null && user.getPassword().equals(password)) {

            // Step 4: Login successful — create a session to remember this user
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            session.setAttribute("userRole", user.getRole());

            // Step 5: Redirect based on role (these pages don't exist yet — that's next)
            if (user.getRole().equalsIgnoreCase("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/views/adminHome.jsp");
            } else {
            	response.sendRedirect(request.getContextPath() + "/MovieListServlet");            }

        } else {
            // Login failed — send user back to login page with an error message
            request.setAttribute("errorMessage", "Invalid email or password!");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

    // If someone directly visits /LoginServlet via browser URL (GET request),
    // just send them to the login page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }
}
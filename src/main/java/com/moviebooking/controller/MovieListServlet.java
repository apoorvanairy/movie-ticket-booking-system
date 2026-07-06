package com.moviebooking.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.MovieDao;
import com.moviebooking.dao.impl.MovieDaoImpl;
import com.moviebooking.model.Movie;
import com.moviebooking.model.User;

/**
 * MovieListServlet fetches all movies from the database
 * and forwards them to home.jsp for display.
 * This becomes the main landing page after a customer logs in.
 */
@WebServlet("/MovieListServlet")
public class MovieListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MovieDao movieDao = new MovieDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---------- SESSION PROTECTION ----------
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // Step 1: Fetch all movies from the database
        List<Movie> movieList = movieDao.getAllMovies();

        // Step 2: Attach the movie list to the request so the JSP can access it
        request.setAttribute("movieList", movieList);

        // Step 3: Forward to home.jsp to display the movies
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }
}
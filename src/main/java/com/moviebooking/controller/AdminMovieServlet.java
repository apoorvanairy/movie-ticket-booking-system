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

@WebServlet("/AdminMovieServlet")
public class AdminMovieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MovieDao movieDao = new MovieDaoImpl();

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        return loggedInUser != null && loggedInUser.getRole().equalsIgnoreCase("ADMIN");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int movieId = Integer.parseInt(request.getParameter("movieId"));

            // Try to delete; check if it actually succeeded
            boolean deleted = movieDao.deleteMovie(movieId);

            List<Movie> movieList = movieDao.getAllMovies();
            request.setAttribute("movieList", movieList);

            if (!deleted) {
                // Deletion failed (likely due to existing Shows referencing this movie)
                request.setAttribute("errorMessage",
                        "Cannot delete this movie because it has active shows. Please delete its shows first.");
            }

            request.getRequestDispatcher("/views/adminMovies.jsp").forward(request, response);
            return;
        }

        // ---------- DEFAULT: LIST ALL MOVIES ----------
        List<Movie> movieList = movieDao.getAllMovies();
        request.setAttribute("movieList", movieList);
        request.getRequestDispatcher("/views/adminMovies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        String movieName = request.getParameter("movieName");
        String genre = request.getParameter("genre");
        String language = request.getParameter("language");
        int durationMinutes = Integer.parseInt(request.getParameter("durationMinutes"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        Movie newMovie = new Movie(movieName, genre, language, durationMinutes, description, imageUrl);
        movieDao.addMovie(newMovie);

        response.sendRedirect(request.getContextPath() + "/AdminMovieServlet");
    }
}
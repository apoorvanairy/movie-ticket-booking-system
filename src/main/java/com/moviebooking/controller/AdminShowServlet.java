package com.moviebooking.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.MovieDao;
import com.moviebooking.dao.ShowsDao;
import com.moviebooking.dao.TheatreDao;
import com.moviebooking.dao.impl.MovieDaoImpl;
import com.moviebooking.dao.impl.ShowsDaoImpl;
import com.moviebooking.dao.impl.TheatreDaoImpl;
import com.moviebooking.model.Movie;
import com.moviebooking.model.Shows;
import com.moviebooking.model.Theatre;
import com.moviebooking.model.User;

/**
 * AdminShowServlet handles all admin actions related to Shows:
 * - Viewing all shows (with movie name + theatre name attached)
 * - Adding a new show (linking a Movie + Theatre with date/time/price/seats)
 * - Deleting an existing show
 */
@WebServlet("/AdminShowServlet")
public class AdminShowServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ShowsDao showsDao = new ShowsDaoImpl();
    private MovieDao movieDao = new MovieDaoImpl();
    private TheatreDao theatreDao = new TheatreDaoImpl();

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
            int showId = Integer.parseInt(request.getParameter("showId"));
            showsDao.deleteShow(showId);
            response.sendRedirect(request.getContextPath() + "/AdminShowServlet");
            return;
        }

        // ---------- DEFAULT: LIST ALL SHOWS + DROPDOWN DATA ----------
        List<Shows> showList = showsDao.getAllShows();

        // For each show, also fetch the movie name and theatre name (same pattern as before)
        List<Movie> movieForEachShow = new ArrayList<>();
        List<Theatre> theatreForEachShow = new ArrayList<>();
        for (Shows show : showList) {
            movieForEachShow.add(movieDao.getMovieById(show.getMovieId()));
            theatreForEachShow.add(theatreDao.getTheatreById(show.getTheatreId()));
        }

        // Fetch all movies and theatres for the dropdowns in the "Add Show" form
        List<Movie> allMovies = movieDao.getAllMovies();
        List<Theatre> allTheatres = theatreDao.getAllTheatres();

        request.setAttribute("showList", showList);
        request.setAttribute("movieForEachShow", movieForEachShow);
        request.setAttribute("theatreForEachShow", theatreForEachShow);
        request.setAttribute("allMovies", allMovies);
        request.setAttribute("allTheatres", allTheatres);

        request.getRequestDispatcher("/views/adminShows.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // ---------- ADD NEW SHOW ----------
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        int theatreId = Integer.parseInt(request.getParameter("theatreId"));
        Date showDate = Date.valueOf(request.getParameter("showDate"));       // expects "yyyy-MM-dd"
        Time showTime = Time.valueOf(request.getParameter("showTime") + ":00"); // expects "HH:mm", we append seconds
        double ticketPrice = Double.parseDouble(request.getParameter("ticketPrice"));
        int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));

        Shows newShow = new Shows(movieId, theatreId, showDate, showTime, ticketPrice, availableSeats);
        showsDao.addShow(newShow);

        response.sendRedirect(request.getContextPath() + "/AdminShowServlet");
    }
}
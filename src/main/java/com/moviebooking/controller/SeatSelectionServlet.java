package com.moviebooking.controller;

import java.io.IOException;

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
 * SeatSelectionServlet shows the ticket quantity selection page
 * for a specific show. Since we don't track individual seat numbers,
 * "seat selection" here simply means choosing how many tickets to book.
 */
@WebServlet("/SeatSelectionServlet")
public class SeatSelectionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ShowsDao showsDao = new ShowsDaoImpl();
    private MovieDao movieDao = new MovieDaoImpl();
    private TheatreDao theatreDao = new TheatreDaoImpl();

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

        // Step 1: Read showId from the URL
        int showId = Integer.parseInt(request.getParameter("showId"));

        // Step 2: Fetch the show, movie, and theatre details
        Shows show = showsDao.getShowById(showId);
        Movie movie = movieDao.getMovieById(show.getMovieId());
        Theatre theatre = theatreDao.getTheatreById(show.getTheatreId());

        // Step 3: Attach everything to the request
        request.setAttribute("show", show);
        request.setAttribute("movie", movie);
        request.setAttribute("theatre", theatre);

        // Step 4: Forward to seatSelection.jsp
        request.getRequestDispatcher("/views/seatSelection.jsp").forward(request, response);
    }
}
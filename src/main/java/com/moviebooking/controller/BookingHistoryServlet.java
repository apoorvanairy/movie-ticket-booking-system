package com.moviebooking.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.BookingDao;
import com.moviebooking.dao.MovieDao;
import com.moviebooking.dao.ShowsDao;
import com.moviebooking.dao.TheatreDao;
import com.moviebooking.dao.impl.BookingDaoImpl;
import com.moviebooking.dao.impl.MovieDaoImpl;
import com.moviebooking.dao.impl.ShowsDaoImpl;
import com.moviebooking.dao.impl.TheatreDaoImpl;
import com.moviebooking.model.Booking;
import com.moviebooking.model.Movie;
import com.moviebooking.model.Shows;
import com.moviebooking.model.Theatre;
import com.moviebooking.model.User;

/**
 * BookingHistoryServlet fetches all past bookings made by the logged-in user,
 * along with the movie and theatre details for each booking,
 * and forwards everything to bookingHistory.jsp for display.
 */
@WebServlet("/BookingHistoryServlet")
public class BookingHistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private BookingDao bookingDao = new BookingDaoImpl();
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

        // Step 1: Fetch all bookings made by this user
        List<Booking> bookingList = bookingDao.getBookingsByUserId(loggedInUser.getUserId());

        // Step 2: For each booking, fetch the related Show, Movie, and Theatre
        // We keep these in parallel lists, matched by position (same pattern as ShowListServlet)
        List<Shows> showList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        List<Theatre> theatreList = new ArrayList<>();

        for (Booking booking : bookingList) {
            Shows show = showsDao.getShowById(booking.getShowId());
            showList.add(show);

            if (show != null) {
                movieList.add(movieDao.getMovieById(show.getMovieId()));
                theatreList.add(theatreDao.getTheatreById(show.getTheatreId()));
            } else {
                movieList.add(null);
                theatreList.add(null);
            }
        }

        // Step 3: Attach everything to the request
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("showList", showList);
        request.setAttribute("movieList", movieList);
        request.setAttribute("theatreList", theatreList);

        // Step 4: Forward to bookingHistory.jsp
        request.getRequestDispatcher("/views/bookingHistory.jsp").forward(request, response);
    }
}
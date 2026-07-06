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
 * ShowListServlet fetches all shows for a specific movie,
 * along with the theatre name for each show, and forwards
 * everything to showList.jsp for display.
 */
@WebServlet("/ShowListServlet")
public class ShowListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MovieDao movieDao = new MovieDaoImpl();
    private ShowsDao showsDao = new ShowsDaoImpl();
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

        // Step 1: Read movieId from the URL (e.g., ShowListServlet?movieId=1)
        int movieId = Integer.parseInt(request.getParameter("movieId"));

        // Step 2: Fetch the movie details (to show its name/genre at the top)
        Movie movie = movieDao.getMovieById(movieId);

        // Step 3: Fetch all shows for this movie
        List<Shows> showList = showsDao.getShowsByMovieId(movieId);

        // Step 4: For each show, also fetch the theatre name
        // We store theatre names in a separate list, matched by position with showList
        List<Theatre> theatreList = new ArrayList<>();
        for (Shows show : showList) {
            Theatre theatre = theatreDao.getTheatreById(show.getTheatreId());
            theatreList.add(theatre);
        }

        // Step 5: Attach everything to the request
        request.setAttribute("movie", movie);
        request.setAttribute("showList", showList);
        request.setAttribute("theatreList", theatreList);

        // Step 6: Forward to showList.jsp
        request.getRequestDispatcher("/views/showList.jsp").forward(request, response);
    }
}
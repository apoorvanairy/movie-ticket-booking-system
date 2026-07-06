package com.moviebooking.controller;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.BookingDao;
import com.moviebooking.dao.ShowsDao;
import com.moviebooking.dao.impl.BookingDaoImpl;
import com.moviebooking.dao.impl.ShowsDaoImpl;
import com.moviebooking.model.Booking;
import com.moviebooking.model.Shows;
import com.moviebooking.model.User;

/**
 * BookingServlet processes the actual ticket booking.
 * It checks seat availability, saves the booking to the database,
 * and reduces the available seats for that show.
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ShowsDao showsDao = new ShowsDaoImpl();
    private BookingDao bookingDao = new BookingDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ---------- SESSION PROTECTION ----------
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // Step 1: Read form data
        int showId = Integer.parseInt(request.getParameter("showId"));
        int numTickets = Integer.parseInt(request.getParameter("numTickets"));

        // Step 2: Fetch the show to check seat availability and get the price
        Shows show = showsDao.getShowById(showId);

        if (show == null || show.getAvailableSeats() < numTickets) {
            // Not enough seats — send back with an error (simple approach: redirect back)
            response.sendRedirect(request.getContextPath() + "/SeatSelectionServlet?showId=" + showId);
            return;
        }

        // Step 3: Calculate total amount
        double totalAmount = show.getTicketPrice() * numTickets;

        // Step 4: Create the Booking object
        Date today = new Date(System.currentTimeMillis());
        Booking booking = new Booking(
                loggedInUser.getUserId(),
                showId,
                today,
                numTickets,
                totalAmount,
                "CONFIRMED"
        );

        // Step 5: Save the booking to the database
        boolean bookingSaved = bookingDao.addBooking(booking);

        if (bookingSaved) {
            // Step 6: Reduce available seats for this show
            show.setAvailableSeats(show.getAvailableSeats() - numTickets);
            showsDao.updateShow(show);

            // Step 7: Redirect to a confirmation page
            request.setAttribute("booking", booking);
            request.getRequestDispatcher("/views/bookingConfirmation.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/SeatSelectionServlet?showId=" + showId);
        }
    }
}
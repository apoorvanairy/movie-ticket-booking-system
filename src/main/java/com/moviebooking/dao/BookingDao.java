package com.moviebooking.dao;

import java.util.List;
import com.moviebooking.model.Booking;

public interface BookingDao {

    boolean addBooking(Booking booking);
    Booking getBookingById(int bookingId);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUserId(int userId);
    boolean updateBooking(Booking booking);
    boolean deleteBooking(int bookingId);
}
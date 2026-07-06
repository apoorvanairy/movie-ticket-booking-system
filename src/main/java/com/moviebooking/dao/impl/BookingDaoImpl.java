package com.moviebooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.dao.BookingDao;
import com.moviebooking.dbutil.DBConnection;
import com.moviebooking.model.Booking;

public class BookingDaoImpl implements BookingDao {

    @Override
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Booking (user_id, show_id, booking_date, num_tickets, total_amount, booking_status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getShowId());
            ps.setDate(3, booking.getBookingDate());
            ps.setInt(4, booking.getNumTickets());
            ps.setDouble(5, booking.getTotalAmount());
            ps.setString(6, booking.getBookingStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding booking!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM Booking WHERE booking_id = ?";
        Booking booking = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                booking = new Booking(
                        rs.getInt("booking_id"), rs.getInt("user_id"), rs.getInt("show_id"),
                        rs.getDate("booking_date"), rs.getInt("num_tickets"),
                        rs.getDouble("total_amount"), rs.getString("booking_status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching booking by ID!");
            e.printStackTrace();
        }
        return booking;
    }

    @Override
    public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM Booking";
        List<Booking> bookingList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                bookingList.add(new Booking(
                        rs.getInt("booking_id"), rs.getInt("user_id"), rs.getInt("show_id"),
                        rs.getDate("booking_date"), rs.getInt("num_tickets"),
                        rs.getDouble("total_amount"), rs.getString("booking_status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching all bookings!");
            e.printStackTrace();
        }
        return bookingList;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        String sql = "SELECT * FROM Booking WHERE user_id = ?";
        List<Booking> bookingList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookingList.add(new Booking(
                        rs.getInt("booking_id"), rs.getInt("user_id"), rs.getInt("show_id"),
                        rs.getDate("booking_date"), rs.getInt("num_tickets"),
                        rs.getDouble("total_amount"), rs.getString("booking_status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching bookings by user ID!");
            e.printStackTrace();
        }
        return bookingList;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE Booking SET user_id=?, show_id=?, booking_date=?, num_tickets=?, total_amount=?, booking_status=? WHERE booking_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getShowId());
            ps.setDate(3, booking.getBookingDate());
            ps.setInt(4, booking.getNumTickets());
            ps.setDouble(5, booking.getTotalAmount());
            ps.setString(6, booking.getBookingStatus());
            ps.setInt(7, booking.getBookingId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating booking!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting booking!");
            e.printStackTrace();
            return false;
        }
    }
}
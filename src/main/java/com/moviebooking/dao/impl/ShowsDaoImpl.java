package com.moviebooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.dao.ShowsDao;
import com.moviebooking.dbutil.DBConnection;
import com.moviebooking.model.Shows;

public class ShowsDaoImpl implements ShowsDao {

    @Override
    public boolean addShow(Shows show) {
        String sql = "INSERT INTO Shows (movie_id, theatre_id, show_date, show_time, ticket_price, available_seats) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, show.getMovieId());
            ps.setInt(2, show.getTheatreId());
            ps.setDate(3, show.getShowDate());
            ps.setTime(4, show.getShowTime());
            ps.setDouble(5, show.getTicketPrice());
            ps.setInt(6, show.getAvailableSeats());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding show!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Shows getShowById(int showId) {
        String sql = "SELECT * FROM Shows WHERE show_id = ?";
        Shows show = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, showId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                show = new Shows(
                        rs.getInt("show_id"), rs.getInt("movie_id"), rs.getInt("theatre_id"),
                        rs.getDate("show_date"), rs.getTime("show_time"),
                        rs.getDouble("ticket_price"), rs.getInt("available_seats")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching show by ID!");
            e.printStackTrace();
        }
        return show;
    }

    @Override
    public List<Shows> getAllShows() {
        String sql = "SELECT * FROM Shows";
        List<Shows> showList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                showList.add(new Shows(
                        rs.getInt("show_id"), rs.getInt("movie_id"), rs.getInt("theatre_id"),
                        rs.getDate("show_date"), rs.getTime("show_time"),
                        rs.getDouble("ticket_price"), rs.getInt("available_seats")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching all shows!");
            e.printStackTrace();
        }
        return showList;
    }

    @Override
    public List<Shows> getShowsByMovieId(int movieId) {
        String sql = "SELECT * FROM Shows WHERE movie_id = ?";
        List<Shows> showList = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                showList.add(new Shows(
                        rs.getInt("show_id"), rs.getInt("movie_id"), rs.getInt("theatre_id"),
                        rs.getDate("show_date"), rs.getTime("show_time"),
                        rs.getDouble("ticket_price"), rs.getInt("available_seats")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching shows by movie ID!");
            e.printStackTrace();
        }
        return showList;
    }

    @Override
    public boolean updateShow(Shows show) {
        String sql = "UPDATE Shows SET movie_id=?, theatre_id=?, show_date=?, show_time=?, ticket_price=?, available_seats=? WHERE show_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, show.getMovieId());
            ps.setInt(2, show.getTheatreId());
            ps.setDate(3, show.getShowDate());
            ps.setTime(4, show.getShowTime());
            ps.setDouble(5, show.getTicketPrice());
            ps.setInt(6, show.getAvailableSeats());
            ps.setInt(7, show.getShowId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating show!");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteShow(int showId) {
        String sql = "DELETE FROM Shows WHERE show_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, showId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting show!");
            e.printStackTrace();
            return false;
        }
    }
}
package com.moviebooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.dao.TheatreDao;
import com.moviebooking.dbutil.DBConnection;
import com.moviebooking.model.Theatre;

/**
 * TheatreDaoImpl is the IMPLEMENTATION class of TheatreDao interface.
 * Contains actual JDBC code (SQL queries) to perform CRUD operations
 * on the Theatre table.
 */
public class TheatreDaoImpl implements TheatreDao {

    // ---------- CREATE ----------
    @Override
    public boolean addTheatre(Theatre theatre) {

        String sql = "INSERT INTO Theatre (theatre_name, location) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, theatre.getTheatreName());
            ps.setString(2, theatre.getLocation());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while adding theatre!");
            e.printStackTrace();
            return false;
        }
    }

    // ---------- READ (single theatre by ID) ----------
    @Override
    public Theatre getTheatreById(int theatreId) {

        String sql = "SELECT * FROM Theatre WHERE theatre_id = ?";
        Theatre theatre = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, theatreId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                theatre = new Theatre(
                        rs.getInt("theatre_id"),
                        rs.getString("theatre_name"),
                        rs.getString("location")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching theatre by ID!");
            e.printStackTrace();
        }

        return theatre;
    }

    // ---------- READ (all theatres) ----------
    @Override
    public List<Theatre> getAllTheatres() {

        String sql = "SELECT * FROM Theatre";
        List<Theatre> theatreList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Theatre theatre = new Theatre(
                        rs.getInt("theatre_id"),
                        rs.getString("theatre_name"),
                        rs.getString("location")
                );
                theatreList.add(theatre);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching all theatres!");
            e.printStackTrace();
        }

        return theatreList;
    }

    // ---------- UPDATE ----------
    @Override
    public boolean updateTheatre(Theatre theatre) {

        String sql = "UPDATE Theatre SET theatre_name = ?, location = ? WHERE theatre_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, theatre.getTheatreName());
            ps.setString(2, theatre.getLocation());
            ps.setInt(3, theatre.getTheatreId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while updating theatre!");
            e.printStackTrace();
            return false;
        }
    }

    // ---------- DELETE ----------
    @Override
    public boolean deleteTheatre(int theatreId) {

        String sql = "DELETE FROM Theatre WHERE theatre_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, theatreId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while deleting theatre!");
            e.printStackTrace();
            return false;
        }
    }
}
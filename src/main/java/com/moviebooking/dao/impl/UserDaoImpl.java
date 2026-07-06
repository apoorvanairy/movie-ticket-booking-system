package com.moviebooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.moviebooking.dao.UserDao;
import com.moviebooking.dbutil.DBConnection;
import com.moviebooking.model.User;

public class UserDaoImpl implements UserDao {

    // ---------- CREATE ----------
    @Override
    public boolean addUser(User user) {

        String sql = "INSERT INTO User (full_name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while adding user!");
            e.printStackTrace();
            return false;
        }
    }

    // ---------- READ (single user by ID) ----------
    @Override
    public User getUserById(int userId) {

        String sql = "SELECT * FROM User WHERE user_id = ?";
        User user = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching user by ID!");
            e.printStackTrace();
        }

        return user;
    }

    // ---------- READ (single user by EMAIL) ----------
    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM User WHERE email = ?";
        User user = null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching user by email!");
            e.printStackTrace();
        }

        return user;
    }

    // ---------- READ (all users) ----------
    @Override
    public List<User> getAllUsers() {

        String sql = "SELECT * FROM User";
        List<User> userList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("role")
                );
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching all users!");
            e.printStackTrace();
        }

        return userList;
    }

    // ---------- UPDATE ----------
    @Override
    public boolean updateUser(User user) {

        String sql = "UPDATE User SET full_name = ?, email = ?, password = ?, phone = ?, role = ? WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getRole());
            ps.setInt(6, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while updating user!");
            e.printStackTrace();
            return false;
        }
    }

    // ---------- DELETE ----------
    @Override
    public boolean deleteUser(int userId) {

        String sql = "DELETE FROM User WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error while deleting user!");
            e.printStackTrace();
            return false;
        }
    }
}
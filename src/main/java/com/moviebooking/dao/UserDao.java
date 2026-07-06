package com.moviebooking.dao;

import java.util.List;
import com.moviebooking.model.User;

public interface UserDao {

    boolean addUser(User user);
    User getUserById(int userId);
    User getUserByEmail(String email);   // NEW METHOD — needed for login
    List<User> getAllUsers();
    boolean updateUser(User user);
    boolean deleteUser(int userId);
}
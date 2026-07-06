package com.moviebooking.dao;

import java.util.List;
import com.moviebooking.model.Shows;

public interface ShowsDao {

    boolean addShow(Shows show);
    Shows getShowById(int showId);
    List<Shows> getAllShows();
    List<Shows> getShowsByMovieId(int movieId);   // NEW METHOD
    boolean updateShow(Shows show);
    boolean deleteShow(int showId);
}
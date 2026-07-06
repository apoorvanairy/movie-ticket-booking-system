package com.moviebooking.dao;

import java.util.List;
import com.moviebooking.model.Movie;

/**
 * MovieDao is an INTERFACE.
 * It defines WHAT operations are possible on the Movie table.
 * The actual JDBC code will be written in MovieDaoImpl.
 */
public interface MovieDao {

    // CREATE - Add a new movie to the database
    boolean addMovie(Movie movie);

    // READ - Get a single movie by its ID
    Movie getMovieById(int movieId);

    // READ - Get all movies from the database
    List<Movie> getAllMovies();

    // UPDATE - Update an existing movie's details
    boolean updateMovie(Movie movie);

    // DELETE - Delete a movie by its ID
    boolean deleteMovie(int movieId);
}
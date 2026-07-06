package com.moviebooking.dao;

import java.util.List;
import com.moviebooking.model.Theatre;

/**
 * TheatreDao is an INTERFACE.
 * It defines WHAT operations are possible on the Theatre table.
 * The actual JDBC code will be written in TheatreDaoImpl.
 */
public interface TheatreDao {

    // CREATE - Add a new theatre to the database
    boolean addTheatre(Theatre theatre);

    // READ - Get a single theatre by its ID
    Theatre getTheatreById(int theatreId);

    // READ - Get all theatres from the database
    List<Theatre> getAllTheatres();

    // UPDATE - Update an existing theatre's details
    boolean updateTheatre(Theatre theatre);

    // DELETE - Delete a theatre by its ID
    boolean deleteTheatre(int theatreId);
}
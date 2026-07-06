package com.moviebooking.model;

import java.sql.Date;
import java.sql.Time;

public class Shows {

    private int showId;
    private int movieId;
    private int theatreId;
    private Date showDate;
    private Time showTime;
    private double ticketPrice;
    private int availableSeats;

    public Shows() {
    }

    public Shows(int movieId, int theatreId, Date showDate, Time showTime, double ticketPrice, int availableSeats) {
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public Shows(int showId, int movieId, int theatreId, Date showDate, Time showTime, double ticketPrice, int availableSeats) {
        this.showId = showId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
    }

    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public int getTheatreId() { return theatreId; }
    public void setTheatreId(int theatreId) { this.theatreId = theatreId; }

    public Date getShowDate() { return showDate; }
    public void setShowDate(Date showDate) { this.showDate = showDate; }

    public Time getShowTime() { return showTime; }
    public void setShowTime(Time showTime) { this.showTime = showTime; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    @Override
    public String toString() {
        return "Shows [showId=" + showId + ", movieId=" + movieId + ", theatreId=" + theatreId +
                ", showDate=" + showDate + ", showTime=" + showTime +
                ", ticketPrice=" + ticketPrice + ", availableSeats=" + availableSeats + "]";
    }
}
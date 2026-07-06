package com.moviebooking.model;

import java.sql.Date;

public class Booking {

    private int bookingId;
    private int userId;
    private int showId;
    private Date bookingDate;
    private int numTickets;
    private double totalAmount;
    private String bookingStatus;

    public Booking() {
    }

    public Booking(int userId, int showId, Date bookingDate, int numTickets, double totalAmount, String bookingStatus) {
        this.userId = userId;
        this.showId = showId;
        this.bookingDate = bookingDate;
        this.numTickets = numTickets;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public Booking(int bookingId, int userId, int showId, Date bookingDate, int numTickets, double totalAmount, String bookingStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.bookingDate = bookingDate;
        this.numTickets = numTickets;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public int getNumTickets() { return numTickets; }
    public void setNumTickets(int numTickets) { this.numTickets = numTickets; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", userId=" + userId + ", showId=" + showId +
                ", bookingDate=" + bookingDate + ", numTickets=" + numTickets +
                ", totalAmount=" + totalAmount + ", bookingStatus=" + bookingStatus + "]";
    }
}
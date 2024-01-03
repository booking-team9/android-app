package com.example.bookingappteam9.model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class TimeSlot {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
    private boolean isOccupied;
    @Expose(serialize = false, deserialize = false)
    private String rangeString;
    public TimeSlot(){}

    public TimeSlot(Long id, LocalDateTime startDate, LocalDateTime endDate, double price, boolean isOccupied, String rangeString) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.rangeString = rangeString;
        this.isOccupied = isOccupied;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getRangeString() {
        return rangeString;
    }

    public void setRangeString(String rangeString) {
        this.rangeString = rangeString;
    }
}

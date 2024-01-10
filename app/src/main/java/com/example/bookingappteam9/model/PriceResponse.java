package com.example.bookingappteam9.model;

public class PriceResponse {
    private boolean isAvailable;
    private double pricePerNight;
    private double totalPrice;

    public PriceResponse(boolean isAvailable, double pricePerNight, double totalPrice) {
        this.isAvailable = isAvailable;
        this.pricePerNight = pricePerNight;
        this.totalPrice = totalPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

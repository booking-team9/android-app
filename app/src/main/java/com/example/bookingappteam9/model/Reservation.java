package com.example.bookingappteam9.model;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;
    private String accommodationName;
    private String guestEmail;
    private String hostEmail;
    private Long guestId;
    private Long hostId;
    private ReservationStatus reservationStatus;
    private Integer numberOfGuests;
    private Integer guestTimesCancelled;
    private Long accommodationId;

    public Reservation(){

    }

    public Reservation(Long id, LocalDateTime startDate, LocalDateTime endDate, double price, String accommodationName, String guestEmail, String hostEmail, Long guestId, Long hostId, ReservationStatus reservationStatus, Integer numberOfGuests, Integer guestTimesCancelled, Long accommodationId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.accommodationName = accommodationName;
        this.guestEmail = guestEmail;
        this.hostEmail = hostEmail;
        this.guestId = guestId;
        this.hostId = hostId;
        this.reservationStatus = reservationStatus;
        this.numberOfGuests = numberOfGuests;
        this.guestTimesCancelled = guestTimesCancelled;
        this.accommodationId = accommodationId;
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Integer getGuestTimesCancelled() {
        return guestTimesCancelled;
    }

    public void setGuestTimesCancelled(Integer guestTimesCancelled) {
        this.guestTimesCancelled = guestTimesCancelled;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }
}

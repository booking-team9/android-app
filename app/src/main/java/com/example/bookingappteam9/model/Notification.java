package com.example.bookingappteam9.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private String message;
    private LocalDateTime date;
    private Long receiver;


    public Notification(Long id, String message, LocalDateTime date, Long receiver) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }
}

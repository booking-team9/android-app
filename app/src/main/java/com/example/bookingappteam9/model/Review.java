package com.example.bookingappteam9.model;

import java.time.LocalDateTime;

public class Review {

    private Long id;
    private Integer grade;
    private String comment;
    private ReviewStatus status;
    private LocalDateTime date;
    private Long accommodationId;
    private Long hostId;
    private User author;

    public Review(){}

    public Review(Long id, Integer grade, String comment, ReviewStatus status, LocalDateTime date, Long accommodationId, Long hostId, User author) {
        this.id = id;
        this.grade = grade;
        this.comment = comment;
        this.status = status;
        this.date = date;
        this.accommodationId = accommodationId;
        this.hostId = hostId;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}

package com.example.bookingappteam9.model;

import java.time.LocalDateTime;

public class Report {
    private Long id;
    private String reason;
    private Long authorId;
    private Long reportedUserId;
    private LocalDateTime date;

    public Report(){}

    public Report(Long id, String reason, Long authorId, Long reportedUserId, LocalDateTime date) {
        this.id = id;
        this.reason = reason;
        this.authorId = authorId;
        this.reportedUserId = reportedUserId;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

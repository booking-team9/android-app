package com.example.bookingappteam9.model;

import java.time.LocalDateTime;

public class AdminReport {
    private Long id;
    private String reason;
    private String authorEmail;
    private String reportedUserEmail;
    private Long reportedUserId;
    private LocalDateTime date;

    public AdminReport(){}

    public AdminReport(Long id, String reason, String authorEmail, String reportedUserEmail, Long reportedUserId, LocalDateTime date) {
        this.id = id;
        this.reason = reason;
        this.authorEmail = authorEmail;
        this.reportedUserEmail = reportedUserEmail;
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

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getReportedUserEmail() {
        return reportedUserEmail;
    }

    public void setReportedUserEmail(String reportedUserEmail) {
        this.reportedUserEmail = reportedUserEmail;
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

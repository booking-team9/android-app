package com.example.bookingappteam9.model;

import java.time.LocalDate;

public class PriceRequest {
    private Long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;

    public PriceRequest(Long accommodationId, LocalDate startDate, LocalDate endDate) {
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

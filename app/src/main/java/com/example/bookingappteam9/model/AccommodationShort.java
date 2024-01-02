package com.example.bookingappteam9.model;

import java.util.List;
import java.util.Set;

public class AccommodationShort {
    private Long id;
    private String name;
    private String location;
    private String description;
    private Double averageGrade;
    private List<String> images;

    public AccommodationShort(Long id, String name, String location, String description, Double averageGrade, List<String> images) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.averageGrade = averageGrade;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

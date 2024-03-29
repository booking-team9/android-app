package com.example.bookingappteam9.model;

import java.util.List;

public class HostAccommodation {
    private Long id;
    private String name;
    private String location;
    private AccommodationStatus status;
    private String description;
    private List<String> photos;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    private String hostName;

    public HostAccommodation(Long id, String name, String location, AccommodationStatus status, String description, List<String> photos, String hostName) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
        this.description = description;
        this.photos = photos;
        this.hostName = hostName;
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

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}

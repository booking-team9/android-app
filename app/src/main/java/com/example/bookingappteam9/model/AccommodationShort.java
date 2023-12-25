package com.example.bookingappteam9.model;

public class AccommodationShort {
    private Long id;
    private String name;
    private String location;
    private double averageGrade;
    private AccommodationStatus status;
    private int image;

    public AccommodationShort(Long id, String name, String address, double rating){
        this.id=id;
        this.location=address;
        this.name=name;
        this.averageGrade = rating;
    }
    public AccommodationShort(Long id, String name, String address, double rating,AccommodationStatus status, int image){
        this.id=id;
        this.location=address;
        this.name=name;
        this.averageGrade = rating;
        this.status=status;
        this.image = image;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String address) {
        this.location = address;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double rating) {
        this.averageGrade = rating;
    }

}

package com.example.bookingappteam9.model;

public class AccommodationShort {
    private Long id;
    private String name;
    private String address;
    private double rating;
    private AccommodationStatus status;
    private String image;

    public AccommodationShort(Long id, String name, String address, double rating){
        this.id=id;
        this.address=address;
        this.name=name;
        this.rating = rating;
    }
    public AccommodationShort(Long id, String name, String address, double rating, String image,AccommodationStatus status){
        this.id=id;
        this.address=address;
        this.name=name;
        this.rating = rating;
        this.image = image;
        this.status=status;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

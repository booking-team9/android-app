package com.example.bookingappteam9.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Accommodation {
    private Long id;
    private String name;
    private String description;
    private List<String> amenities;
    private String accommodationType;
    private Integer maxGuests;
    private Integer minGuests;
    private List<String> photos;
    private Boolean pricePerGuest;
    private Integer cancellationDeadline;
    private Boolean autoApproval;
    private Double averageGrade;
    private AccommodationStatus status;
    private List<Review> reviews;
    private Address address;
    private Host host;
    private List<TimeSlot> availability;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public Integer getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(Integer maxGuests) {
        this.maxGuests = maxGuests;
    }

    public Integer getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(Integer minGuests) {
        this.minGuests = minGuests;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Boolean getPricePerGuest() {
        return pricePerGuest;
    }

    public void setPricePerGuest(Boolean pricePerGuest) {
        this.pricePerGuest = pricePerGuest;
    }

    public Integer getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(Integer cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public Boolean getAutoApproval() {
        return autoApproval;
    }

    public void setAutoApproval(Boolean autoApproval) {
        this.autoApproval = autoApproval;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
    }

    public Accommodation(){

    }


    public Accommodation(Long id, String name, String description, List<String> amenities, String accommodationType, Integer maxGuests, Integer minGuests, List<String> photos, Boolean pricePerGuest, Integer cancellationDeadline, Boolean autoApproval, Double averageGrade, AccommodationStatus status, List<Review> reviews, Address address, Host host, List<TimeSlot> availability) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amenities = amenities;
        this.accommodationType = accommodationType;
        this.maxGuests = maxGuests;
        this.minGuests = minGuests;
        this.photos = photos;
        this.pricePerGuest = pricePerGuest;
        this.cancellationDeadline = cancellationDeadline;
        this.autoApproval = autoApproval;
        this.averageGrade = averageGrade;
        this.status = status;
        this.reviews = reviews;
        this.address = address;
        this.host = host;
        this.availability = availability;
    }
}

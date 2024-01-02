package com.example.bookingappteam9.model;

import java.util.List;

public class NewAccommodation {
    private String name;
    private String description;
    private Address address;
    private String accommodationType;
    private List<String> amenities;
    private Integer maxGuests;
    private Integer minGuests;
    private List<String> photos;
    private Boolean isPricePerGuest;
    private AccommodationStatus status;
    private List<TimeSlot> availability;
    private Integer cancellationDeadline;
    private Boolean isAutoApproval;
    private Host host;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
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
        return isPricePerGuest;
    }

    public void setPricePerGuest(Boolean pricePerGuest) {
        isPricePerGuest = pricePerGuest;
    }

    public AccommodationStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationStatus status) {
        this.status = status;
    }

    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public void setAvailability(List<TimeSlot> availability) {
        this.availability = availability;
    }

    public Integer getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(Integer cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public Boolean getAutoApproval() {
        return isAutoApproval;
    }

    public void setAutoApproval(Boolean autoApproval) {
        isAutoApproval = autoApproval;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}

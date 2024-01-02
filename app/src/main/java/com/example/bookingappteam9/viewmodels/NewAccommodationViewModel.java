package com.example.bookingappteam9.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Photo;
import com.example.bookingappteam9.model.TimeSlot;

import java.util.List;

public class NewAccommodationViewModel extends ViewModel {
    private Boolean firstStepEmpty = true;

    public Boolean getSecondStepEmpty() {
        return secondStepEmpty;
    }

    public void setSecondStepEmpty(Boolean secondStepEmpty) {
        this.secondStepEmpty = secondStepEmpty;
    }

    private Boolean secondStepEmpty = true;
    private Boolean thirdStepEmpty = true;
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> type = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<Integer> minGuests = new MutableLiveData<>();
    private MutableLiveData<Integer> maxGuests = new MutableLiveData<>();
    private MutableLiveData<List<String>> amenities = new MutableLiveData<>();
    private MutableLiveData<Address> address = new MutableLiveData<>();
    private MutableLiveData<List<String>> photos = new MutableLiveData<>();
    private MutableLiveData<List<Photo>> rawPhotos = new MutableLiveData<>();
    private MutableLiveData<List<TimeSlot>> availability = new MutableLiveData<>();
    private MutableLiveData<Boolean> pricePerGuest = new MutableLiveData<>();
    private MutableLiveData<Boolean> autoApproval = new MutableLiveData<>();
    private MutableLiveData<Integer> cancellationDeadline = new MutableLiveData<>();

    public void setName(String name) {
        this.name.setValue(name);
    }
    public void setType(String type){
        this.type.setValue(type);
    }
    public void setDescription(String description){
        this.description.setValue(description);
    }
    public void setMinGuests(Integer minGuests){
        this.minGuests.setValue(minGuests);
    }
    public void setMaxGuests(Integer maxGuests){
        this.maxGuests.setValue(maxGuests);
    }
    public void setAmenities(List<String> amenities){
        this.amenities.setValue(amenities);
    }
    public void setAddress(Address address){
        this.address.setValue(address);
    }
    public void setPhotos(List<String> photos){
        this.photos.setValue(photos);
    }
    public void setAvailability(List<TimeSlot> availability){
        this.availability.setValue(availability);
    }
    public void setPricePerGuest(Boolean pricePerGuest){
        this.pricePerGuest.setValue(pricePerGuest);
    }
    public void setAutoApproval(Boolean autoApproval){
        this.autoApproval.setValue(autoApproval);
    }
    public void setCancellationDeadline(Integer cancellationDeadline){
        this.cancellationDeadline.setValue(cancellationDeadline);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getType() {
        return type;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public MutableLiveData<Integer> getMinGuests() {
        return minGuests;
    }

    public MutableLiveData<Integer> getMaxGuests() {
        return maxGuests;
    }

    public MutableLiveData<List<String>> getAmenities() {
        return amenities;
    }

    public MutableLiveData<Address> getAddress() {
        return address;
    }

    public MutableLiveData<List<String>> getPhotos() {
        return photos;
    }

    public MutableLiveData<List<TimeSlot>> getAvailability() {
        return availability;
    }

    public MutableLiveData<Boolean> getPricePerGuest() {
        return pricePerGuest;
    }

    public MutableLiveData<Boolean> getAutoApproval() {
        return autoApproval;
    }

    public MutableLiveData<Integer> getCancellationDeadline() {
        return cancellationDeadline;
    }

    public Boolean getFirstStepEmpty() {
        return firstStepEmpty;
    }

    public void setFirstStepEmpty(Boolean firstStepEmpty) {
        this.firstStepEmpty = firstStepEmpty;
    }

    public Boolean getThirdStepEmpty() {
        return thirdStepEmpty;
    }

    public void setThirdStepEmpty(Boolean thirdStepEmpty) {
        this.thirdStepEmpty = thirdStepEmpty;
    }

    public MutableLiveData<List<Photo>> getRawPhotos() {
        return rawPhotos;
    }

    public void setRawPhotos(List<Photo> rawPhotos) {
        this.rawPhotos.setValue(rawPhotos);
    }
}

package com.example.bookingappteam9.fragments.accommodations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccommodationsPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    public AccommodationsPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("Type you search text");
    }
    public LiveData<String> getText(){
        return searchText;
    }
}

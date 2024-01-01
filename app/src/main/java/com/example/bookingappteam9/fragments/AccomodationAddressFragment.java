package com.example.bookingappteam9.fragments;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.FragmentAccomodationAddressBinding;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.viewmodels.NewAccommodationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class AccomodationAddressFragment extends Fragment{
    private FragmentAccomodationAddressBinding binding;
    private ChipGroup addedPhotos;
    private LatLng coords;
    private NewAccommodationViewModel viewModel;
    private TextInputLayout street;
    private TextInputLayout number;
    private TextInputLayout country;
    private TextInputLayout city;


    /*ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia =
            registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(7), uris -> {
                // Callback is invoked after the user selects media items or closes the
                // photo picker.
                if (!uris.isEmpty()) {
                    Log.d("PhotoPicker", "Number of items selected: " + uris.size());
                    for (int i = 0; i < uris.size(); i++){
                        Chip chip = new Chip(getContext());
                        String name[] = uris.get(i).toString().split("/");
                        //chip.setText(name[name.length-1]);
                        chip.setText("photo" + String.valueOf(i+1));
                        chip.setOnCloseIconClickListener(v1 -> {
                            addedPhotos.removeView(chip);
                        });
                        chip.setCloseIconVisible(true);
                        addedPhotos.addView(chip);
                    }
                } else {
                    Log.d("PhotoPicker", "No media selected");
                }
            });*/


    private final ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Place place = Autocomplete.getPlaceFromIntent(intent);
                        Log.d("MAP", "Place: " + place.getAddressComponents());
                        fillInAddress(place);
                    }
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.i("MAP", "User canceled autocomplete");
                }
            });
    private void fillInAddress(Place place) {
        AddressComponents components = place.getAddressComponents();
        coords = place.getLatLng();
        if (components != null) {
            for (AddressComponent component : components.asList()) {
                String type = component.getTypes().get(0);
                switch (type) {
                    case "street_number": {
                        binding.addressNumber.getEditText().setText(component.getName());
                        break;
                    }
                    case "route": {
                        binding.addressStreet.getEditText().setText(component.getShortName());
                        break;
                    }

                    case "locality":
                        binding.addressCity.getEditText().setText(component.getName());
                        break;

                    case "country":
                        binding.addressCountry.getEditText().setText(component.getName());
                        break;
                }
            }
        }
    }

    private void startAutocompleteIntent() {

        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG, Place.Field.VIEWPORT);

        // Build the autocomplete intent with field, country, and type filters applied
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountries(Arrays.asList("RS"))
                .setTypesFilter(new ArrayList<String>() {{
                    add(TypeFilter.ADDRESS.toString().toLowerCase());
                }})
                .build(getActivity());
        startAutocomplete.launch(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


/*        // Reset the form
        Button resetButton = findViewById(R.id.autocomplete_reset_button);
        resetButton.setOnClickListener(v -> clearForm());*/
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), "AIzaSyCS4P1VuIRVsX-oYdaHYLtIrsNehiJ7k_E");
        }

        binding = FragmentAccomodationAddressBinding.inflate(inflater, container, false);
        street = binding.addressStreet;
        city = binding.addressCity;
        number = binding.addressNumber;
        country = binding.addressCountry;
        binding.searchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutocompleteIntent();
            }
        });
        View view = binding.getRoot();
        /*addedPhotos = binding.addedPhotos;
        binding.addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMultipleMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });*/

        Button nextButton = binding.nextButton;
        nextButton.setOnClickListener(v -> next());
        return view;
    }
    private void next(){
        String streetStr = street.getEditText().getText().toString();
        String numberStr = number.getEditText().getText().toString();
        String countryStr = country.getEditText().getText().toString();
        String cityStr = city.getEditText().getText().toString();
        if (streetStr.isEmpty()){
            street.setError("Required!");
        }else{
            street.setError(null);
        }
        if(numberStr.isEmpty()){
            number.setError("Required!");
        }else{
            number.setError(null);
        }
        if(countryStr.isEmpty()){
            country.setError("Required");
        }else{
            country.setError(null);
        }

        if(cityStr.isEmpty()){
            city.setError("Required!");
        }else{
            city.setError(null);
        }
        if (!streetStr.isEmpty() && !cityStr.isEmpty() && !countryStr.isEmpty() && !numberStr.isEmpty() && coords != null){
            Address address = new Address(streetStr, numberStr, cityStr, countryStr);
            address.setLatitude(coords.latitude);
            address.setLongitude(coords.longitude);
            viewModel.setAddress(address);
            viewModel.setSecondStepEmpty(false);
            findNavController(getParentFragment()).navigate(R.id.action_accomodationAddressFragment_to_availabilityFragment);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(NewAccommodationViewModel.class);
        if (!viewModel.getSecondStepEmpty()){
            viewModel.getAddress().observe(getViewLifecycleOwner(), address -> {
                street.getEditText().setText(address.getStreet());
                city.getEditText().setText(address.getCity());
                number.getEditText().setText(address.getNumber());
                country.getEditText().setText(address.getCountry());
                coords = new LatLng(address.getLatitude(), address.getLongitude());
            });
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
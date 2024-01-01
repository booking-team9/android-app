package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.FragmentNewPropertyBinding;
import com.example.bookingappteam9.viewmodels.NewAccommodationViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPropertyFragment extends Fragment {
    private FragmentNewPropertyBinding binding;
    private NewAccommodationViewModel viewModel;
    private TextInputLayout amenitiesInput;
    private Button nextButton;
    private ChipGroup amenities;
    private TextInputLayout name;
    private TextInputLayout type;
    private TextInputLayout description;
    private TextInputLayout minGuests;
    private TextInputLayout maxGuests;


    public NewPropertyFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewPropertyFragment newInstance() {
        NewPropertyFragment fragment = new NewPropertyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewPropertyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        amenitiesInput = binding.newAccAddAmenity;
        nextButton = binding.newAccNextButton;
        amenities = binding.newAccAmenities;
        name = binding.newAccName;
        type = binding.newAccType;
        description = binding.newAccDescription;
        minGuests = binding.newAccMinGuests;
        maxGuests = binding.newAccMaxGuests;
        minGuests.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (minGuests.getEditText().getText().toString().isEmpty()) {
                    return;
                }
                if (Integer.valueOf(minGuests.getEditText().getText().toString()) <= 0) {
                    minGuests.setError("Must be greater than 0");
                } else {
                    minGuests.setError(null);
                }
                if (!maxGuests.getEditText().getText().toString().isEmpty() && Integer.valueOf(minGuests.getEditText().getText().toString()) >= Integer.valueOf(maxGuests.getEditText().getText().toString())) {
                    minGuests.setError("Must be less than maximum guests!");
                } else {
                    minGuests.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        maxGuests.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (maxGuests.getEditText().getText().toString().isEmpty()) {
                    return;
                }
                if (Integer.valueOf(maxGuests.getEditText().getText().toString()) <= Integer.valueOf(minGuests.getEditText().getText().toString())) {
                    maxGuests.setError("Must be greater than minimum guests");
                } else {
                    maxGuests.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        amenitiesInput.setEndIconOnClickListener(v -> {
            String amenity = amenitiesInput.getEditText().getText().toString();
            Chip chip = new Chip(getContext());
            chip.setText(amenity);
            chip.setOnCloseIconClickListener(v1 -> {
                amenities.removeView(chip);
            });
            //chip.setChipBackgroundColorResource(R.color);
            chip.setCloseIconVisible(true);
            amenities.addView(chip);
            amenitiesInput.getEditText().getText().clear();
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getEditText().getText().toString();
                String typeStr = type.getEditText().getText().toString();
                String desc = description.getEditText().getText().toString();
                String minGuest = minGuests.getEditText().getText().toString();
                String maxGuest = maxGuests.getEditText().getText().toString();
                TextInputLayout errorField = binding.errorField;
                List<String> amenitiesStr = new ArrayList<>();
                for (int i = 0; i < amenities.getChildCount(); i++) {
                    String amenity = ((Chip) amenities.getChildAt(i)).getText().toString();
                    amenitiesStr.add(amenity);
                }
                if (!nameStr.isEmpty() && !typeStr.isEmpty() && !desc.isEmpty() && !minGuest.isEmpty() && !maxGuest.isEmpty()) {
                    errorField.setError(null);
                    viewModel.setName(nameStr);
                    viewModel.setType(typeStr);
                    viewModel.setDescription(desc);
                    viewModel.setMinGuests(Integer.valueOf(minGuest));
                    viewModel.setMaxGuests(Integer.valueOf(maxGuest));
                    viewModel.setAmenities(amenitiesStr);
                    viewModel.setFirstStepEmpty(false);
                    findNavController(getParentFragment()).navigate(R.id.action_newPropertyFragment_to_accomodationAddressFragment);
                } else {
                    errorField.setError("All fields are required!");
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewAccommodationViewModel.class);
        if (!viewModel.getFirstStepEmpty()) {
            viewModel.getName().observe(getViewLifecycleOwner(), oldName -> {
                name.getEditText().setText(oldName);

            });
            viewModel.getType().observe(getViewLifecycleOwner(), oldType -> {
                type.getEditText().setText(oldType);
            });
            viewModel.getDescription().observe(getViewLifecycleOwner(), oldDesc -> {
                description.getEditText().setText(oldDesc);
            });
            viewModel.getMinGuests().observe(getViewLifecycleOwner(), min -> {
                minGuests.getEditText().setText(String.valueOf(min));
            });
            viewModel.getMaxGuests().observe(getViewLifecycleOwner(), max -> {
                maxGuests.getEditText().setText(String.valueOf(max));
            });
            viewModel.getAmenities().observe(getViewLifecycleOwner(), amen -> {
                for (String a: amen){
                    Chip chip = new Chip(getContext());
                    chip.setText(a);
                    chip.setOnCloseIconClickListener(v1 -> {
                        amenities.removeView(chip);
                    });
                    //chip.setChipBackgroundColorResource(R.color);
                    chip.setCloseIconVisible(true);
                    amenities.addView(chip);
                }
            });

        }
        Log.i("ShopApp", "onCreate Products List Fragment");

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
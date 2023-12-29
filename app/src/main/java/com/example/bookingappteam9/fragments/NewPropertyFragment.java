package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.databinding.FragmentNewPropertyBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPropertyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPropertyFragment extends Fragment {
    private FragmentNewPropertyBinding binding;



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

        TextInputLayout amenitiesInput = binding.newAccAddAmenity;
        Button nextButton = binding.newAccNextButton;
        ChipGroup amenities = binding.newAccAmenities;
        amenitiesInput.setEndIconOnClickListener(v -> {
            System.out.println("new am");
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
                findNavController(getParentFragment()).navigate(R.id.action_newPropertyFragment_to_accomodationAddressFragment);
/*                Intent intent = new Intent(getActivity(), NewAddressActivity.class);
                startActivity(intent);*/
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
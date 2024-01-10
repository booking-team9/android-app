package com.example.bookingappteam9.fragments;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AmenityAdapter;
import com.example.bookingappteam9.adapters.DetailsPhotosAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAccommodationDetailsBinding;
import com.example.bookingappteam9.model.Accommodation;
import com.example.bookingappteam9.model.Photo;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsFragment extends Fragment {

    private AccommodationDetailsViewModel mViewModel;
    private FragmentAccommodationDetailsBinding binding;
    private DetailsPhotosAdapter photoAdapter;
    private AmenityAdapter amenityAdapter;
    private  Long accommodationId;
    private Boolean isFavorite = false;
    private Accommodation accommodation;

    public static AccommodationDetailsFragment newInstance() {
        return new AccommodationDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            this.accommodationId = getArguments().getLong("accommodationId");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccommodationDetailsBinding.inflate(inflater, container, false);
        List<String> photos = new ArrayList<>();
        photoAdapter = new DetailsPhotosAdapter(photos);
        amenityAdapter = new AmenityAdapter(new ArrayList<>());
        binding.carouselRecyclerView.setAdapter(photoAdapter);
        binding.carouselRecyclerView.setLayoutManager(new CarouselLayoutManager(new HeroCarouselStrategy()));
        binding.accommodationDetailsAmenities.setAdapter(amenityAdapter);
        binding.accommodationDetailsAmenities.setLayoutManager(new LinearLayoutManager(getContext()));
        SnapHelper helper = new CarouselSnapHelper();
        helper.attachToRecyclerView(binding.carouselRecyclerView);

        ClientUtils.accommodationService.getById(accommodationId).enqueue(new Callback<Accommodation>() {
            @Override
            public void onResponse(Call<Accommodation> call, Response<Accommodation> response) {
                if (response.isSuccessful()) {
                    accommodation = response.body();
                    photoAdapter.addAll(accommodation.getPhotos());
                    amenityAdapter.addAll(accommodation.getAmenities());
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<Accommodation> call, Throwable t) {
                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

            }
        });
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.favorite){
                    isFavorite = !isFavorite;
                    binding.topAppBar.getMenu().getItem(0).setIcon(isFavorite ? R.drawable.baseline_favorite_24_red : R.drawable.baseline_favorite_24);
                    return true;
                }
                return false;
            }
        });

        View root = binding.getRoot();
        return root;
    }
    private void loadData(){
        binding.accommodationDetailsName.setText(accommodation.getName());
        binding.accommodationDetailsAddress.setText(accommodation.getAddress().toString());
        binding.accommodationDetailsType.setText(accommodation.getAccommodationType());
        binding.accommodationDetailsDescription.setText(accommodation.getDescription());
        StringBuilder host = new StringBuilder();
        host.append(accommodation.getHost().getFirstName()).append(" ").append(accommodation.getHost().getLastName());
        binding.accommodationDetailsHost.setText(host);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccommodationDetailsViewModel.class);
    }


}
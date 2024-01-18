package com.example.bookingappteam9.fragments;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.activities.HomeActivity;
import com.example.bookingappteam9.adapters.AmenityAdapter;
import com.example.bookingappteam9.adapters.DetailsPhotosAdapter;
import com.example.bookingappteam9.adapters.ReviewsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAccommodationDetailsBinding;
import com.example.bookingappteam9.model.Accommodation;
import com.example.bookingappteam9.model.Host;
import com.example.bookingappteam9.model.Photo;
import com.example.bookingappteam9.model.PriceRequest;
import com.example.bookingappteam9.model.PriceResponse;
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.model.TimeSlot;
import com.example.bookingappteam9.utils.PrefUtils;
import com.example.bookingappteam9.utils.Round;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailsFragment extends Fragment {

    private AccommodationDetailsViewModel mViewModel;
    private FragmentAccommodationDetailsBinding binding;
    private DetailsPhotosAdapter photoAdapter;
    private AmenityAdapter amenityAdapter;
    private ReviewsAdapter reviewsAdapter;
    private  Long accommodationId;
    private Boolean isFavorite = false;
    private Accommodation accommodation;
    private SupportMapFragment mapFragment;
    private MaterialDatePicker datePicker;
    private String picked_date;
    private TimeSlot slot = new TimeSlot();

    private boolean isHostOwner = false;
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+1"));
    private PrefUtils.UserInfo user;


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
        user = PrefUtils.getUserInfo(getActivity().getApplicationContext());
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

        if (user.getRole().equals(Role.Guest)){
            binding.topAppBar.getMenu().getItem(0).setVisible(true);
            ClientUtils.guestService.checkFavorite(user.getId(), accommodationId).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()){
                        isFavorite = response.body();
                        if (isFavorite)
                            binding.topAppBar.getMenu().getItem(0).setIcon(R.drawable.baseline_favorite_24_red);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                }
            });

        }
        if (user.getRole().equals(Role.Host)){
            binding.guestGroup.setVisibility(View.GONE);

        }
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
                    if (isFavorite){
                        ClientUtils.guestService.addFavorite(user.getId(), accommodationId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Log.d("QM","Favorite successfully added!");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                            }
                        });
                    }else{
                        ClientUtils.guestService.removeFavorite(user.getId(), accommodationId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Log.d("QM","Favorite successfully removed!");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });

        View root = binding.getRoot();
        return root;
    }
    private void loadData(){
        if (user.getId().equals(accommodation.getHost().getId())){
            isHostOwner = true;
        }
        reviewsAdapter = new ReviewsAdapter(new ArrayList<>(), isHostOwner);
        binding.accommodationDetailsReviews.setAdapter(reviewsAdapter);
        binding.accommodationDetailsReviews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.accommodationDetailsName.setText(accommodation.getName());
        binding.accommodationDetailsAddress.setText(accommodation.getAddress().toString());
        binding.accommodationDetailsType.setText(accommodation.getAccommodationType());
        binding.accommodationDetailsDescription.setText(accommodation.getDescription());
        binding.accommodationDetailsCancellationDeadline.setText(accommodation.getCancellationDeadline().toString() + " days");
        StringBuilder host = new StringBuilder();
        host.append(accommodation.getHost().getFirstName()).append(" ").append(accommodation.getHost().getLastName());
        binding.accommodationDetailsHost.setText(host);
        CalendarConstraints.DateValidator validator = new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                for (TimeSlot s: accommodation.getAvailability()){
                    long start = s.getStartDate().toEpochSecond(ZoneOffset.UTC)*1000;
                    long end = s.getEndDate().toEpochSecond(ZoneOffset.UTC)*1000;
                    if (date >= start && date <= end && date > Instant.now().toEpochMilli()){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel dest, int flags) {

            }
        };
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        long dec = calendar.getTimeInMillis();
        CalendarConstraints constraints = new CalendarConstraints.Builder().setValidator(validator).setStart(today).setEnd(dec).build();
        datePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.thisMonthInUtcMilliseconds())).setCalendarConstraints(constraints).build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Pair<Long, Long> dates = (Pair<Long, Long>) selection;
                slot.setStartDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(dates.first), ZoneId.of("GMT+1")));
                slot.setEndDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(dates.second), ZoneId.of("GMT+1")));
                slot.setRangeString(datePicker.getHeaderText());
                picked_date = datePicker.getHeaderText();
                binding.accommodationDetailsPriceDates.setText(picked_date);
                ClientUtils.accommodationService.getPrice(new PriceRequest(accommodationId, slot.getStartDate().toLocalDate(), slot.getEndDate().toLocalDate())).enqueue(new Callback<PriceResponse>() {
                    @Override
                    public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                        if (response.isSuccessful()){
                            binding.accommodationDetailsPrice.setText(Round.round(response.body().getTotalPrice(), 2) + "€");

                        }
                    }

                    @Override
                    public void onFailure(Call<PriceResponse> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                    }
                });
            }});
        binding.accommodationDetailsAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("date clicked");
                datePicker.show(getChildFragmentManager(), "datepicker");
            }
        });
        binding.accommodationDetailsReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ClientUtils.reviewService.getByAccommodationId(accommodationId).enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()){
                    reviewsAdapter.addAll(response.body());
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(callback);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((HomeActivity)getActivity()).getNavView().setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView nav = ((HomeActivity)getActivity()).getNavView();
        if (nav != null){
            nav.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView nav = ((HomeActivity)getActivity()).getNavView();
        if (nav != null){
            nav.setVisibility(View.INVISIBLE);
        }

        mViewModel = new ViewModelProvider(this).get(AccommodationDetailsViewModel.class);
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.accommodation_details_map);
    }


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setZoomGesturesEnabled(false);
            LatLng apartment = new LatLng(accommodation.getAddress().getLatitude(), accommodation.getAddress().getLongitude());
            googleMap.addMarker(new MarkerOptions().position(apartment).title(accommodation.getName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(apartment, 12));

        }
    };


}
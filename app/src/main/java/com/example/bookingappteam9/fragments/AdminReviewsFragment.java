package com.example.bookingappteam9.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AdminReviewsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAdminReviewsBinding;
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.ReviewStatus;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminReviewsFragment extends Fragment {
    private FragmentAdminReviewsBinding binding;
    private AdminReviewsAdapter adapter;
    private AutoCompleteTextView reviewFilter;
    private final String[] statuses = {"All", "Pending", "Approved", "Reported"};


    public static AdminReviewsFragment newInstance(String param1, String param2) {
        AdminReviewsFragment fragment = new AdminReviewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminReviewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ArrayList<Review> reviews = new ArrayList<>();
        adapter = new AdminReviewsAdapter(reviews);
        binding.adminReviews.setAdapter(adapter);
        binding.adminReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewFilter = binding.reviewsFilter;

        ClientUtils.reviewService.getAll().enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()){
                    adapter.addAll(response.body());
                    Log.d("QM", "Reviews successfully loaded!");
                    binding.progressLoaderAdminReviews.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");

            }
        });





        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    adapter.showALl();
                }else{
                    adapter.filterReviews(ReviewStatus.valueOf(statuses[position]));
                }
            }
        });
    }
}
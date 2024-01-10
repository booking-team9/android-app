package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.adapters.HostReviewsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostWithReviewsBinding;
import com.example.bookingappteam9.model.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostWithReviewsFragment extends Fragment {
    private HostReviewsAdapter adapter;
    private FragmentHostWithReviewsBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HostWithReviewsFragment() {
        // Required empty public constructor
    }

    public static HostWithReviewsFragment newInstance(String param1, String param2) {
        HostWithReviewsFragment fragment = new HostWithReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHostWithReviewsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        ArrayList<Review> reviews = new ArrayList<>();
        adapter = new HostReviewsAdapter(reviews);
        binding.hostReviewsList.setAdapter(adapter);
        binding.hostReviewsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<ArrayList<Review>> call = ClientUtils.reviewService.getByHostId(1L);
        call.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.code()==200){
                    List<Review> revievsRaw = response.body();
                    adapter.addReviews(revievsRaw);
                    adapter.notifyDataSetChanged();
                    binding.progressLoaderHostReviews.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });

        return root;
    }
}
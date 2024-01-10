package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.adapters.HostReviewsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostWithReviewsBinding;
import com.example.bookingappteam9.model.Host;
import com.example.bookingappteam9.model.Review;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostWithReviewsFragment extends Fragment {
    private HostReviewsAdapter adapter;
    private FragmentHostWithReviewsBinding binding;
    private Long hostId;
    private Host host;
    private TextView hostNameText;
    private Button reportButton;

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
        if (getArguments() != null){
            this.hostId = getArguments().getLong("hostId");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<Host> call1 = ClientUtils.hostService.getById(hostId);
        call1.enqueue(new Callback<Host>() {
            @Override
            public void onResponse(Call<Host> call, Response<Host> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    Log.d("REZ", String.valueOf(response.body()));
                    System.out.println(response.body());
                    host = response.body();
                    assert host != null;
                    hostNameText.setText(host.getFirstName()+" "+host.getLastName());
                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<Host> call1, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });
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
        hostNameText = binding.hostName;
        reportButton = binding.reportButton;
        ArrayList<Review> reviews = new ArrayList<>();
        adapter = new HostReviewsAdapter(reviews);
        binding.hostReviewsList.setAdapter(adapter);
        binding.hostReviewsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportDialogFragment dialogFragment = ReportDialogFragment.newInstance(hostId);
                AppCompatActivity activity = (AppCompatActivity) getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                dialogFragment.show(ft, "dialog");
            }
        });


        Call<ArrayList<Review>> call = ClientUtils.reviewService.getByHostId(hostId);
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
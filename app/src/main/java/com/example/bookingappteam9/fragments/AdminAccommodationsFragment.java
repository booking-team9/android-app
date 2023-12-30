package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AdminAccommodationListAdapter;
import com.example.bookingappteam9.adapters.AdminAccommodationsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAdminAccommodationsBinding;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAccommodationsFragment extends Fragment {
    private AdminAccommodationsAdapter adapter;
    private ArrayList<HostAccommodation> accommodations;
    private FragmentAdminAccommodationsBinding binding;


    public AdminAccommodationsFragment() {
        // Required empty public constructor
    }

    public static AdminAccommodationsFragment newInstance(String param1, String param2) {
        AdminAccommodationsFragment fragment = new AdminAccommodationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAccommodationsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        ArrayList<HostAccommodation> list = new ArrayList<>();
        adapter = new AdminAccommodationsAdapter(list);
        binding.adminAccommodationsList.setAdapter(adapter);
        binding.adminAccommodationsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        Call<ArrayList<HostAccommodation>> call = ClientUtils.accommodationService.getUnapproved();

        call.enqueue(new Callback<ArrayList<HostAccommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<HostAccommodation>> call, Response<ArrayList<HostAccommodation>> response) {
                if (response.code() == 200){
                    ArrayList<HostAccommodation> hostAccommodations = response.body();
                    adapter.addAll(hostAccommodations);

                }else{
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HostAccommodation>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
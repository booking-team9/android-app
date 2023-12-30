package com.example.bookingappteam9.fragments.accommodations;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.adapters.AllAccommodationsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAllAccommodationsBinding;
import com.example.bookingappteam9.model.AccommodationShort;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllAccommodationsFragment extends Fragment {

    private AllAccommodationsAdapter adapter;
    private FragmentAllAccommodationsBinding binding;

    public AllAccommodationsFragment(){

    }

    public static AllAccommodationsFragment newInstance(){
        return new AllAccommodationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAllAccommodationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<AccommodationShort> accommodations = new ArrayList<>();
        adapter = new AllAccommodationsAdapter(accommodations);
        binding.allAccommodationsList.setAdapter(adapter);
        binding.allAccommodationsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        Call<ArrayList<AccommodationShort>> call = ClientUtils.accommodationService.getAllShort();

        call.enqueue(new Callback<ArrayList<AccommodationShort>>() {
            @Override
            public void onResponse(Call<ArrayList<AccommodationShort>> call, Response<ArrayList<AccommodationShort>> response) {
                if (response.code() == 200){
                    adapter.addAll(response.body());
                }else{
                    Log.d("QM","Meesage recieved: "+response.code());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AccommodationShort>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });
        return root;
    }
}
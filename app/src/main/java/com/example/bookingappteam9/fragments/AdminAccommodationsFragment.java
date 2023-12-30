package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AdminAccommodationListAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAdminAccommodationsBinding;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAccommodationsFragment extends ListFragment {
    private AdminAccommodationListAdapter adapter;
    private ArrayList<HostAccommodation> accommodations;
    private FragmentAdminAccommodationsBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdminAccommodationsFragment() {
        // Required empty public constructor
    }

    public static AdminAccommodationsFragment newInstance(String param1, String param2) {
        AdminAccommodationsFragment fragment = new AdminAccommodationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AccommodationShort accommodationShort = new AccommodationShort(1L, "QM", "aleksa kuma 21 kosjeric", 4.5);
//        accommodationShort.setStatus(AccommodationStatus.Pending);
        ArrayList<HostAccommodation> list = new ArrayList<>();
//        list.add(accommodationShort);
//        accommodations = (ArrayList<AccommodationShort>) ClientUtils.accommodationService.getByHostId(1L);
        adapter = new AdminAccommodationListAdapter(getActivity(),list);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminAccommodationsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        Call<ArrayList<HostAccommodation>> call = ClientUtils.accommodationService.getUnapproved();
        call.enqueue(new Callback<ArrayList<HostAccommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<HostAccommodation>> call, Response<ArrayList<HostAccommodation>> response) {
                if (response.code() == 200){
                    ArrayList<HostAccommodation> hostAccommodations = response.body();
                    addProducts(hostAccommodations);

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

    public void addProducts(ArrayList<HostAccommodation> list){
        this.adapter.addAll(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Handle the click on item at 'position'
    }

}
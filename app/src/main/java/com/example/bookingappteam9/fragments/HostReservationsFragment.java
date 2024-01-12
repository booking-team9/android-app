package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.adapters.HostReservationsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostReservationsBinding;
import com.example.bookingappteam9.model.Reservation;
import com.example.bookingappteam9.model.ReservationStatus;
import com.example.bookingappteam9.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostReservationsFragment extends Fragment {

    private HostReservationsAdapter adapter;
    private FragmentHostReservationsBinding binding;
    private AutoCompleteTextView reservationFilter;
    private SearchView searchView;
    private final String[] statuses = {"All", "Approved", "Done", "Active"};

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HostReservationsFragment() {
        // Required empty public constructor
    }

    public static HostReservationsFragment newInstance(String param1, String param2) {
        HostReservationsFragment fragment = new HostReservationsFragment();
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
        reservationFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    adapter.showALlStatuses();
                }else{
                    adapter.filterReservations(ReservationStatus.valueOf(statuses[position]));
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals(""))
                    adapter.showALl();
                else{
                    adapter.searchReservations(newText);
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        reservationFilter.setText("");
        searchView.setQuery("", false);
        adapter.showALl();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHostReservationsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        ArrayList<Reservation> reservations = new ArrayList<>();
        adapter = new HostReservationsAdapter(reservations);
        binding.hostReservationList.setAdapter(adapter);
        binding.hostReservationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        reservationFilter = binding.reservationsFilter;
        searchView = binding.searchView;
        searchView.clearFocus();

        Call<ArrayList<Reservation>> call = ClientUtils.reservationService.getDecidedReservationsByHostId(userInfo.getId());
        call.enqueue(new Callback<ArrayList<Reservation>>() {
            @Override
            public void onResponse(Call<ArrayList<Reservation>> call, Response<ArrayList<Reservation>> response) {
                if (response.code()==200){
                    List<Reservation> reservatonsRaw = response.body();
                    adapter.addReservations(reservatonsRaw);
                    adapter.notifyDataSetChanged();
                    if(binding!=null)
                        binding.progressLoaderHostReservations.setVisibility(View.INVISIBLE);
                }
                else {
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reservation>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });



        return root;
    }
}
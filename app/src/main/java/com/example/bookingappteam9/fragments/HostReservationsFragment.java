package com.example.bookingappteam9.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class HostReservationsFragment extends Fragment implements SensorEventListener{

    private HostReservationsAdapter adapter;
    private FragmentHostReservationsBinding binding;
    private AutoCompleteTextView reservationFilter;
    private SearchView searchView;
    private final String[] statuses = {"All", "Approved", "Done", "Active"};
    private SensorManager sensorManager;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;
    private static final float SHAKE_THRESHOLD = 800;


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
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener( this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        sensorManager.unregisterListener(this);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 200) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float[] values = sensorEvent.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                if (speed > SHAKE_THRESHOLD) {

//                    reservationFilter.setSelection(1);
                    adapter.showALlStatuses();
                    reservationFilter.setText("All", false);
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            Log.i("REZ_ACCELEROMETER", String.valueOf(accuracy));
        }
    }
}
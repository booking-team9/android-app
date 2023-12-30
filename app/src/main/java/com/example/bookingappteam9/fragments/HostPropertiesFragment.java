package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.HostPropertiesAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostPropertiesBinding;
import com.example.bookingappteam9.model.HostAccommodation;
import com.example.bookingappteam9.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostPropertiesFragment extends ListFragment {
    private HostPropertiesAdapter adapter;
    private FragmentHostPropertiesBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HostPropertiesFragment() {
        // Required empty public constructor
    }
    public static HostPropertiesFragment newInstance(String param1, String param2) {
        HostPropertiesFragment fragment = new HostPropertiesFragment();
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
        Log.i("ShopApp", "onCreate Products List Fragment");

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
        binding = FragmentHostPropertiesBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        ArrayList<HostAccommodation> accommodations = new ArrayList<>();
        adapter = new HostPropertiesAdapter(accommodations);
        binding.hostPropertiesList.setAdapter(adapter);
        binding.hostPropertiesList.setLayoutManager(new LinearLayoutManager(getActivity()));
        Call<ArrayList<HostAccommodation>> call = ClientUtils.accommodationService.getByHostId(userInfo.getId());
        call.enqueue(new Callback<ArrayList<HostAccommodation>>() {
            @Override
            public void onResponse(Call<ArrayList<HostAccommodation>> call, Response<ArrayList<HostAccommodation>> response) {
                if (response.code() == 200){
                    List<HostAccommodation> accommodationsRaw = response.body();
                    adapter.addCards(accommodationsRaw);
                    adapter.notifyDataSetChanged();

                }else{
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<HostAccommodation>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });
        binding.addProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getParentFragment()).navigate(R.id.action_navigation_host_properties_to_newPropertyFragment);

            }
        });

        return root;
    }




    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

}
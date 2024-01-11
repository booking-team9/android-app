package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.activities.HomeActivity;
import com.example.bookingappteam9.activities.SplashActivity;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentHostProfileBinding;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Host;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostProfileFragment extends Fragment {
    private FragmentHostProfileBinding binding;
    private Host host;
    private Long id;

    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView fullAddress;
    private TextView profileType;
    private static HomeActivity ARG_PARAM1 = new HomeActivity();
    private static final String ARG_PARAM2 = "param2";
    private Gson gson = new Gson();

    private HomeActivity mParam1;
    private String mParam2;

    public HostProfileFragment() {
        // Required empty public constructor
    }
    public static HostProfileFragment newInstance(HomeActivity param1, String param2) {
        HostProfileFragment fragment = new HostProfileFragment();
        Bundle args = new Bundle();
        ARG_PARAM1 = param1;
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHostProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fullName = binding.hostFullName;
        fullAddress = binding.profileHostAddress;
        phone = binding.profileHostPhone;
        email = binding.profileHostEmail;
        profileType = binding.hostType;
        binding.logoutButtonHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearUserInfo(getActivity().getApplicationContext());
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
            }
        });
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        id = userInfo.getId();
        ImageView editImage = (ImageView) view.findViewById(R.id.edit_button);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getParentFragment()).navigate(R.id.action_navigation_host_profile_to_editProfileFragment);
            }
        });
        binding.viewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("hostId", id);
                findNavController(getParentFragment()).navigate(R.id.action_navigation_host_profile_to_hostWithReviewsFragment, bundle);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ShopApp", "onCreate Products List Fragment");
        getDataFromClient();

    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromClient();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    private void getDataFromClient(){
        Call<Host> call = ClientUtils.hostService.getById(id);
        call.enqueue(new Callback<Host>() {
            @Override
            public void onResponse(Call<Host> call, Response<Host> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    System.out.println(response.body());
                    host = response.body();
                    setData();

                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Host> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void setData(){
        fullName.setText(host.getFirstName()+" "+host.getLastName());
        phone.setText(host.getPhone());
        Address address = host.getAddress();
        fullAddress.setText(address.getStreet() + " - " + address.getNumber() + ",  " + address.getCity());
        profileType.setText("Host");
        email.setText(host.getEmail());
        Log.i("Jabuka",gson.toJson(host) );
    }


}
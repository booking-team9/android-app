package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

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
import com.example.bookingappteam9.activities.HomeScreen;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentGuestProfileBinding;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Guest;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuestProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestProfileFragment extends Fragment {
    private FragmentGuestProfileBinding binding;
    private Guest guest;
    private Long id;
    private TextView fullName;
    private TextView email;
    private TextView phone;
    private TextView fullAddress;
    private TextView profileType;
    private static HomeScreen ARG_PARAM1 = new HomeScreen();
    private static final String ARG_PARAM2 = "param2";
    private Gson gson = new Gson();

    private HomeScreen mParam1;
    private String mParam2;

    public GuestProfileFragment() {
        // Required empty public constructor
    }
    public static GuestProfileFragment newInstance(HomeScreen param1, String param2) {
        GuestProfileFragment fragment = new GuestProfileFragment();
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
        binding = FragmentGuestProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fullName = binding.guestFullName;
        fullAddress = binding.profileGuestAddress;
        phone = binding.profileGuestPhone;
        email = binding.profileGuestEmail;
        profileType = binding.guestType;
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        id = userInfo.getId();
        ImageView editImage = (ImageView) view.findViewById(R.id.edit_button);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getParentFragment()).navigate(R.id.action_navigation_guest_profile_to_editProfileFragment);
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
        Call<Guest> call = ClientUtils.guestService.getById(id);
        call.enqueue(new Callback<Guest>() {
            @Override
            public void onResponse(Call<Guest> call, Response<Guest> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    System.out.println(response.body());
                    guest = response.body();
                    setData();

                }else{
                    Log.d("REZ","Meesage recieved: "+response.code());
                }
            }
            @Override
            public void onFailure(Call<Guest> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
            }
        });

    }

    private void setData(){
        fullName.setText(guest.getFirstName()+" "+guest.getLastName());
        phone.setText(guest.getPhone());
        Address address = guest.getAddress();
        fullAddress.setText(address.getStreet() + " - " + address.getNumber() + ",  " + address.getCity());
        profileType.setText("Guest");
        email.setText(guest.getEmail());
        Log.i("Jabuka",gson.toJson(guest) );
    }
}
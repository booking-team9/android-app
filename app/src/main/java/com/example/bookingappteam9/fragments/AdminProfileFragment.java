package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.activities.SplashActivity;
import com.example.bookingappteam9.databinding.FragmentAdminProfileBinding;
import com.example.bookingappteam9.utils.PrefUtils;

public class AdminProfileFragment extends Fragment {

    private FragmentAdminProfileBinding binding;
    private String adminEmail;
    private TextView adminName;

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        adminEmail = userInfo.getEmail();
        adminName = binding.adminName;
        binding.logoutButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearUserInfo(getActivity().getApplicationContext());

                Intent intent = new Intent(getActivity(), SplashActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        binding.changePasswordButtonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findNavController(getParentFragment()).navigate(R.id.action_adminProfileFragment_to_changePasswordFragment);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminName.setText(this.adminEmail);

    }
}
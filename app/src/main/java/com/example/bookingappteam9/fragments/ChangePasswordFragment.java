package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam9.activities.HomeScreen;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentChangePasswordBinding;
import com.example.bookingappteam9.model.PasswordChange;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {
    private FragmentChangePasswordBinding binding;
    private PasswordChange passwordChange;
    private Button changeButton;
    private EditText oldPasssword;
    private EditText newPasssword;
    private EditText confirmedPasssword;
    private String email;
    private static HomeScreen ARG_PARAM1 = new HomeScreen();
    private static final String ARG_PARAM2 = "param2";
    private HomeScreen mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(HomeScreen param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        ARG_PARAM1=param1;
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
        binding = FragmentChangePasswordBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        String mail = "";
        if (bundle != null) {
            mail = bundle.getString("email"); // Replace "key" with the actual key you used
            // Now you have the data, you can use it as needed
        }
        this.email = mail;
        passwordChange = new PasswordChange();
        return view;
//        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "change password");
        changeButton = binding.changePasswordButton;
        oldPasssword = binding.oldPassword;
        newPasssword = binding.newPassword;
        confirmedPasssword = binding.confirmedPassword;
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("email", email);
                Call<String> call;
                Log.d("Booking", "Changing password");
                collectData();
                call = ClientUtils.accountService.changePassword(passwordChange);
                Log.d("adad", String.valueOf(passwordChange));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code()==200){
                            Log.d("REZ", "Password changed");
                            System.out.println(response.body());
                            Toast.makeText(getActivity(),"Password changed",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void collectData(){
        passwordChange.setEmail(email);
        passwordChange.setOldPassword(String.valueOf(oldPasssword.getText()));
        passwordChange.setNewPassword(String.valueOf(newPasssword.getText()));
        passwordChange.setConfirmedPassword(String.valueOf(confirmedPasssword.getText()));

    }
}
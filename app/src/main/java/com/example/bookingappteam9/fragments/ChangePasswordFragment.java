package com.example.bookingappteam9.fragments;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.activities.HomeActivity;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentChangePasswordBinding;
import com.example.bookingappteam9.model.PasswordChange;
import com.example.bookingappteam9.utils.PrefUtils;

import okhttp3.ResponseBody;
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
    private Long id;
    private static HomeActivity ARG_PARAM1 = new HomeActivity();
    private static final String ARG_PARAM2 = "param2";
    private HomeActivity mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(HomeActivity param1, String param2) {
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
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        id = userInfo.getId();
        email = userInfo.getEmail();
        ImageView backButton = (ImageView) view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToEdit();
            }
        });
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
                Call<ResponseBody> call;
                Log.d("Booking", "Changing password");
                collectData();
                call = ClientUtils.accountService.changePassword(passwordChange);
                Log.d("adad", String.valueOf(passwordChange));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Success");
                            builder.setMessage("Your password is successfully changed");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("REZ", "Password changed");
                                    System.out.println(response.body());
                                    backToEdit();
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Fail");
                        builder.setMessage("Failed to change password");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                                backToEdit();
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
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

    private void backToEdit(){
        findNavController(getParentFragment()).navigate(R.id.action_changePasswordFragment_to_editProfileFragment);
    }
}
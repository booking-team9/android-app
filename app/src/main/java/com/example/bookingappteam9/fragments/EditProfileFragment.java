package com.example.bookingappteam9.fragments;

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
import com.example.bookingappteam9.activities.HomeScreen;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentEditProfileBinding;
import com.example.bookingappteam9.fragments.accommodations.AccommodationsPageFragment;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Host;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private Host host;
    private Button confirmButton;
    private Button deleteAccountButton;
    private Button changePasswordButton;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText phoneText;
    private EditText streetText;
    private EditText numberText;
    private EditText cityText;

    private static HomeScreen ARG_PARAM1 = new HomeScreen();
    private static final String ARG_PARAM2 = "param2";
    private Gson gson = new Gson();
    private HomeScreen mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }
    public static EditProfileFragment newInstance(HomeScreen param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        ImageView backImage = (ImageView) view.findViewById(R.id.back_icon);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransition.to(ProfileFragment.newInstance(ARG_PARAM1, "Ovo je profile review!"), ARG_PARAM1, false, R.id.navigationView);
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "edit account");
        firstNameText = binding.editFirstName;
        lastNameText = binding.editLastName;
        emailText = binding.editEmail;
        phoneText = binding.editPhone;
        streetText = binding.editStreet;
        numberText = binding.editNumber;
        cityText = binding.editCity;
        confirmButton = binding.submitButton;
        deleteAccountButton = binding.deleteAccountButton;
        changePasswordButton = binding.changePasswordButton;
        getDataFromClient();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Host> call;
                        Log.d("Booking", "Edit profile call");
                        editAccout();
                        call = ClientUtils.hostService.edit(host);

                        call.enqueue(new Callback<Host>() {
                            @Override
                            public void onResponse(Call<Host> call, Response<Host> response) {
                                if (response.code() == 200){
                                    Log.d("REZ","Meesage recieved");
                                    System.out.println(response.body());
                                    Host host = response.body();
                                    System.out.println(host);
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }else{
                                    Log.d("REZ","Meesage recieved: "+response.code());
                                }
                            }
                            @Override
                            public void onFailure(Call<Host> call, Throwable t) {
                                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                            }
                        });

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



            }


        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Call<ResponseBody> call;
                        Log.d("Booking", "Deleting account call");
                        call = ClientUtils.hostService.deleteHost(14L);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code() == 200){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("Success");
                                    builder.setMessage("Your account has been deleted successfully");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d("REZ","Meesage recieved");
                                            System.out.println(response.body());
                                            FragmentTransition.to(AccommodationsPageFragment.newInstance(), ARG_PARAM1, false, R.id.navigationView);
                                            dialog.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setTitle("Fail");
                                    builder.setMessage("Your account failed to delete");
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
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
                                builder.setMessage("Your account failed to delete");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        });
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", host.getEmail());
                ChangePasswordFragment changePasswordFragment = ChangePasswordFragment.newInstance(ARG_PARAM1, "Changing password");
                changePasswordFragment.setArguments(bundle);

                FragmentTransition.to(changePasswordFragment, ARG_PARAM1, false, R.id.navigationView);

            }
        });

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
        Call<Host> call = ClientUtils.hostService.getById(5L);
        call.enqueue(new Callback<Host>() {
            @Override
            public void onResponse(Call<Host> call, Response<Host> response) {
                if (response.code() == 200){
                    Log.d("REZ","Meesage recieved");
                    Log.d("REZ", String.valueOf(response.body()));
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
        firstNameText.setText(host.getFirstName());
        lastNameText.setText(host.getLastName());
        emailText.setText(host.getEmail());
        phoneText.setText(host.getPhone());
        streetText.setText(host.getAddress().getStreet());
        numberText.setText(host.getAddress().getNumber());
        cityText.setText(host.getAddress().getCity());
        Log.i("Jabuka",gson.toJson(host) );
    }

    private void editAccout(){
        host.setId(5L);
        host.setFirstName(String.valueOf(firstNameText.getText()));
        host.setLastName(String.valueOf(lastNameText.getText()));
        host.setEmail(String.valueOf(emailText.getText()));
        host.setPhone(String.valueOf(phoneText.getText()));
        Address address = new Address(String.valueOf(streetText.getText()), String.valueOf(numberText.getText()),String.valueOf(cityText.getText()));
        host.setAddress(address);
    }
}
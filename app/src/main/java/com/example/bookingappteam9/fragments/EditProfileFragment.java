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
import com.example.bookingappteam9.activities.HomeScreen;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentEditProfileBinding;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Guest;
import com.example.bookingappteam9.model.Host;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private Guest guest;
    private Host host;
    private Long id;
    private Role role;
    private String email;
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
        ImageView backImage = (ImageView) view.findViewById(R.id.back_icon);
        PrefUtils.UserInfo userInfo = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        id = userInfo.getId();
        role = userInfo.getRole();
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                if (role==Role.Host){
                    findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_host_profile);
                }else if(role == Role.Guest){
                    findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_guest_profile);
                }

            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "edit account");

//        authorize();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(role==Role.Host){
                            Call<Host> call;
                            Log.d("Booking", "Edit profile call");
                            editHostAccout();
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
                                        AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                                        builderGood.setTitle("Success");
                                        builderGood.setMessage("Changes made successfully");
                                        builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                                findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_host_profile);
                                            }
                                        });
                                        AlertDialog alertGood = builderGood.create();
                                        alertGood.show();
                                    }else{
                                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                                        builderBad.setTitle("Fail");
                                        builderBad.setMessage("Failed to edit profile");
                                        builderBad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                                findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_host_profile);
                                            }
                                        });
                                        AlertDialog alertBad = builderBad.create();
                                        alertBad.show();
                                        Log.d("REZ","Meesage recieved: "+response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Host> call, Throwable t) {
                                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                                }
                            });
                        }else if(role == Role.Guest){
                            Call<Guest> call;
                            Log.d("Booking", "Edit profile call");
                            editGuestAccout();
                            call = ClientUtils.guestService.edit(guest);

                            call.enqueue(new Callback<Guest>() {
                                @Override
                                public void onResponse(Call<Guest> call, Response<Guest> response) {
                                    if (response.code() == 200){
                                        Log.d("REZ","Meesage recieved");
                                        System.out.println(response.body());
                                        Guest guest = response.body();
                                        System.out.println(guest);
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                                        builderGood.setTitle("Success");
                                        builderGood.setMessage("Changes made successfully");
                                        builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                                findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_guest_profile);
                                            }
                                        });
                                        AlertDialog alertGood = builderGood.create();
                                        alertGood.show();
                                    }else{
                                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                                        builderBad.setTitle("Fail");
                                        builderBad.setMessage("Failed to edit profile");
                                        builderBad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
//                                                findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_guest_profile);
                                            }
                                        });
                                        AlertDialog alertBad = builderBad.create();
                                        alertBad.show();
                                        Log.d("REZ","Meesage recieved: "+response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<Guest> call, Throwable t) {
                                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                                }
                            });
                        }


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
                        Call<ResponseBody> call = null;
                        Log.d("Booking", "Deleting account call");
                        if (role==Role.Host)
                            call = ClientUtils.hostService.deleteHost(id);
                        else if(role==Role.Guest)
                            call = ClientUtils.guestService.deleteGuest(id);

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
                                            findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_navigation_home);
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
                findNavController(getParentFragment()).navigate(R.id.action_editProfileFragment_to_changePasswordFragment);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        getDataFromClient();
//        authorize();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getDataFromClient(){
        if(role==Role.Host){
            Call<Host> call = ClientUtils.hostService.getById(id);
            call.enqueue(new Callback<Host>() {
                @Override
                public void onResponse(Call<Host> call, Response<Host> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        Log.d("REZ", String.valueOf(response.body()));
                        System.out.println(response.body());
                        host = response.body();
                        setHostData();
                    }else{
                        Log.d("REZ","Meesage recieved: "+response.code());
                    }
                }
                @Override
                public void onFailure(Call<Host> call, Throwable t) {
                    Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
                }
            });
        }else if (role==Role.Guest){
            Call<Guest> call = ClientUtils.guestService.getById(id);
            call.enqueue(new Callback<Guest>() {
                @Override
                public void onResponse(Call<Guest> call, Response<Guest> response) {
                    if (response.code() == 200){
                        Log.d("REZ","Meesage recieved");
                        Log.d("REZ", String.valueOf(response.body()));
                        System.out.println(response.body());
                        guest = response.body();
                        setGuestData();
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


    }

    private void setHostData(){
        firstNameText.setText(host.getFirstName());
        lastNameText.setText(host.getLastName());
        emailText.setText(host.getEmail());
        phoneText.setText(host.getPhone());
        streetText.setText(host.getAddress().getStreet());
        numberText.setText(host.getAddress().getNumber());
        cityText.setText(host.getAddress().getCity());
        Log.i("Jabuka",gson.toJson(host) );
    }
    private void setGuestData(){
        firstNameText.setText(guest.getFirstName());
        lastNameText.setText(guest.getLastName());
        emailText.setText(guest.getEmail());
        phoneText.setText(guest.getPhone());
        streetText.setText(guest.getAddress().getStreet());
        numberText.setText(guest.getAddress().getNumber());
        cityText.setText(guest.getAddress().getCity());
        Log.i("Jabuka",gson.toJson(guest) );
    }

    private void editHostAccout(){
        host.setFirstName(String.valueOf(firstNameText.getText()));
        host.setLastName(String.valueOf(lastNameText.getText()));
        host.setEmail(String.valueOf(emailText.getText()));
        host.setPhone(String.valueOf(phoneText.getText()));
        Address address = new Address(String.valueOf(streetText.getText()), String.valueOf(numberText.getText()),String.valueOf(cityText.getText()), "");
        host.setAddress(address);
    }
    private void editGuestAccout(){
        guest.setFirstName(String.valueOf(firstNameText.getText()));
        guest.setLastName(String.valueOf(lastNameText.getText()));
        guest.setEmail(String.valueOf(emailText.getText()));
        guest.setPhone(String.valueOf(phoneText.getText()));
        Address address = new Address(String.valueOf(streetText.getText()), String.valueOf(numberText.getText()),String.valueOf(cityText.getText()), "");
        guest.setAddress(address);
    }
//    private void authorize(){
//        String token = PrefUtils.getFromPrefs(getActivity().getApplication(), "LoginPrefs", "token", "");
//        JWT parsedJWT = new JWT(token);
//        Claim emailData = parsedJWT.getClaim("sub");
//        email = emailData.asString();
//        Log.d("EMAIL", email);
//        Call<Account> call = ClientUtils.accountService.getByEmail(email);
//        call.enqueue(new Callback<Account>() {
//            @Override
//            public void onResponse(Call<Account> call, Response<Account> response) {
//                if (response.code() == 200){
//                    account = response.body();
//                    getDataFromClient();
//                    Log.d("ACCOUNT", account.toString());
//                }else{
//                    Log.d("REZ","Meesage recieved: "+response.code());
//                }
//            }
//            @Override
//            public void onFailure(Call<Account> call, Throwable t) {
//                Log.d("REZ", t.getMessage() != null?t.getMessage():"error");
//            }
//        });
//    }
}
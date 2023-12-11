package com.example.bookingappteam9.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.ActivityRegisterScreenBinding;
import com.example.bookingappteam9.model.Address;
import com.example.bookingappteam9.model.Register;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.model.Token;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {
    ActivityRegisterScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Button registerButton = binding.registerButton;
        TextInputLayout firstName = binding.registerFirstName;
        TextInputLayout lastName = binding.registerLastName;
        TextInputLayout email = binding.registerEmail;
        TextInputLayout password = binding.registerPassword;
        TextInputLayout confirmPassword = binding.registerConfirmPassword;
        TextInputLayout country = binding.registerCountry;
        TextInputLayout street = binding.registerStreet;
        TextInputLayout number = binding.registerNumber;
        TextInputLayout city = binding.registerCity;
        TextInputLayout phone = binding.registerPhone;
        RadioButton guest = binding.roleGuest;
        RadioButton host = binding.roleHost;
        confirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(confirmPassword.getEditText().getText().toString().equals(password.getEditText().getText().toString()))) {
                    confirmPassword.setError("Passwords must match!");
                } else {
                    confirmPassword.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameText = firstName.getEditText().getText().toString();
                String lastNameText = lastName.getEditText().getText().toString();
                String emailText = email.getEditText().getText().toString();
                String passwordText = password.getEditText().getText().toString();
                String confirmPasswordText = confirmPassword.getEditText().getText().toString();
                String countryText = country.getEditText().getText().toString();
                String cityText = city.getEditText().getText().toString();
                String streetText = street.getEditText().getText().toString();
                String numberText = number.getEditText().getText().toString();
                String phoneText = phone.getEditText().getText().toString();
                if (!firstNameText.isEmpty() && !lastNameText.isEmpty() && !emailText.isEmpty() &&
                        !passwordText.isEmpty() && !confirmPasswordText.isEmpty() && !countryText.isEmpty() &&
                        !cityText.isEmpty() && !streetText.isEmpty() && !numberText.isEmpty() && !phoneText.isEmpty() && (guest.isChecked() || host.isChecked())){
                    String role;
                    if (guest.isSelected()){
                        role = "Guest";
                    }else{
                        role = "Host";
                    }
                    Call<Token> call = ClientUtils.accountService.register(new Register(emailText, passwordText, firstNameText, lastNameText, phoneText, Role.valueOf(role), new Address(cityText, streetText, countryText, numberText)));
                    call.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if (response.code() == 200){
                                Log.d("Register","Meesage recieved");
                                Intent intent = new Intent(RegisterScreen.this, ConfirmationScreen.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(RegisterScreen.this, "Unsuccessful registration!", Toast.LENGTH_SHORT).show();
                                Log.d("Register","Meesage recieved: "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(RegisterScreen.this, "Server error!", Toast.LENGTH_SHORT).show();
                            Log.d("Register", t.getMessage() != null?t.getMessage():"error");
                        }
                    });
                }else{
                    confirmPassword.setError("All fields are required!");

                }
            }
        });
    }

}
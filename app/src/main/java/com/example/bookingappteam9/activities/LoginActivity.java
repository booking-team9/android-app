package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Login;
import com.example.bookingappteam9.model.Token;
import com.example.bookingappteam9.utils.AccountValidator;
import com.example.bookingappteam9.utils.PrefUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button loginButton = findViewById(R.id.loginButton);
        TextInputLayout userNameText = findViewById(R.id.loginEmail);
        TextInputLayout passwordText = findViewById(R.id.loginPassword);
        AccountValidator validator = new AccountValidator();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameText.getEditText().getText().toString();
                String password = passwordText.getEditText().getText().toString();
                if (!username.isEmpty() && !password.isEmpty()){
                    Call<Token> call = ClientUtils.accountService.login(new Login(username, password));
                    call.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if (response.code() == 200){
                                Log.d("Login","Meesage recieved");
                                String token = response.body().getToken();
                                JWT jwt = new JWT(token);
                                Map<String, Claim> allClaims = jwt.getClaims();
                                String email = jwt.getSubject();
                                Long id = jwt.getClaim("id").asLong();
                                String raw_role = jwt.getClaim("role").asObject(JsonArray.class).get(0).getAsJsonObject().get("authority").getAsString();
                                String role = parseRole(raw_role);
                                Long tokenExp = jwt.getClaim("exp").asLong();
                                PrefUtils.saveLoginInfo(getApplicationContext(), email, token, role, id, tokenExp);
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("role", role);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Unsuccessful login!", Toast.LENGTH_SHORT).show();
                                Log.d("Login","Meesage recieved: "+response.code());
                            }
                        }



                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                            Log.d("Login", t.getMessage() != null?t.getMessage():"error");
                        }
                    });

                }

            }
        });
    }
    private String parseRole(String role){
        switch (role){
            case "ROLE_Host":
                return "Host";
            case "ROLE_Guest":
                return "Guest";
            case "ROLE_Admin":
                return "Admin";
            default:
                return "error";
        }
    }
}
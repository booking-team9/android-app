package com.example.bookingappteam9.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.utils.AccountValidator;

import org.w3c.dom.Text;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
        Button loginButton = findViewById(R.id.loginButton);
        TextView userNameText = findViewById(R.id.loginEmail);
        TextView passwordText = findViewById(R.id.loginPassword);
        AccountValidator validator = new AccountValidator();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.ValidateUserName(userNameText.getText().toString()) && validator.ValidatePassword(passwordText.getText().toString())){
                    Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginScreen.this, "Invalid data!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
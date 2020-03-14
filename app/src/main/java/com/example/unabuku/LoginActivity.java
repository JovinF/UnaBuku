package com.example.unabuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.unabuku.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button loginButton, registerButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.user_namelogin);
        password = findViewById(R.id.user_passwordlogin);
        databaseHelper = new DatabaseHelper(this);

        Button loginButton = findViewById(R.id.login_user);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                if (databaseHelper.isLoginValid(usernameValue, passwordValue)) {

                    Intent loginIntent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(loginIntent);
                    Toast.makeText(LoginActivity.this,"Login Succesvol", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"Onjuiste Gegevens", Toast.LENGTH_SHORT).show();

                }
            }
        });

        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click

                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }

}
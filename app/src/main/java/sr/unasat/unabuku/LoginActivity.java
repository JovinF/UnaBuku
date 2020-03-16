package sr.unasat.unabuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sr.unasat.unabuku.Database.DatabaseHelper;
import sr.unasat.unabuku.Entity.User;

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

        loginButton = findViewById(R.id.login_user);
        registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                LoginUser(usernameValue, passwordValue);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }

    private void LoginUser(String username, String password) {
        User user = databaseHelper.authenticateUser(username, password);
        if (user != null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("LoggedUserID", user.getUserId());
            editor.apply();

            Intent loginIntent = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(loginIntent);
            Toast.makeText(LoginActivity.this, "Login Succesvol", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Onjuiste Gegevens", Toast.LENGTH_SHORT).show();
        }
    }
}
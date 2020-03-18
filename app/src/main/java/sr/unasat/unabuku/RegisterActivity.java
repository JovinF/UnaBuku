package sr.unasat.unabuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sr.unasat.unabuku.Database.UnaBukuDAO;

public class RegisterActivity extends AppCompatActivity {
    EditText name, username, email, password, studnummer;
    Button register;
    UnaBukuDAO unaBukuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        username = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        studnummer = findViewById(R.id.studNummer);

        register = findViewById(R.id.registerUser);

        unaBukuDAO = new UnaBukuDAO(this);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String nameValue = name.getText().toString();
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                String emailValue = email.getText().toString();
                String studnummerValue = password.getText().toString();

                if (usernameValue.length() > 1) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", nameValue);
                    contentValues.put("username", usernameValue);
                    contentValues.put("password", passwordValue);
                    contentValues.put("email", emailValue);
                    contentValues.put("studnummer", studnummerValue);
                    unaBukuDAO.insertOneUser(contentValues);
                    Toast.makeText(RegisterActivity.this, "Registratie succesvol", Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(RegisterActivity.this, MenuActivity.class);
                    startActivity(registerIntent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Vul AUB alles in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

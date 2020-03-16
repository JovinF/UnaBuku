package sr.unasat.unabuku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sr.unasat.unabuku.Database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText username, email, password, studnummer;
    Button register;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        studnummer = findViewById(R.id.user_stud_nummer);
        register = findViewById(R.id.register_user);
        databaseHelper = new DatabaseHelper(this);

        Button registerButton = findViewById(R.id.register_user);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                String emailValue = email.getText().toString();
                String studnummerValue = password.getText().toString();

                if(usernameValue.length()>1){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("user_name",usernameValue);
                    contentValues.put("user_email",emailValue);
                    contentValues.put("user_password",passwordValue);
                    contentValues.put("user_studnummer",studnummerValue);
                    databaseHelper.createUser(contentValues);
                    Toast.makeText(RegisterActivity.this,"Registratie succesvol",Toast.LENGTH_SHORT).show();
                    Intent registerIntent = new Intent(RegisterActivity.this,MenuActivity.class);
                    startActivity(registerIntent);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Vul AUB alles in",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}

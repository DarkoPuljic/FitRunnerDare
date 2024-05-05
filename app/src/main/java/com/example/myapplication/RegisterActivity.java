package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText f_name, l_name, usern, passw;
    Button reg;
    ImageButton ret;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        f_name = findViewById(R.id.firstname);
        l_name = findViewById(R.id.lastname);
        usern = findViewById(R.id.username_reg);
        passw = findViewById(R.id.password_reg);
        reg = findViewById(R.id.btn_reg_reg);
        ret = findViewById(R.id.btn_return_reg);

        reg.setOnClickListener(v -> {
            String firstName = f_name.getText().toString().trim();
            String lastName = l_name.getText().toString().trim();
            String username = usern.getText().toString().trim();
            String password = passw.getText().toString().trim();

            DBHelper dbHelper = new DBHelper(RegisterActivity.this);
            boolean isInserted = dbHelper.addUser(firstName, lastName, username, password);
            dbHelper.close();

            if (isInserted) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
        ret.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });
    }
}
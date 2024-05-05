package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    Button btn_log_login;
    ImageButton btn_ret_login;
    TextView usern, passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_log_login = findViewById(R.id.btn_log_login);
        btn_ret_login = findViewById(R.id.btn_return_login);
        usern = findViewById(R.id.username_log);
        passw = findViewById(R.id.password_log);

        btn_ret_login.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });

        btn_log_login.setOnClickListener(v -> {
            String username = usern.getText().toString().trim();
            String password = passw.getText().toString().trim();

            DBHelper dbHelper = new DBHelper(LoginActivity.this);
            boolean isAuthenticated = dbHelper.checkUser(username, password);
            dbHelper.close();

            if (isAuthenticated) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
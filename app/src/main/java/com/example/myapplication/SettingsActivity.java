package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.util.Date;


public class SettingsActivity extends AppCompatActivity {

    Button btn_prof, btn_logout;
    ImageButton btn_ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_prof = findViewById(R.id.btn_profile_set);
        btn_logout = findViewById(R.id.btn_log_out);
        btn_ret = findViewById(R.id.btn_return_set);

        btn_prof.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        btn_ret.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, WelcomeActivity.class);
            startActivity(intent);
        });
    }
}
package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageButton btn_ret;
    Spinner spinnerWeight, spinnerHeight, spinnerAge;

    // Definišemo ime SharedPreferences fajla
    private static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_ret = findViewById(R.id.btn_return_profile);
        spinnerWeight = findViewById(R.id.spinnerHeight);
        spinnerHeight = findViewById(R.id.spinnerWeight);
        spinnerAge = findViewById(R.id.spinnerAge);

        btn_ret.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Učitavamo vrednosti spinner-a iz SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedWeightPosition = prefs.getInt("weightPosition", 0); // 0 je defaultna vrednost
        int savedHeightPosition = prefs.getInt("heightPosition", 0);
        int savedAgePosition = prefs.getInt("agePosition", 0);

        // Postavljamo odgovarajuće pozicije u spinner-ima
        spinnerWeight.setSelection(savedWeightPosition);
        spinnerHeight.setSelection(savedHeightPosition);
        spinnerAge.setSelection(savedAgePosition);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Čuvamo trenutne pozicije spinner-a u SharedPreferences prilikom napuštanja aktivnosti
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("weightPosition", spinnerWeight.getSelectedItemPosition());
        editor.putInt("heightPosition", spinnerHeight.getSelectedItemPosition());
        editor.putInt("agePosition", spinnerAge.getSelectedItemPosition());
        editor.apply();
    }
}

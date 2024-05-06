package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    ImageButton btnret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        btnret = findViewById(R.id.btn_return_his);

        SharedPreferences sharedPreferences = getSharedPreferences("StopData", MODE_PRIVATE);
        Map<String, ?> allStopData = sharedPreferences.getAll();
        LinearLayout historyLayout = findViewById(R.id.history_layout);

        for (Map.Entry<String, ?> entry : allStopData.entrySet()) {
            String key = entry.getKey();
            long stopTime = sharedPreferences.getLong(key + "_time", 0);
            int distance = sharedPreferences.getInt(key + "_distance", 0);
            long dateInMillis = sharedPreferences.getLong(key + "_date", 0);

            String formattedTime = formatTime(stopTime);
            String formattedDate = formatDate(dateInMillis);

            addStopDataView(historyLayout, formattedTime, distance, formattedDate);
        }
        btnret.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all entries from SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Remove all views from historyLayout
                historyLayout.removeAllViews();
            }
        });
    }

    private String formatTime(long millis) {
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
    }

    private String formatDate(long millis) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return dateFormat.format(new Date(millis));
    }

    private void addStopDataView(ViewGroup parent, String time, int distance, String date) {
        TextView textView = new TextView(this);
        textView.setText("Stop Time: " + time + ", Distance: " + distance + " meters, Date: " + date);
        parent.addView(textView);
    }
}
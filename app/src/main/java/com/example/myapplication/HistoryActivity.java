package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {

    ImageButton ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ret = findViewById(R.id.btn_return_set);

        ret.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
        // Get data from Intent
        long time = getIntent().getLongExtra("time", 0);
        int distance = getIntent().getIntExtra("distance", 0);
        long dateInMillis = getIntent().getLongExtra("date", 0);

        // Convert date to string
        String dateString = DateFormat.getDateTimeInstance().format(new Date(dateInMillis));

        // Find CardView in layout
        CardView cardView = findViewById(R.id.card_view_history);

        // Find TextViews in CardView layout
        TextView timeTextView = cardView.findViewById(R.id.text_time);
        TextView distanceTextView = cardView.findViewById(R.id.text_distance);
        TextView dateTextView = cardView.findViewById(R.id.text_date);

        // Set data to TextViews
        timeTextView.setText("Time: " + time + " milliseconds");
        distanceTextView.setText("Distance: " + distance + " meters");
        dateTextView.setText("Date: " + dateString);
    }
}
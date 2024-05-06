package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng startLatLng;
    private LatLng stopLatLng;
    private TextView distanceText;
    private long startTimeMillis;
    private long stopTimeMillis;
    private CountDownTimer timer;
    private TextView timerText;
    private int distanceInMeters;
    private boolean dataSaved = false;
    private ImageButton btnset, btnhis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        distanceText = findViewById(R.id.distance_text);
        timerText = findViewById(R.id.timer_text);
        btnset = findViewById(R.id.btn_main_set);
        btnhis = findViewById(R.id.btn_main_his);

        btnset.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        btnhis.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, move camera to device's last known location
            moveCameraToLastKnownLocation();
        }
    }

    private void moveCameraToLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng lastKnownLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLocation, 15));
            }
        });
    }

    public void onStartButtonClick(View view) {
        startTimeMillis = System.currentTimeMillis();
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
                updateTimerText(elapsedTimeMillis);
            }

            @Override
            public void onFinish() {
                // Handle timer finish if needed
            }
        };
        timer.start();
        // Get the current location and set it as the start position
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                startLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng, 15));
                Toast.makeText(MainActivity.this, "Start position saved", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onStopButtonClick(View view) {
        stopTimeMillis = System.currentTimeMillis();

        long timeDifferenceMillis = stopTimeMillis - startTimeMillis;
        // Stop the timer
        if (timer != null) {
            timer.cancel();
        }
        // Get the current location and set it as the stop position
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                stopLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (startLatLng != null) {

                    distanceInMeters = (int) calculateDistance(startLatLng, stopLatLng);
                    distanceText.setText("Distance: " + distanceInMeters + " meters");
                    SharedPreferences sharedPreferences = getSharedPreferences("StopData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String key = "stop_" + System.currentTimeMillis();
                    editor.putLong(key + "_time", stopTimeMillis);
                    editor.putInt(key + "_distance", distanceInMeters);
                    editor.putLong(key + "_date", System.currentTimeMillis()); // Save current date
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, "Please set start position first", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Failed to get current location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double calculateDistance(LatLng startLatLng, LatLng stopLatLng) {
        Location startLocation = new Location("");
        startLocation.setLatitude(startLatLng.latitude);
        startLocation.setLongitude(startLatLng.longitude);

        Location stopLocation = new Location("");
        stopLocation.setLatitude(stopLatLng.latitude);
        stopLocation.setLongitude(stopLatLng.longitude);

        return startLocation.distanceTo(stopLocation);
    }
    private void updateTimerText(long elapsedTimeMillis) {
        long seconds = (elapsedTimeMillis / 1000) % 60;
        long minutes = (elapsedTimeMillis / (1000 * 60)) % 60;
        long hours = (elapsedTimeMillis / (1000 * 60 * 60)) % 24;
        String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerText.setText("Elapsed Time: " + timeString);
    }
}



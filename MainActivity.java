
package com.relife.fakegps;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText latInput, lngInput;
    private Button setLocationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latInput = findViewById(R.id.latInput);
        lngInput = findViewById(R.id.lngInput);
        setLocationBtn = findViewById(R.id.setLocationBtn);

        setLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double lat = Double.parseDouble(latInput.getText().toString());
                    double lng = Double.parseDouble(lngInput.getText().toString());
                    mockLocation(lat, lng);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mockLocation(double lat, double lng) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        String providerName = LocationManager.GPS_PROVIDER;

        try {
            lm.addTestProvider(providerName, false, false, false, false, true, true, true, 0, 5);
            lm.setTestProviderEnabled(providerName, true);

            Location mockLocation = new Location(providerName);
            mockLocation.setLatitude(lat);
            mockLocation.setLongitude(lng);
            mockLocation.setAltitude(3);
            mockLocation.setTime(System.currentTimeMillis());
            mockLocation.setAccuracy(1);
            mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());

            lm.setTestProviderLocation(providerName, mockLocation);

            Toast.makeText(this, "Location set to: " + lat + ", " + lng, Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "Enable mock location in Developer Options", Toast.LENGTH_LONG).show();
        }
    }
}

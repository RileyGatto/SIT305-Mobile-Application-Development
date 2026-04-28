package com.example.passtask91p;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.passtask91p.data.DBHelper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        db = new DBHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        Cursor cursor = db.getItems();

// Example: user's current location
        double userLat = -33.8688;  // replace later with real GPS
        double userLng = 151.2093;

// Example radius (km)
        float radius = 5;

        if (cursor.moveToFirst()) {
            do {
                double itemLat = cursor.getDouble(7);
                double itemLng = cursor.getDouble(8);
                String name = cursor.getString(3);

                float[] results = new float[1];

                android.location.Location.distanceBetween(
                        userLat, userLng,
                        itemLat, itemLng,
                        results
                );

                float distanceInKm = results[0] / 1000;

                if (distanceInKm <= radius) {

                    LatLng location = new LatLng(itemLat, itemLng);

                    mMap.addMarker(new MarkerOptions()
                            .position(location)
                            .title(name));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
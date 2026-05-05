package com.example.passtask91p;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.passtask91p.data.DBHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DBHelper db;

    double userLat = 0;
    double userLng = 0;

    // Set Radius
    double RADIUS_KM = 1000;

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

        // Get user location first
        FusedLocationProviderClient client =
                LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        client.getLastLocation().addOnSuccessListener(location -> {

            if (location == null) return;

            userLat = location.getLatitude();
            userLng = location.getLongitude();

            LatLng userLocation = new LatLng(userLat, userLng);

            // Move camera to user
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12f));

            loadMarkersWithinRadius();
        });
    }

    private void loadMarkersWithinRadius() {

        Cursor cursor = db.getItems();

        if (cursor.moveToFirst()) {
            do {
                double lat = cursor.getDouble(7);
                double lng = cursor.getDouble(8);
                String name = cursor.getString(3);

                if (lat == 0 && lng == 0) continue;

                // calcs distance
                float[] results = new float[1];
                Location.distanceBetween(
                        userLat, userLng,
                        lat, lng,
                        results
                );

                float distanceKm = results[0] / 1000f;

                // This filters for radius search
                if (distanceKm <= RADIUS_KM) {

                    LatLng itemLoc = new LatLng(lat, lng);

                    mMap.addMarker(new MarkerOptions()
                            .position(itemLoc)
                            .title(name + " (" + String.format("%.1f km", distanceKm) + ")"));
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
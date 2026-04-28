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

        if (cursor.moveToFirst()) {
            do {
                double lat = cursor.getDouble(7);
                double lng = cursor.getDouble(8);
                String name = cursor.getString(3);

                // skip invalid coordinates
                if (lat == 0 && lng == 0) continue;

                LatLng location = new LatLng(lat, lng);

                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(name));

            } while (cursor.moveToNext());
        }

        cursor.close();

        // Zoom out to show Australia
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(
                new LatLng(-25.2744, 133.7751), // center of Australia
                4f
        ));
    }
}
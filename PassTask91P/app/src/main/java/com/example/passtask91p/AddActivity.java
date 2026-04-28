package com.example.passtask91p;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import com.example.passtask91p.data.DBHelper;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
public class AddActivity extends AppCompatActivity {

    DBHelper db;

    private static final int PICK_IMAGE = 1;

    String imageUri = "";

    double lat = 0, lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        }

        db = new DBHelper(this);
        Spinner categorySpinner;

        categorySpinner = findViewById(R.id.categorySpinner);
        String[] categories = {"Electronics", "Pets", "Clothing", "Stationary", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(adapter);

        Button locationBtn = findViewById(R.id.location_btn);


        locationBtn.setOnClickListener(v -> {

            FusedLocationProviderClient client =
                    LocationServices.getFusedLocationProviderClient(this);

            Toast.makeText(this, "Getting location...", Toast.LENGTH_SHORT).show();

            client.getCurrentLocation(
                    com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                    null
            ).addOnSuccessListener(location -> {

                if (location != null) {

                    double newLat = location.getLatitude();
                    double newLng = location.getLongitude();

                    // reject bad values
                    if (newLat == 0 || newLng == 0) {
                        Toast.makeText(this, "Invalid GPS fix, try again", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    lat = newLat;
                    lng = newLng;

                    ((EditText)findViewById(R.id.Location))
                            .setText("Location Set ✓");

                    Toast.makeText(this,
                            "Lat: " + lat + " Lng: " + lng,
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this,
                            "Could not get location. Try again.",
                            Toast.LENGTH_LONG).show();
                }

            }).addOnFailureListener(e -> {
                Toast.makeText(this,
                        "Location error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            });
        });

        //Image upload button
        Button upload = findViewById(R.id.btnUploadImage);

        upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        EditText locationField = findViewById(R.id.Location);

        locationField.setOnClickListener(v -> {

            List<Place.Field> fields = Arrays.asList(
                    Place.Field.NAME,
                    Place.Field.LAT_LNG
            );

            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);

            startActivityForResult(intent, 100);
        });

        //Home Button
        Button home = findViewById(R.id.btnHomeAdd);
        home.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class))
        );

        //Save Button
        Button save = findViewById(R.id.btnSave);

        save.setOnClickListener(view -> {

            RadioButton foundBtn = findViewById(R.id.Found);

            String type = foundBtn.isChecked() ? "Found" : "Lost";
            String name = ((EditText) findViewById(R.id.Name)).getText().toString().trim();
            String phone = ((EditText) findViewById(R.id.Phone)).getText().toString().trim();
            String desc = ((EditText) findViewById(R.id.Description)).getText().toString().trim();


            String category = categorySpinner.getSelectedItem().toString();

            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            String location = locationField.getText().toString().trim();

            //Valadation
            if (name.isEmpty() || phone.isEmpty() || desc.isEmpty() || location.isEmpty() || imageUri.isEmpty()) {
                Toast.makeText(this, "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            //insert new post in database
            boolean outcome = db.insert(type, category, name, phone, desc, location, lat, lng, imageUri, date
            );

            if (outcome) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Saving: " + lat + ", " + lng, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(this, "Error Saving item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Image Handling
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);

            lat = place.getLatLng().latitude;
            lng = place.getLatLng().longitude;

            ((EditText)findViewById(R.id.Location))
                    .setText(place.getName());
        }

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), uri
                );

                // Convert to Base64
                imageUri = ImageUtil.bitmapToBase64(bitmap);

                // Preview
                ImageView preview = findViewById(R.id.imagePreview);
                preview.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
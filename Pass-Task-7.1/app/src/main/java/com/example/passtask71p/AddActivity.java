package com.example.passtask71p;

import android.content.Intent;
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

import com.example.passtask71p.data.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    DBHelper db;

    private static final int PICK_IMAGE = 1;
    String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        db = new DBHelper(this);
        Spinner categorySpinner;

        categorySpinner = findViewById(R.id.categorySpinner);
        String[] categories = {"Electronics", "Pets", "Wallets"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(

                this,

                android.R.layout.simple_spinner_dropdown_item,

                categories

        );
        categorySpinner.setAdapter(adapter);
        // ========================
        // IMAGE UPLOAD BUTTON
        // ========================
        Button upload = findViewById(R.id.btnUploadImage);

        upload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        // ========================
        // SAVE BUTTON
        // ========================
        Button save = findViewById(R.id.btnSave);

        save.setOnClickListener(view -> {

            RadioButton foundBtn = findViewById(R.id.Found);

            String type = foundBtn.isChecked() ? "Found" : "Lost";
            String name = ((EditText) findViewById(R.id.Name)).getText().toString().trim();
            String phone = ((EditText) findViewById(R.id.Phone)).getText().toString().trim();
            String desc = ((EditText) findViewById(R.id.Description)).getText().toString().trim();
            String location = ((EditText) findViewById(R.id.Location)).getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();

            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

            // =========================
            // VALIDATION (IMPORTANT)
            // =========================
            if (name.isEmpty() ||
                    phone.isEmpty() ||
                    desc.isEmpty() ||
                    location.isEmpty() ||
                    imageUri.isEmpty()) {

                Toast.makeText(this,
                        "Please fill all fields and upload an image",
                        Toast.LENGTH_SHORT).show();
                return; // STOP HERE
            }

            boolean outcome = db.insert(
                    type,
                    category,
                    name,
                    phone,
                    desc,
                    location,
                    imageUri,
                    date
            );

            if (outcome) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Error Saving item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ========================
    // IMAGE RESULT HANDLER
    // ========================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        uri
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
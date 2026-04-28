package com.example.passtask91p;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passtask91p.data.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ItemActivity extends AppCompatActivity {

    DBHelper db;
    int id;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        db = new DBHelper(this);

        id = getIntent().getIntExtra("id", -1);

        // UI elements
        ImageView image = findViewById(R.id.imgItem);

        TextView title = findViewById(R.id.txtTitle);
        TextView category = findViewById(R.id.txtCategory);
        TextView name = findViewById(R.id.txtName);
        TextView phone = findViewById(R.id.txtPhone);
        TextView desc = findViewById(R.id.txtDesc);
        TextView date = findViewById(R.id.txtDate);
        TextView location = findViewById(R.id.txtLocation);

        Cursor cursor = db.getItemById(id);

        if (cursor.moveToFirst()) {

            String type = cursor.getString(1);
            String categoryStr = cursor.getString(2);
            String nameStr = cursor.getString(3);
            String phoneStr = cursor.getString(4);
            String descStr = cursor.getString(5);
            String locStr = cursor.getString(6);
            String imageStr = cursor.getString(9);
            String dateStr = cursor.getString(10);

            // SET DATA
            title.setText(type + " - " + nameStr);
            category.setText("Category: " + categoryStr);
            name.setText("Name: " + nameStr);
            phone.setText("Phone: " + phoneStr);
            desc.setText("Description: " + descStr);
            location.setText("Location: " + locStr);
            date.setText("Listing Created: " + getDaysAgo(dateStr));

            // IMAGE LOAD
            if (imageStr != null && !imageStr.isEmpty()) {

                Bitmap bitmap = ImageUtil.base64ToBitmap(imageStr);
                image.setImageBitmap(bitmap);

            } else {
                image.setImageResource(android.R.drawable.ic_menu_report_image);
            }
        }

        cursor.close();

        // BACK BUTTON
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> finish());

        // DELETE BUTTON
        Button remove = findViewById(R.id.remove_button);
        remove.setOnClickListener(view -> {
            db.delete(id);
            Toast.makeText(this, "Item has been removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    //Time Format class
    private String getDaysAgo(String dateString) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            Date pastDate = sdf.parse(dateString);

            Calendar past = Calendar.getInstance();
            Calendar now = Calendar.getInstance();

            assert pastDate != null;
            past.setTime(pastDate);

            int diff = now.get(Calendar.DAY_OF_YEAR)
                    - past.get(Calendar.DAY_OF_YEAR)
                    + (now.get(Calendar.YEAR) - past.get(Calendar.YEAR)) * 365;

            if (diff == 0) return "Today";
            if (diff == 1) return "Yesterday";

            return diff + " days ago";

        } catch (Exception e) {
            return dateString;
        }
    }
}
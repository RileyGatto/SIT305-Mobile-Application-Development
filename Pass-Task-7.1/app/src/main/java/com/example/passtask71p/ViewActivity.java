package com.example.passtask71p;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passtask71p.adapter.adapter;
import com.example.passtask71p.data.DBHelper;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    DBHelper db;
    RecyclerView recyclerView;
    Spinner filter;

    adapter adapterObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);

        db = new DBHelper(this);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filter = findViewById(R.id.categoryFilter);

        //Spinner Setup
        String[] categories = {"All", "Electronics", "Pets", "Clothing", "Stationary", "Other"};

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );

        filter.setAdapter(spinAdapter);

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // FIRST LOAD
        loadData();

        Button home = findViewById(R.id.btnHome);
        home.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    //Load Data
    void loadData() {

        String selected = filter.getSelectedItem().toString();

        Cursor cursor;

        if (selected.equals("All")) {
            cursor = db.getItems();
        } else {
            cursor = db.getReadableDatabase().rawQuery(
                    "SELECT * FROM items WHERE category=? ORDER BY id DESC",
                    new String[]{selected}
            );
        }

        ArrayList<Integer> newIds = new ArrayList<>();
        ArrayList<String> newType = new ArrayList<>();
        ArrayList<String> newName = new ArrayList<>();
        ArrayList<String> newLocation = new ArrayList<>();
        ArrayList<String> newDate = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                newIds.add(cursor.getInt(0));
                newType.add(cursor.getString(1));
                newName.add(cursor.getString(3)); // NAME = TITLE
                newLocation.add(cursor.getString(6));
                newDate.add(cursor.getString(8));
            } while (cursor.moveToNext());
        }

        cursor.close();

        if (adapterObj == null) {
            adapterObj = new adapter(this, newIds, newType, newName, newLocation, newDate);
            recyclerView.setAdapter(adapterObj);
        } else {
            adapterObj.updateData(newIds, newType, newName, newLocation, newDate);
        }
    }
}
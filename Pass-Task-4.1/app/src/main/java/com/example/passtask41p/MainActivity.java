package com.example.passtask41p;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.passtask41p.ui.AddEventFragment;
import com.example.passtask41p.ui.EventListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottom_navigation);

        loadFragment(new EventListFragment());

        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_list) {
                loadFragment(new EventListFragment());
            } else {
                loadFragment(new AddEventFragment());
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
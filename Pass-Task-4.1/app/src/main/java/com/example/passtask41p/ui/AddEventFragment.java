package com.example.passtask41p.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

import com.example.passtask41p.R;
import com.example.passtask41p.data.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddEventFragment extends Fragment {

    EditText title, location, date, time;
    Spinner category;
    Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_fragment, container, false);

        title = view.findViewById(R.id.eventTitle);
        location = view.findViewById(R.id.etLocation);
        date = view.findViewById(R.id.etDate);
        time = view.findViewById(R.id.etTime);
        category = view.findViewById(R.id.spinnerCategory);
        saveBtn = view.findViewById(R.id.btnSave);

        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        category.setAdapter(adapter);

        saveBtn.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void saveEvent() {
        if (TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(date.getText())) {
            Toast.makeText(getContext(), "Title & Date required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prevent past date
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if (date.getText().toString().compareTo(currentDate) < 0) {
            Toast.makeText(getContext(), "Date cannot be in the past", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event();
        event.title = title.getText().toString();
        event.category = category.getSelectedItem().toString();
        event.location = location.getText().toString();
        event.date = date.getText().toString();
        event.time = time.getText().toString();

        EventDatabase.getInstance(getContext()).eventDao().insert(event);

        Toast.makeText(getContext(), "Event Saved!", Toast.LENGTH_SHORT).show();
    }
}
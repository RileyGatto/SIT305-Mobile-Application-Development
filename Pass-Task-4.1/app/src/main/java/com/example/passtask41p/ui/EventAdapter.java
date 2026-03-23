package com.example.passtask41p.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passtask41p.R;
import com.example.passtask41p.data.*;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvTitle);
            details = view.findViewById(R.id.tvDetails);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event e = events.get(position);

        holder.title.setText(e.title);
        holder.details.setText(e.date + " | " + e.location);


        holder.itemView.setOnClickListener(v -> showUpdateDialog(v, e));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showUpdateDialog(View view, Event event) {

        View dialogView = LayoutInflater.from(view.getContext())
                .inflate(R.layout.update_dialog, null);

        EditText title = dialogView.findViewById(R.id.etTitle);
        EditText location = dialogView.findViewById(R.id.etLocation);
        EditText date = dialogView.findViewById(R.id.etDate);
        EditText time = dialogView.findViewById(R.id.etTime);
        Spinner category = dialogView.findViewById(R.id.spinnerCategory);

        // Spinner setup
        String[] categories = {"Work", "Social", "Travel"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, categories);
        category.setAdapter(adapter);

        // Pre-fill data
        title.setText(event.title);
        location.setText(event.location);
        date.setText(event.date);
        time.setText(event.time);

        // Set selected category
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equals(event.category)) {
                category.setSelection(i);
                break;
            }
        }

        AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Edit Event")
                .setView(dialogView)
                .setPositiveButton("Update", null)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Delete", null) // 👈 DELETE BUTTON INSIDE
                .create();

        dialog.setOnShowListener(d -> {

            // The Update Button
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {

                if (title.getText().toString().isEmpty() || date.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Title & Date required", Toast.LENGTH_SHORT).show();
                    return;
                }

                event.title = title.getText().toString();
                event.category = category.getSelectedItem().toString();
                event.location = location.getText().toString();
                event.date = date.getText().toString();
                event.time = time.getText().toString();

                EventDatabase.getInstance(view.getContext())
                        .eventDao()
                        .update(event);

                notifyDataSetChanged();
                Toast.makeText(view.getContext(), "Updated", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });

            // The DELETE BUTTON
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {

                EventDatabase.getInstance(view.getContext())
                        .eventDao()
                        .delete(event);

                events.remove(event);
                notifyDataSetChanged();

                Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
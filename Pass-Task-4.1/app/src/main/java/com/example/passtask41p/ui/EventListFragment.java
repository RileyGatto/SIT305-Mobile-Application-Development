package com.example.passtask41p.ui;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passtask41p.R;
import com.example.passtask41p.data.*;

import java.util.List;

public class EventListFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadEvents();

        return view;
    }

    private void loadEvents() {
        List<Event> events = EventDatabase.getInstance(getContext())
                .eventDao().getAllEvents();

        recyclerView.setAdapter(new EventAdapter(events));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEvents();
    }
}
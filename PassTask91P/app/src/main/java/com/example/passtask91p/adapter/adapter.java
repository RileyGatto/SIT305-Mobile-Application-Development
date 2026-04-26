package com.example.passtask91p.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passtask91p.ItemActivity;
import com.example.passtask91p.R;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    //Local Variables to store data
    List<Integer> ids; //Unique ID for each entre
    List<String> type;
    List<String> name;
    List<String> location;
    List<String> date;
    Context context;

    public adapter(Context context, List<Integer> ids, List<String> type, List<String> name, List<String> location, List<String> date) {

        this.context = context;
        this.ids = ids;
        this.type = type;
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, subInfo, dateInfo;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.item);
            subInfo = v.findViewById(R.id.subInfo);
            dateInfo = v.findViewById(R.id.dateInfo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.title.setText(name.get(position));
        holder.subInfo.setText(type.get(position));
        holder.dateInfo.setText(date.get(position));

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ItemActivity.class);
            i.putExtra("id", ids.get(position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Integer> ids, List<String> type, List<String> name, List<String> location, List<String> date) {

        this.ids.clear();
        this.type.clear();
        this.name.clear();       
        this.location.clear();
        this.date.clear();

        this.ids.addAll(ids);
        this.type.addAll(type);
        this.name.addAll(name);  
        this.location.addAll(location);
        this.date.addAll(date);

        notifyDataSetChanged();
    }
}

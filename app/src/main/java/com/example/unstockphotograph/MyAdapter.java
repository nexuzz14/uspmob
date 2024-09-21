package com.example.unstockphotograph;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<DataClass> dataList;
    Context context;

    // Constructor
    public MyAdapter(ArrayList<DataClass> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the card layout
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the current data item
        DataClass data = dataList.get(position);

        // Set text data
        holder.judulTextView.setText(data.getJudul());
        holder.deskripsiTextView.setText(data.getDeskripsi());

        // Load image using Glide
        Glide.with(context)
                .load(data.getImageUrl()) // Load the image URL dynamically
                .into(holder.imageView);   // into the ImageView
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // ViewHolder class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView judulTextView, deskripsiTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find views
            imageView = itemView.findViewById(R.id.imageView);
            judulTextView = itemView.findViewById(R.id.judulTextView);
            deskripsiTextView = itemView.findViewById(R.id.deskripsiTextView);
        }
    }
}

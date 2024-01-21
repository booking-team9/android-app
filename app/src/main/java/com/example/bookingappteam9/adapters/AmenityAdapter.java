package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;

import java.util.List;

public class AmenityAdapter extends RecyclerView.Adapter<AmenityAdapter.ViewHolder> {
    private List<String> amenities;

    public AmenityAdapter(List<String> amenities){
        this.amenities = amenities;
    }

    public void addAll(List<String> amenities){
        this.amenities.addAll(amenities);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenity_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAmenity().setText(this.amenities.get(position));

    }

    @Override
    public int getItemCount() {
        return this.amenities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView amenity;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            amenity = (TextView) view.findViewById(R.id.amenity_item);
        }

        public TextView getAmenity() {
            return amenity;
        }
    }
}

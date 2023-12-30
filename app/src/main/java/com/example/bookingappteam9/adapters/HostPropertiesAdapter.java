package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.HostAccommodation;
import com.google.android.material.chip.Chip;

import java.util.List;

public class HostPropertiesAdapter extends RecyclerView.Adapter<HostPropertiesAdapter.ViewHolder> {

    private List<HostAccommodation> cards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView description;
        private final Chip status;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.accommodation_name_host);
            address = (TextView) view.findViewById(R.id.accommodation_host_admin);
            description = (TextView) view.findViewById(R.id.accommodation_description_host);
            status = (Chip) view.findViewById(R.id.accommodation_status_host);
        }

        public TextView getName() {
            return name;
        }

        public TextView getAddress() {
            return address;
        }

        public TextView getDescription() {
            return description;
        }

        public Chip getStatus() {
            return status;
        }
    }

    public HostPropertiesAdapter(List<HostAccommodation> accommodations) {
        this.cards = accommodations;
    }

    @NonNull
    @Override
    public HostPropertiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_host_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostPropertiesAdapter.ViewHolder holder, int position) {
        holder.getName().setText(cards.get(position).getName());
        holder.getAddress().setText(cards.get(position).getLocation());
        holder.getDescription().setText(cards.get(position).getDescription());
        holder.getStatus().setText(cards.get(position).getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void addCards(List<HostAccommodation> accommodations) {
        this.cards.addAll(accommodations);
    }
}

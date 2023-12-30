package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.AccommodationShort;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.List;

public class AllAccommodationsAdapter extends RecyclerView.Adapter<AllAccommodationsAdapter.ViewHolder> {

    private List<AccommodationShort> accommodationList;
    public AllAccommodationsAdapter(List<AccommodationShort> accommodations){
        this.accommodationList = accommodations;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(accommodationList.get(position).getName());
        holder.getAddress().setText(accommodationList.get(position).getLocation());
        holder.getRating().setText(String.valueOf(accommodationList.get(position).getAverageGrade()));
        holder.getPrice().setVisibility(View.INVISIBLE);
        holder.getPriceTag().setVisibility(View.INVISIBLE);
        holder.getDescription().setText(accommodationList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return this.accommodationList.size();
    }
    public void addAll(List<AccommodationShort> accommodationList){
        this.accommodationList.addAll(accommodationList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView description;
        private final TextView rating;
        private final TextView price;
        private final TextView priceTag;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.accommodation_name);
            address = (TextView) view.findViewById(R.id.accommodation_address);
            description = (TextView) view.findViewById(R.id.accommodation_description);
            rating = (TextView) view.findViewById(R.id.accommodation_rating);
            price = (TextView) view.findViewById(R.id.accommodation_price);
            priceTag = (TextView) view.findViewById(R.id.priceTag);
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

        public TextView getRating() {
            return rating;
        }

        public TextView getPrice() {
            return price;
        }
        public TextView getPriceTag(){return priceTag;}
    }
}

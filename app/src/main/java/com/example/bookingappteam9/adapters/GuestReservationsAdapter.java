package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.Reservation;
import com.google.android.material.chip.Chip;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuestReservationsAdapter extends RecyclerView.Adapter<GuestReservationsAdapter.ViewHolder>{
    private List<Reservation> reservations;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView accommodationName;
        private final TextView hostEmail;
        private final TextView dates;
        private final TextView price;
        private final Chip status;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            accommodationName = (TextView) view.findViewById(R.id.property_name_reservation);
            hostEmail = (TextView) view.findViewById(R.id.host_reservation);
            dates = (TextView) view.findViewById(R.id.date_reservation);
            price = (TextView) view.findViewById(R.id.prices_reservation);
            status = (Chip) view.findViewById(R.id.reservations_status);
        }

        public TextView getAccommodationName() {
            return accommodationName;
        }

        public TextView getHostEmail() {
            return hostEmail;
        }

        public TextView getDates() {
            return dates;
        }

        public TextView getPrice() {
            return price;
        }

        public Chip getStatus() {
            return status;
        }

    }

    public GuestReservationsAdapter(List<Reservation> reservations){
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_guest_card, parent, false);
        return new GuestReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestReservationsAdapter.ViewHolder holder, int position) {
        holder.getAccommodationName().setText(reservations.get(position).getAccommodationName());
        holder.getHostEmail().setText(reservations.get(position).getHostEmail());
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM dd");
        String range = reservations.get(position).getStartDate().format(formater) + " - " + reservations.get(position).getEndDate().format(formater);
        holder.getDates().setText(range);
        String priceInfo = Double.toString(reservations.get(position).getPrice()) + " €";
        holder.getPrice().setText(priceInfo);
        holder.getStatus().setText(reservations.get(position).getReservationStatus().toString());
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void addReservations(List<Reservation> reservationss){
        this.reservations.addAll(reservationss);
    }
}

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

public class HostReservationsAdapter extends RecyclerView.Adapter<HostReservationsAdapter.ViewHolder>{
    private List<Reservation> reservations;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView accommodationName;
        private final TextView guestEmail;
        private final TextView dates;
        private final TextView price;
        private final Chip status;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            accommodationName = (TextView) view.findViewById(R.id.accommodation_name_reservation);
            guestEmail = (TextView) view.findViewById(R.id.guest_reservation);
            dates = (TextView) view.findViewById(R.id.dates_reservation);
            price = (TextView) view.findViewById(R.id.price_reservation);
            status = (Chip) view.findViewById(R.id.reservation_status);
        }

        public TextView getAccommodationName() {
            return accommodationName;
        }

        public TextView getGuestEmail() {
            return guestEmail;
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

    public HostReservationsAdapter(List<Reservation> reservations){
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_host_card, parent, false);
        return new HostReservationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAccommodationName().setText(reservations.get(position).getAccommodationName());
        String guestInfo = reservations.get(position).getGuestEmail() + "\n (times canceled: " + String.valueOf( reservations.get(position).getGuestTimesCancelled()) + ")";
        holder.getGuestEmail().setText(guestInfo);
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
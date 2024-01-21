package com.example.bookingappteam9.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.fragments.ReportDialogFragment;
import com.example.bookingappteam9.model.Reservation;
import com.example.bookingappteam9.model.ReservationStatus;
import com.google.android.material.chip.Chip;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HostReservationsAdapter extends RecyclerView.Adapter<HostReservationsAdapter.ViewHolder>{
    private List<Reservation> reservations;
    private final List<Reservation> allReservations;
    private View view;
    private ReservationStatus status;
    private String searchText = "";

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
        this.allReservations = new ArrayList<>(reservations);
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
        holder.getGuestEmail().setText(reservations.get(position).getGuestEmail());
        holder.getGuestEmail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getGuestEmail().setTextColor(Color.BLACK);
                ReportDialogFragment dialogFragment = ReportDialogFragment.newInstance(reservations.get(holder.getBindingAdapterPosition()).getGuestId());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                dialogFragment.show(ft, "dialog");
            }
        });
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
        this.allReservations.addAll(reservationss);
    }

    public void filterReservations(ReservationStatus status){
        this.status = status;
        this.reservations = this.allReservations.stream().filter(reservation -> reservation.getReservationStatus().equals(status)).collect(Collectors.toList());
        searchReservations(this.searchText);
        notifyDataSetChanged();
    }
    public void showALlStatuses(){
        this.status = null;
        this.reservations = new ArrayList<>(this.allReservations);
        searchReservations(this.searchText);
        notifyDataSetChanged();
    }

    public void showALl(){
        this.reservations = new ArrayList<>(this.allReservations);
        this.searchText = "";
        filterReservations(this.status);
        notifyDataSetChanged();
    }

    public void searchReservations(String text){
        this.searchText = text;
        this.reservations = this.allReservations
                .stream()
                .filter(reservation -> reservation.getAccommodationName().toLowerCase().contains(text.toLowerCase()) && (this.status==null || reservation.getReservationStatus().equals(this.status)))
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }


}

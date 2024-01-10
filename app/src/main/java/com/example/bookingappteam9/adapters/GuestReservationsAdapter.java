package com.example.bookingappteam9.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.fragments.AdapterClickListener;
import com.example.bookingappteam9.fragments.ReviewDialogFragment;
import com.example.bookingappteam9.model.Reservation;
import com.example.bookingappteam9.model.ReservationStatus;
import com.google.android.material.chip.Chip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GuestReservationsAdapter extends RecyclerView.Adapter<GuestReservationsAdapter.ViewHolder> {
    private List<Reservation> reservations;
    private AdapterClickListener listener;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView accommodationName;
        private final TextView hostEmail;
        private final TextView dates;
        private final TextView price;
        private final Chip status;
        private final TextView reviewText;
        private final Button reviewAccommodation;
        private final Button reviewHost;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            accommodationName = (TextView) view.findViewById(R.id.property_name_reservation);
            hostEmail = (TextView) view.findViewById(R.id.host_reservation);
            dates = (TextView) view.findViewById(R.id.date_reservation);
            price = (TextView) view.findViewById(R.id.prices_reservation);
            status = (Chip) view.findViewById(R.id.reservations_status);
            reviewText = (TextView) view.findViewById(R.id.review_text_card);
            reviewHost = (Button) view.findViewById(R.id.add_review_host);
            reviewAccommodation = (Button) view.findViewById(R.id.add_review_accommodation);
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

        public TextView getReviewText() {
            return reviewText;
        }

        public Button getReviewAccommodation() {
            return reviewAccommodation;
        }

        public Button getReviewHost() {
            return reviewHost;
        }
    }

    public GuestReservationsAdapter(List<Reservation> reservations, AdapterClickListener listener) {
        this.reservations = reservations;
        this.listener = listener;
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
        holder.getHostEmail().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getHostEmail().setTextColor(Color.BLACK);
                listener.onClick(reservations.get(holder.getBindingAdapterPosition()).getHostId());
            }
        });
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM dd");
        String range = reservations.get(position).getStartDate().format(formater) + " - " + reservations.get(position).getEndDate().format(formater);
        holder.getDates().setText(range);
        String priceInfo = Double.toString(reservations.get(position).getPrice()) + " €";
        holder.getPrice().setText(priceInfo);
        holder.getStatus().setText(reservations.get(position).getReservationStatus().toString());
        if (reservations.get(position).getReservationStatus().equals(ReservationStatus.Done) && reservations.get(position).getEndDate().plusDays(7).isBefore(LocalDateTime.now())) {
            holder.reviewText.setVisibility(View.VISIBLE);
            holder.reviewHost.setVisibility(View.VISIBLE);
            holder.reviewAccommodation.setVisibility(View.VISIBLE);
            holder.reviewAccommodation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReviewDialogFragment dialogFragment = ReviewDialogFragment.newInstance("Accommodation review", "accommodation", reservations.get(holder.getBindingAdapterPosition()).getAccommodationId());
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

        }
        holder.reviewHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewDialogFragment dialogFragment = ReviewDialogFragment.newInstance("Host review", "host", reservations.get(holder.getBindingAdapterPosition()).getHostId());
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
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void addReservations(List<Reservation> reservationss) {
        this.reservations.addAll(reservationss);
    }
}

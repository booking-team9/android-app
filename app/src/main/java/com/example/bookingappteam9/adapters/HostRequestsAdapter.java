package com.example.bookingappteam9.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Reservation;
import com.example.bookingappteam9.model.ReservationStatus;
import com.google.android.material.chip.Chip;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostRequestsAdapter extends RecyclerView.Adapter<HostRequestsAdapter.ViewHolder>{
    private List<Reservation> reservations;
    private View view;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView accommodationName;
        private final TextView guestEmail;
        private final TextView dates;
        private final TextView price;
        private final Chip status;
        private Button accept;
        private Button decline;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            accommodationName = (TextView) view.findViewById(R.id.accommodation_name_request);
            guestEmail = (TextView) view.findViewById(R.id.guest_request);
            dates = (TextView) view.findViewById(R.id.dates_request);
            price = (TextView) view.findViewById(R.id.price_request);
            status = (Chip) view.findViewById(R.id.request_status);
            accept = (Button) view.findViewById(R.id.accept_button_host);
            decline = (Button) view.findViewById(R.id.decline_button_host);
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

        public Button getAccept() {
            return accept;
        }

        public Button getDecline() {
            return decline;
        }
    }

    public HostRequestsAdapter(List<Reservation> reservations){
        this.reservations = reservations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_host_card, parent, false);
        return new HostRequestsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostRequestsAdapter.ViewHolder holder, int position) {
        holder.getAccommodationName().setText(reservations.get(position).getAccommodationName());
        String guestInfo = reservations.get(position).getGuestEmail() + "\n (times canceled: " + String.valueOf( reservations.get(position).getGuestTimesCancelled()) + ")";
        holder.getGuestEmail().setText(guestInfo);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM dd");
        String range = reservations.get(position).getStartDate().format(formater) + " - " + reservations.get(position).getEndDate().format(formater);
        holder.getDates().setText(range);
        String priceInfo = Double.toString(reservations.get(position).getPrice()) + " €";
        holder.getPrice().setText(priceInfo);
        holder.getStatus().setText(reservations.get(position).getReservationStatus().toString());

        holder.getAccept().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = ClientUtils.reservationService.approveReservation(reservations.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Reservation accepted successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    findNavController(view).navigate(R.id.action_navigation_admin_accommodations_self);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                            reservations.remove(holder.getBindingAdapterPosition());
                            notifyItemRemoved(holder.getBindingAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                        builderBad.setTitle("Fail");
                        builderBad.setMessage("Failed to accept reservation");
                        builderBad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertBad = builderBad.create();
                        alertBad.show();
                    }
                });
            }
        });

        holder.getDecline().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = ClientUtils.reservationService.denyReservation(reservations.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Reservation declined successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    findNavController(view).navigate(R.id.action_navigation_admin_accommodations_self);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                            reservations.get(holder.getBindingAdapterPosition()).setReservationStatus(ReservationStatus.Denied);
                            notifyItemChanged(holder.getBindingAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                        builderBad.setTitle("Fail");
                        builderBad.setMessage("Failed to decline reservation");
                        builderBad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertBad = builderBad.create();
                        alertBad.show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void addReservations(List<Reservation> reservationss){
        this.reservations.addAll(reservationss);
    }

}

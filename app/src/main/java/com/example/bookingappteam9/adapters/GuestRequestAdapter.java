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
import com.google.android.material.chip.Chip;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestRequestAdapter extends RecyclerView.Adapter<GuestRequestAdapter.ViewHolder>{
    private List<Reservation> reservations;
    private View view;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView accommodationName;
        private final TextView hostEmail;
        private final TextView dates;
        private final TextView price;
        private final Chip status;
        private Button cancel;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            accommodationName = (TextView) view.findViewById(R.id.property_name_request);
            hostEmail = (TextView) view.findViewById(R.id.host_request);
            dates = (TextView) view.findViewById(R.id.date_request);
            price = (TextView) view.findViewById(R.id.prices_request);
            status = (Chip) view.findViewById(R.id.requests_status);
            cancel = (Button) view.findViewById(R.id.cancel_button_guest);
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

        public Button getCancel() {
            return cancel;
        }
    }

    public GuestRequestAdapter(List<Reservation> reservations){
        this.reservations = reservations;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_guest_card, parent, false);
        return new GuestRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAccommodationName().setText(reservations.get(position).getAccommodationName());
        holder.getHostEmail().setText(reservations.get(position).getHostEmail());
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM dd");
        String range = reservations.get(position).getStartDate().format(formater) + " - " + reservations.get(position).getEndDate().format(formater);
        holder.getDates().setText(range);
        String priceInfo = Double.toString(reservations.get(position).getPrice()) + " €";
        holder.getPrice().setText(priceInfo);
        holder.getStatus().setText(reservations.get(position).getReservationStatus().toString());

        holder.getCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = ClientUtils.reservationService.cancelReservation(reservations.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Reservation canceled successfully");
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
                        builderBad.setMessage("It is too late to cancel reservation");
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

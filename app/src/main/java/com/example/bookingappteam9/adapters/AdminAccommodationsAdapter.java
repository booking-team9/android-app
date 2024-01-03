package com.example.bookingappteam9.adapters;

import static androidx.navigation.ViewKt.findNavController;

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
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAccommodationsAdapter extends RecyclerView.Adapter<AdminAccommodationsAdapter.ViewHolder> {

    private List<HostAccommodation> accommodationList;
    private View view;
    public AdminAccommodationsAdapter(List<HostAccommodation> accommodationList){

        this.accommodationList = accommodationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_admin_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(accommodationList.get(position).getName());
        holder.getAddress().setText(accommodationList.get(position).getLocation());
        holder.getHost().setText(String.valueOf(accommodationList.get(position).getHostName()));
        holder.getStatus().setText(accommodationList.get(position).getStatus().toString());
        holder.getApproveButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<HostAccommodation> call = ClientUtils.accommodationService.approveAccommodation(accommodationList.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<HostAccommodation>() {
                    @Override
                    public void onResponse(Call<HostAccommodation> call, Response<HostAccommodation> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Accommodation approved successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    findNavController(view).navigate(R.id.action_navigation_admin_accommodations_self);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HostAccommodation> call, Throwable t) {
                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                        builderBad.setTitle("Fail");
                        builderBad.setMessage("Failed to approve accommodation");
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


        holder.getDenyButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HostAccommodation> call = ClientUtils.accommodationService.denyAccommodation(accommodationList.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<HostAccommodation>() {
                    @Override
                    public void onResponse(Call<HostAccommodation> call, Response<HostAccommodation> response) {
                        if (response.code() == 200) {
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Accommodation denied successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    findNavController(view).navigate(R.id.action_navigation_admin_accommodations_self);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HostAccommodation> call, Throwable t) {
                        AlertDialog.Builder builderBad = new AlertDialog.Builder(v.getContext());
                        builderBad.setTitle("Fail");
                        builderBad.setMessage("Failed to deny accommodation");
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
        return this.accommodationList.size();
    }

    public void addAll(List<HostAccommodation> accommodations){
        this.accommodationList.addAll(accommodations);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView host;
        private final TextView status;
        private final Button approveButton;
        private final Button denyButton;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.accommodation_name_admin);
            address = (TextView) view.findViewById(R.id.accommodation_address_admin);
            status = (TextView) view.findViewById(R.id.accommodation_status_admin);
            host = (TextView) view.findViewById(R.id.accommodation_host_admin);
            approveButton = (Button) view.findViewById(R.id.approve_button_admin);
            denyButton = (Button) view.findViewById(R.id.deny_button_admin);
        }

        public TextView getName() {
            return name;
        }

        public TextView getAddress() {
            return address;
        }

        public TextView getHost() {
            return host;
        }

        public TextView getStatus() {
            return status;
        }

        public Button getApproveButton() {
            return approveButton;
        }

        public Button getDenyButton() {
            return denyButton;
        }
    }
}

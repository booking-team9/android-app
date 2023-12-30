package com.example.bookingappteam9.adapters;

import static androidx.navigation.ViewKt.findNavController;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.HostAccommodation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAccommodationListAdapter extends ArrayAdapter<HostAccommodation> {
    private ArrayList<HostAccommodation> accommodations;
    public AdminAccommodationListAdapter(Context context, ArrayList<HostAccommodation> accommodationss){
        super(context, R.layout.accommodation_host_card, accommodationss);
        this.accommodations = accommodationss;
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Nullable
    @Override
    public HostAccommodation getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HostAccommodation accommodation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_admin_card,
                    parent, false);
        }
        RelativeLayout productCard = convertView.findViewById(R.id.accommodation_card_admin);
        TextView status = convertView.findViewById(R.id.accommodation_status_admin);
        TextView host = convertView.findViewById(R.id.accommodation_host_admin);
        TextView name = convertView.findViewById(R.id.accommodation_name_admin);
        TextView address = convertView.findViewById(R.id.accommodation_address_admin);
        Button approveButton = convertView.findViewById(R.id.approve_button_admin);
        Button denyButton = convertView.findViewById(R.id.deny_button_admin);

        if (accommodation != null) {
            status.setText(accommodation.getStatus().toString());
            name.setText(accommodation.getName());
            //address.setText(accommodation.getAddress());
            host.setText("df");
        }
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<HostAccommodation> call = ClientUtils.accommodationService.approveAccommodation(accommodation.getId());
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
                                    findNavController(parent).navigate(R.id.action_navigation_admin_accommodations_self);
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


        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HostAccommodation> call = ClientUtils.accommodationService.denyAccommodation(accommodation.getId());
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
                                    findNavController(parent).navigate(R.id.action_navigation_admin_accommodations_self);
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

        return convertView;
    }

}

package com.example.bookingappteam9.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.AdminReport;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReportsAdapter extends RecyclerView.Adapter<AdminReportsAdapter.ViewHolder> {
    List<AdminReport> reports;
    private View view;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView reportedUserName;
        private final TextView authorName;
        private final TextView date;
        private final TextView reportReason;

        private final Button blockUser;
        private final Button deleteReport;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            reportedUserName = (TextView) view.findViewById(R.id.reported_user_name);
            authorName = (TextView) view.findViewById(R.id.report_author_name);
            date = (TextView) view.findViewById(R.id.report_date);
            reportReason = (TextView) view.findViewById(R.id.report_description);
            blockUser = (Button) view.findViewById(R.id.block_button_admin);
            deleteReport = (Button) view.findViewById(R.id.delete_report_button);
         }

        public TextView getReportedUserName() {
            return reportedUserName;
        }

        public TextView getAuthorName() {
            return authorName;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getReportReason() {
            return reportReason;
        }

        public Button getBlockUser() {
            return blockUser;
        }

        public Button getDeleteReport() {
            return deleteReport;
        }
    }

    public AdminReportsAdapter(List<AdminReport> reportList){
        this.reports = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_card, parent, false);
        return new AdminReportsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getReportedUserName().setText(reports.get(position).getReportedUserEmail());
        holder.getAuthorName().setText(reports.get(position).getAuthorEmail());
        holder.getReportReason().setText(reports.get(position).getReason());
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM - dd - yyyy");
        holder.getDate().setText(reports.get(position).getDate().format(formater));
        holder.getBlockUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = ClientUtils.accountService.blockAccount(reports.get(holder.getBindingAdapterPosition()).getReportedUserId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("User blocked successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                            reports.remove(holder.getBindingAdapterPosition());
                            notifyItemRemoved(holder.getBindingAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Failed to block account!", Toast.LENGTH_SHORT).show();
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });

        holder.getDeleteReport().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = ClientUtils.reportService.deleteReport(reports.get(holder.getBindingAdapterPosition()).getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code()==200){
                            AlertDialog.Builder builderGood = new AlertDialog.Builder(v.getContext());
                            builderGood.setTitle("Success");
                            builderGood.setMessage("Report deleeted successfully");
                            builderGood.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertGood = builderGood.create();
                            alertGood.show();
                            reports.remove(holder.getBindingAdapterPosition());
                            notifyItemRemoved(holder.getBindingAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Failed to delete report!", Toast.LENGTH_SHORT).show();
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.reports.size();
    }

    public void addReports(List<AdminReport> reportList){
        this.reports.addAll(reportList);
    }




}

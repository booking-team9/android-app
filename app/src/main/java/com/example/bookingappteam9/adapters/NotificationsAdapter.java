package com.example.bookingappteam9.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Notification;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private List<Notification> notifications;
    private View view;
    public NotificationsAdapter(List<Notification> notifications){
        this.notifications = notifications;
    }
    public void addAll(List<Notification> notifications){
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }
    public void dismissNotification(){

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getDate().setText(this.notifications.get(position).getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm")));
        holder.getText().setText(this.notifications.get(position).getMessage());
        holder.getDismiss().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientUtils.notificationsService.dismissNotification(notifications.get(holder.getBindingAdapterPosition()).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(view.getContext(), "Notification dismissed!", Toast.LENGTH_SHORT).show();
                            notifications.remove(notifications.get(holder.getBindingAdapterPosition()));
                            notifyItemRemoved(holder.getBindingAdapterPosition());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;
        private final TextView date;
        private final Button dismiss;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            date = view.findViewById(R.id.notification_date);
            dismiss = view.findViewById(R.id.dismiss_notification);
            text = view.findViewById(R.id.notification_text);
        }

        public TextView getText() {
            return text;
        }

        public TextView getDate() {
            return date;
        }

        public Button getDismiss() {
            return dismiss;
        }
    }
}

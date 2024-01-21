package com.example.bookingappteam9.adapters;

import android.media.Rating;
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
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.ReviewStatus;
import com.google.android.material.chip.Chip;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReviewsAdapter extends RecyclerView.Adapter<AdminReviewsAdapter.ViewHolder> {
    private List<Review> reviews;
    private final List<Review> allReviews;
    private View view;
    private ReviewStatus status;

    public AdminReviewsAdapter(List<Review> reviews){
        this.reviews = reviews;
        this.allReviews = new ArrayList<>(reviews);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_review_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String authorName = this.reviews.get(position).getAuthor().getFirstName() + this.reviews.get(position).getAuthor().getLastName();
        holder.getAuthor().setText(authorName);
        holder.getTimestamp().setText(this.reviews.get(position).getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        holder.getComment().setText(this.reviews.get(position).getComment());
        holder.getStatus().setText(this.reviews.get(position).getStatus().toString());
        holder.getGrade().setRating(this.reviews.get(position).getGrade());
        ReviewStatus status = reviews.get(position).getStatus();
        if (status.equals(ReviewStatus.Pending)){
            holder.getApprove().setEnabled(true);
            holder.getDelete().setEnabled(true);
        }else if(status.equals(ReviewStatus.Reported)){
            holder.getDelete().setEnabled(true);
        }else{
            holder.getDelete().setEnabled(false);
            holder.getApprove().setEnabled(false);
        }
        holder.getApprove().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientUtils.reviewService.approveReview(reviews.get(holder.getBindingAdapterPosition()).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(view.getContext(), "Review successfully approved!", Toast.LENGTH_SHORT).show();
                            reload();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null?t.getMessage():"error");

                    }
                });

            }
        });
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientUtils.reviewService.deleteReview(reviews.get(holder.getBindingAdapterPosition()).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(view.getContext(), "Review successfully deleted!", Toast.LENGTH_SHORT).show();
                            reload();

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null?t.getMessage():"error");

                    }
                });

            }
        });
    }
    private void reload(){
        ClientUtils.reviewService.getAll().enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if (response.isSuccessful()){
                    reviews.clear();
                    allReviews.clear();
                    addAll(response.body());
                    if (status != null){
                        filterReviews(status);
                    }

                    Log.d("QM", "Reviews successfully loaded!");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");

            }
        });
    }
    public void addAll(List<Review> reviews){
        this.reviews.addAll(reviews);
        this.allReviews.addAll(reviews);
        notifyDataSetChanged();
    }
    public void filterReviews(ReviewStatus status){
        this.status = status;
        this.reviews = this.allReviews.stream().filter(review -> review.getStatus().equals(status)).collect(Collectors.toList());
        notifyDataSetChanged();
    }
    public void showALl(){
        this.status = null;
        this.reviews = this.allReviews;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView author;
        private final TextView timestamp;
        private final Button delete;
        private final Button approve;
        private final TextView comment;
        private final RatingBar grade;
        private final Chip status;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            author = view.findViewById(R.id.admin_card_author);
            timestamp = view.findViewById(R.id.admin_card_date);
            delete = view.findViewById(R.id.admin_review_delete);
            approve = view.findViewById(R.id.admin_review_approve);
            comment = view.findViewById(R.id.admin_review_text);
            grade = view.findViewById(R.id.admin_review_rating);
            status = view.findViewById(R.id.admin_review_status);
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getTimestamp() {
            return timestamp;
        }

        public Button getDelete() {
            return delete;
        }

        public Button getApprove() {
            return approve;
        }

        public TextView getComment() {
            return comment;
        }

        public RatingBar getGrade() {
            return grade;
        }

        public Chip getStatus() {
            return status;
        }
    }
}

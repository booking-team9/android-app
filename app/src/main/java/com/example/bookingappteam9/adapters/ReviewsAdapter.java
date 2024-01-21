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
import com.example.bookingappteam9.model.Review;

import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Review> reviewList;
    private boolean isHostOwner;
    private View view;
    public ReviewsAdapter(List<Review> reviews, boolean isHostOwner){
        this.reviewList =reviews;
        this.isHostOwner = isHostOwner;
    }
    public void addAll(List<Review> reviewList){
        this.reviewList.addAll(reviewList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAuthor().setText(this.reviewList.get(position).getAuthor().toString());
        holder.getComment().setText(this.reviewList.get(position).getComment());
        holder.getDate().setText(this.reviewList.get(position).getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")));
        holder.getRating().setRating(this.reviewList.get(position).getGrade());
        if (isHostOwner){
            holder.getReport().setVisibility(View.VISIBLE);
            holder.getReport().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientUtils.reviewService.reportReview(reviewList.get(holder.getBindingAdapterPosition()).getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(view.getContext(), "Review successfully reported!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(view.getContext(), "Review already reported!", Toast.LENGTH_SHORT).show();

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

    }

    @Override
    public int getItemCount() {
        return this.reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView comment;
        private final TextView author;
        private final TextView date;
        private final Button report;
        private final RatingBar rating;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            comment = view.findViewById(R.id.review_comment);
            author = view.findViewById(R.id.review_author);
            date = view.findViewById(R.id.review_date);
            report = view.findViewById(R.id.review_report);
            rating = view.findViewById(R.id.review_rating);
        }

        public TextView getComment() {
            return comment;
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getDate() {
            return date;
        }

        public Button getReport() {
            return report;
        }

        public RatingBar getRating() {
            return rating;
        }
    }
}

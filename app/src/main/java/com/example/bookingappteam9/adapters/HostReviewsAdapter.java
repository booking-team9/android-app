package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.User;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HostReviewsAdapter extends RecyclerView.Adapter<HostReviewsAdapter.ViewHolder>{
    List<Review> reviews;
    private View view;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView reporterName;
        private final TextView reportDate;
        private final TextView description;
        private final TextView reviewRating;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            reporterName = (TextView) view.findViewById(R.id.reporter_name);
            reportDate = (TextView) view.findViewById(R.id.review_date);
            description = (TextView) view.findViewById(R.id.review_description);
            reviewRating = (TextView) view.findViewById(R.id.review_rating);
        }

        public TextView getReporterName() {
            return reporterName;
        }

        public TextView getReportDate() {
            return reportDate;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getReviewRating() {
            return reviewRating;
        }
    }

    public HostReviewsAdapter(List<Review> reviewList){
        this.reviews = reviewList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_card, parent, false);
        return new HostReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User author = reviews.get(position).getAuthor();
        holder.getReporterName().setText(author.getFirstName() + " " + author.getLastName());
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("MMM - dd - yyyy");
        holder.getReportDate().setText(reviews.get(position).getDate().format(formater));
        holder.getDescription().setText(reviews.get(position).getComment());
        holder.getReviewRating().setText(String.valueOf(reviews.get(position).getGrade()));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addReviews(List<Review> reviewList){
        this.reviews.addAll(reviewList);
    }
}

package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.Review;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Review> reviewList;
    public ReviewsAdapter(List<Review> reviews){
        this.reviewList =reviews;
    }
    public void addAll(List<Review> reviewList){
        this.reviewList.addAll(reviewList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getAuthor().setText(this.reviewList.get(position).getAuthor().toString());
        holder.getComment().setText(this.reviewList.get(position).getComment());
        holder.getDate().setText(this.reviewList.get(position).getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")));
        holder.getRating().setRating(this.reviewList.get(position).getGrade());
        holder.getReport().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

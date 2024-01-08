package com.example.bookingappteam9.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentReviewDialogBinding;
import com.example.bookingappteam9.model.Review;
import com.example.bookingappteam9.model.ReviewStatus;
import com.example.bookingappteam9.model.Token;
import com.example.bookingappteam9.model.User;
import com.example.bookingappteam9.utils.PrefUtils;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewDialogFragment extends DialogFragment {
    private static Long subjectId;
    private FragmentReviewDialogBinding binding;
    private EditText reviewText;
    private Button submitButton;
    private Button cancelButton;
    private static String dialogTitle;
    private RatingBar reviewRating;
    private static String reviewType;
    private Button deleteButton;
    private Long reviewId;

    public ReviewDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewDialogFragment newInstance(String param1, String param2, Long id) {
        ReviewDialogFragment fragment = new ReviewDialogFragment();
        dialogTitle = param1;
        reviewType = param2;
        subjectId = id;
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        reviewText = binding.reviewDialogText;
        submitButton = binding.submitReview;
        cancelButton = binding.cancelReview;
        deleteButton = binding.deleteReview;
        reviewRating = binding.reviewDialogRating;
        Long userId = PrefUtils.getUserInfo(getContext().getApplicationContext()).getId();

        if (reviewType.equals("host")) {
            ClientUtils.reviewService.findHostReview(subjectId, userId).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        reviewText.setText(response.body().getComment());
                        reviewRating.setRating(response.body().getGrade());
                        deleteButton.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.GONE);
                        reviewText.setEnabled(false);
                        reviewRating.setEnabled(false);
                        reviewId = response.body().getId();

                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                }
            });
        } else {
            ClientUtils.reviewService.findAccommodationReview(subjectId, userId).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        reviewText.setText(response.body().getComment());
                        reviewRating.setRating(response.body().getGrade());
                        deleteButton.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.GONE);
                        reviewText.setEnabled(false);
                        reviewRating.setEnabled(false);
                        reviewId = response.body().getId();
                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                }
            });
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientUtils.reviewService.deleteReview(reviewId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getContext(), "Review successfully deleted!", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                    }
                });
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!reviewText.getText().toString().isEmpty() && reviewRating.getRating() != 0) {
                    Review review = new Review();
                    review.setGrade((int) reviewRating.getRating());
                    review.setComment(reviewText.getText().toString());
                    review.setDate(LocalDateTime.now());
                    review.setAuthor(new User(PrefUtils.getUserInfo(getActivity().getApplicationContext()).getId()));
                    if (reviewType.equals("host")) {
                        review.setStatus(ReviewStatus.Approved);
                        review.setAccommodationId(null);
                        review.setHostId(subjectId);
                    } else {
                        review.setStatus(ReviewStatus.Pending);
                        review.setHostId(null);
                        review.setAccommodationId(subjectId);
                    }
                    ClientUtils.reviewService.saveNewReview(review).enqueue(new Callback<Review>() {
                        @Override
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            Toast.makeText(getContext(), "Review successfully submitted!", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }

                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                            Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(dialogTitle);
    }
}
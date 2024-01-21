package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentReportDialogBinding;
import com.example.bookingappteam9.model.Report;
import com.example.bookingappteam9.utils.PrefUtils;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDialogFragment extends DialogFragment {

    private static Long hostId;
    private FragmentReportDialogBinding binding;
    private TextView reportReasonText;
    private Button submitButton;
    private Button cancelButton;



    public ReportDialogFragment() {
        // Required empty public constructor
    }

    public static ReportDialogFragment newInstance(Long id) {
        ReportDialogFragment fragment = new ReportDialogFragment();
        Bundle args = new Bundle();
        hostId = id;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        reportReasonText = binding.reportDialogText;
        submitButton = binding.submitReport;
        cancelButton = binding.cancelReport;

        Long authorId = PrefUtils.getUserInfo(getContext().getApplicationContext()).getId();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!reportReasonText.getText().toString().isEmpty()){
                    Report report = new Report();
                    report.setReason(reportReasonText.getText().toString());
                    report.setDate(LocalDateTime.now());
                    report.setAuthorId(authorId);
                    report.setReportedUserId(hostId);

                    ClientUtils.reportService.saveNewReport(report).enqueue(new Callback<Report>() {
                        @Override
                        public void onResponse(Call<Report> call, Response<Report> response) {
                            Toast.makeText(getContext(), "Peport successfully submitted!", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }

                        @Override
                        public void onFailure(Call<Report> call, Throwable t) {
                            Toast.makeText(getContext(), "You can't report this host!", Toast.LENGTH_SHORT).show();
                            Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "You didn't write your reason!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return root;
    }
}
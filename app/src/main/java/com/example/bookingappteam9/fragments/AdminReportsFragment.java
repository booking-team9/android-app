package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bookingappteam9.adapters.AdminReportsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentAdminReportsBinding;
import com.example.bookingappteam9.model.AdminReport;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReportsFragment extends Fragment {
    private AdminReportsAdapter adapter;
    private FragmentAdminReportsBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdminReportsFragment() {
        // Required empty public constructor
    }

    public static AdminReportsFragment newInstance(String param1, String param2) {
        AdminReportsFragment fragment = new AdminReportsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminReportsBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        ArrayList<AdminReport> reports = new ArrayList<>();
        adapter = new AdminReportsAdapter(reports);
        binding.adminReportsList.setAdapter(adapter);
        binding.adminReportsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<ArrayList<AdminReport>> call = ClientUtils.reportService.getAll();
        call.enqueue(new Callback<ArrayList<AdminReport>>() {
            @Override
            public void onResponse(Call<ArrayList<AdminReport>> call, Response<ArrayList<AdminReport>> response) {
                if(response.code()==200){
                    List<AdminReport> reportsRaw = response.body();
                    adapter.addReports(reportsRaw);
                    adapter.notifyDataSetChanged();
                    if(binding!=null)
                        binding.progressLoaderAdminReports.setVisibility(View.INVISIBLE);
                }
                else {
                    Log.d("QM","Meesage recieved: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AdminReport>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null?t.getMessage():"error");
            }
        });

        return root;
    }
}
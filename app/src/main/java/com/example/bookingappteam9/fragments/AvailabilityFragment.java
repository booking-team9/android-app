package com.example.bookingappteam9.fragments;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.AvailabilityAdapter;
import com.example.bookingappteam9.databinding.FragmentAvailabilityBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailabilityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentAvailabilityBinding binding;
    private AvailabilityAdapter availabilityAdapter;
    private MaterialDatePicker datePicker;
    private String picked_date;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvailabilityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvailabilityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvailabilityFragment newInstance(String param1, String param2) {
        AvailabilityFragment fragment = new AvailabilityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAvailabilityBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        List<Pair<String, String>> availability = new ArrayList<>();
        availabilityAdapter = new AvailabilityAdapter(availability);
        binding.recyclerView.setAdapter(availabilityAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        datePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(new Pair<>(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.thisMonthInUtcMilliseconds())).build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                picked_date = datePicker.getHeaderText();
                binding.inputDate.getEditText().setText(picked_date);
                System.out.println(picked_date);
            }
        });
        binding.inputDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("date clicked");
                datePicker.show(getChildFragmentManager(), "123");
            }
        });
        binding.addSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = binding.inputPrice.getEditText().getText().toString();
                if (picked_date != "" && price != ""){
                    availabilityAdapter.addSlot(new Pair<>(picked_date,"$" + price));
                    availabilityAdapter.notifyDataSetChanged();
                    binding.inputPrice.getEditText().setText("");
                    binding.inputDate.getEditText().setText("");
                    picked_date = "";
                    binding.inputDate.requestFocus();
                }


            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}
package com.example.bookingappteam9.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam9.adapters.AccommodationListAdapter;
import com.example.bookingappteam9.databinding.FragmentProductsListBinding;
import com.example.bookingappteam9.model.Accommodation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HostAccommodationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostAccommodationsFragment extends ListFragment {

    private AccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Accommodation> allAccommodations;
    private FragmentProductsListBinding binding;

    public static HostAccommodationsFragment newInstance(ArrayList<Accommodation> accommodations){
        HostAccommodationsFragment fragment = new HostAccommodationsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Booking", "onCreateView Products List Fragment");
        binding = FragmentProductsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Booking", "onCreate Products List Fragment");
        if (getArguments() != null) {
            allAccommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new AccommodationListAdapter(getActivity(), allAccommodations);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
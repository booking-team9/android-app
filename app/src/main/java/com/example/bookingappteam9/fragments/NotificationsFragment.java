package com.example.bookingappteam9.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.adapters.NotificationsAdapter;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentNotificationsBinding;
import com.example.bookingappteam9.model.Notification;
import com.example.bookingappteam9.utils.PrefUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private NotificationsAdapter adapter;



    public NotificationsFragment() {
        // Required empty public constructor
    }


    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
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
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new NotificationsAdapter(new ArrayList<>());
        binding.notificationsList.setAdapter(adapter);
        binding.notificationsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSettingsDialog dialogFragment = NotificationSettingsDialog.newInstance();
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("settings");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                dialogFragment.show(ft, "settings");
            }
        });

        Long userId = PrefUtils.getUserInfo(getContext().getApplicationContext()).getId();

        ClientUtils.notificationsService.getByUserId(userId).enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(Call<ArrayList<Notification>> call, Response<ArrayList<Notification>> response) {
                if (response.isSuccessful()){
                    adapter.addAll(response.body());
                    if (binding != null)
                        binding.progressLoaderNotifications.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Notification>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

            }
        });

        return root;
    }
}
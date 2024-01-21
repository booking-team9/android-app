package com.example.bookingappteam9.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.databinding.FragmentNotificationSettingsDialogBinding;
import com.example.bookingappteam9.model.Role;
import com.example.bookingappteam9.model.Token;
import com.example.bookingappteam9.utils.PrefUtils;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationSettingsDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationSettingsDialog extends DialogFragment {
    FragmentNotificationSettingsDialogBinding binding;

    private HashMap<String, SwitchCompat> settings = new HashMap<>();
    private SwitchCompat reservationRequest;
    private SwitchCompat reservationCancel;
    private SwitchCompat reviewHost;
    private SwitchCompat reviewAccommodation;
    private SwitchCompat reservationResponse;




    public NotificationSettingsDialog() {
        // Required empty public constructor
    }


    public static NotificationSettingsDialog newInstance() {
        NotificationSettingsDialog fragment = new NotificationSettingsDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        binding = FragmentNotificationSettingsDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getDialog().setTitle("Settings");
        reviewHost = binding.notificationSetting1;
        reviewAccommodation = binding.notificationSetting2;
        reservationRequest = binding.notificationSetting3;
        reservationCancel = binding.notificationSetting4;
        reservationResponse = binding.notificationSetting5;

        settings.put("RESERVATION_REQUEST_NOTIFICATION", reservationRequest);
        settings.put("RESERVATION_CANCEL_NOTIFICATION", reservationCancel);
        settings.put("HOST_REVIEW_NOTIFICATION", reviewHost);
        settings.put("ACCOMMODATION_REVIEW_NOTIFICATION", reviewAccommodation);
        settings.put("RESERVATION_RESPONSE_NOTIFICATION", reservationResponse);
        PrefUtils.UserInfo info = PrefUtils.getUserInfo(getActivity().getApplicationContext());
        if (info.getRole().equals(Role.Host)){
            binding.notificationSetting1.setVisibility(View.VISIBLE);
            binding.notificationSetting2.setVisibility(View.VISIBLE);
            binding.notificationSetting3.setVisibility(View.VISIBLE);
            binding.notificationSetting4.setVisibility(View.VISIBLE);
            binding.settingsAccommodationReview.setVisibility(View.VISIBLE);
            binding.settingsHostReview.setVisibility(View.VISIBLE);
            binding.settingsReservationRequest.setVisibility(View.VISIBLE);
            binding.settingsReservationCancel.setVisibility(View.VISIBLE);
        }else if (info.getRole().equals(Role.Guest)){
            binding.notificationSetting5.setVisibility(View.VISIBLE);
            binding.settingsReservationResponse.setVisibility(View.VISIBLE);
        }
        binding.notificationSetting1.setChecked(false);
        binding.notificationSetting2.setChecked(false);
        binding.notificationSetting3.setChecked(false);
        binding.notificationSetting4.setChecked(false);
        binding.notificationSetting5.setChecked(false);
        ClientUtils.notificationsService.getNotificationsSettings(info.getId()).enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()){
                    for (String setting: response.body()){
                        settings.get(setting).setChecked(true);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");

            }
        });
        binding.settingsApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String>newSettings = new ArrayList<>();
                for (Map.Entry<String, SwitchCompat> set: settings.entrySet()){
                    if (set.getValue().isChecked()){
                        newSettings.add(set.getKey());
                    }
                }
                ClientUtils.notificationsService.setNotificationsSettings(info.getId(), newSettings).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Settings updated successfully!", Toast.LENGTH_SHORT).show();
                        }
                        getDialog().dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("QM", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });





        return root;
    }
}
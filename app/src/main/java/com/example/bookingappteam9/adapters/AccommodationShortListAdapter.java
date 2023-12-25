package com.example.bookingappteam9.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.AccommodationShort;

import java.util.ArrayList;

public class AccommodationShortListAdapter extends ArrayAdapter<AccommodationShort> {
    private ArrayList<AccommodationShort> accommodations;

    public AccommodationShortListAdapter(Context context, ArrayList<AccommodationShort> accommodationss){
        super(context, R.layout.accommodation_short_card, accommodationss);
        this.accommodations = accommodationss;
    }

    @Override
    public int getCount() {
        return accommodations.size();
    }

    @Nullable
    @Override
    public AccommodationShort getItem(int position) {
        return accommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationShort accommodation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_short_card,
                    parent, false);
        }
        RelativeLayout productCard = convertView.findViewById(R.id.accommodation_short_card_item);
        ImageView imageView = convertView.findViewById(R.id.accommodation_photo);
        TextView status = convertView.findViewById(R.id.accommodation_status);
        TextView name = convertView.findViewById(R.id.accommodation_name);
        TextView address = convertView.findViewById(R.id.accommodation_address);
        RatingBar rating = convertView.findViewById(R.id.accommodation_rating);

        if (accommodation != null) {
//            imageView.setImageResource(accommodation.getImage());
            status.setText(accommodation.getStatus().toString());
            name.setText(accommodation.getName());
            address.setText(accommodation.getLocation());
            rating.setRating((float) accommodation.getAverageGrade());
        }

        return convertView;
    }

}

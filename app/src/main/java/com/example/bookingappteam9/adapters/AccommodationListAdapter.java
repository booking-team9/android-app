package com.example.bookingappteam9.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.Accommodation;

import java.util.List;


public class AccommodationListAdapter extends ArrayAdapter<Accommodation> {
    private List<Accommodation> allAccommodations;

    public AccommodationListAdapter(Context context, List<Accommodation> products){
        super(context, R.layout.accommodation_card, products);
        allAccommodations = products;
    }

    @Override
    public int getCount() {
        return allAccommodations.size();
    }

    @Nullable
    @Override
    public Accommodation getItem(int position) {
        return allAccommodations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Accommodation accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }
        LinearLayout accomodationCard = convertView.findViewById(R.id.accommodation_card_item);
        ImageView imageView = convertView.findViewById(R.id.accommodation_image);
        TextView accomodationTitle = convertView.findViewById(R.id.accommodation_title);
        TextView productDescription = convertView.findViewById(R.id.accommodation_description);

        if(accommodation != null){
            imageView.setImageResource(accommodation.getImage());
            accomodationTitle.setText(accommodation.getTitle());
            productDescription.setText(accommodation.getDescription());
            accomodationCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("Booking", "Clicked: " + accommodation.getTitle() + ", id: " +
                        accommodation.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + accommodation.getTitle()  +
                        ", id: " + accommodation.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}

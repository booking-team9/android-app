package com.example.bookingappteam9.adapters;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.fragments.AccommodationDetailsFragment;
import com.example.bookingappteam9.fragments.AdapterClickListener;
import com.example.bookingappteam9.fragments.FragmentTransition;
import com.example.bookingappteam9.fragments.ReviewDialogFragment;
import com.example.bookingappteam9.model.AccommodationShort;
import com.example.bookingappteam9.model.HostAccommodation;
import com.example.bookingappteam9.utils.Round;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AllAccommodationsAdapter extends RecyclerView.Adapter<AllAccommodationsAdapter.ViewHolder> {

    private List<AccommodationShort> accommodationList;
    private AdapterClickListener listener;

    private View view;
    public AllAccommodationsAdapter(List<AccommodationShort> accommodations, AdapterClickListener listener){
        this.accommodationList = accommodations;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accommodation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(accommodationList.get(position).getName());
        holder.getAddress().setText(accommodationList.get(position).getLocation());
        holder.getRating().setText(Round.round(accommodationList.get(position).getAverageGrade(), 1));
        holder.getPrice().setVisibility(View.INVISIBLE);
        holder.getPriceTag().setVisibility(View.INVISIBLE);
        holder.getDescription().setText(accommodationList.get(position).getDescription());
        Glide.with(view).load(ClientUtils.getPhotoPath(accommodationList.get(position).getImages().get(0))).into(holder.getImage());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(accommodationList.get(holder.getBindingAdapterPosition()).getId());
            }
        });


    }


    @Override
    public int getItemCount() {
        return this.accommodationList.size();
    }
    public void addAll(List<AccommodationShort> accommodationList){
        this.accommodationList.addAll(accommodationList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView layout;
        private final TextView name;
        private final TextView address;
        private final TextView description;
        private final TextView rating;
        private final TextView price;
        private final TextView priceTag;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            layout = view.findViewById(R.id.card_layout_accommodation);
            name = (TextView) view.findViewById(R.id.accommodation_name);
            address = (TextView) view.findViewById(R.id.accommodation_address);
            description = (TextView) view.findViewById(R.id.accommodation_description);
            rating = (TextView) view.findViewById(R.id.accommodation_rating);
            price = (TextView) view.findViewById(R.id.accommodation_price);
            priceTag = (TextView) view.findViewById(R.id.priceTag);
            image = (ImageView) view.findViewById(R.id.accommodation_image);
        }

        public TextView getName() {
            return name;
        }

        public TextView getAddress() {
            return address;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getRating() {
            return rating;
        }

        public TextView getPrice() {
            return price;
        }
        public TextView getPriceTag(){return priceTag;}

        public ImageView getImage() {
            return image;
        }

        public MaterialCardView getLayout() {
            return layout;
        }
    }
}

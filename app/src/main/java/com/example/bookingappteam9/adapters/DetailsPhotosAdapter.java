package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingappteam9.R;
import com.example.bookingappteam9.clients.ClientUtils;
import com.example.bookingappteam9.model.Photo;

import java.util.List;

public class DetailsPhotosAdapter extends RecyclerView.Adapter<DetailsPhotosAdapter.ViewHolder> {
    private List<String> photos;
    private View view;
    public DetailsPhotosAdapter(List<String> photos){
        this.photos = photos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carrousel_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(view).load(ClientUtils.getPhotoPath(photos.get(position))).into(holder.getPhoto());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addAll(List<String> photos){
        this.photos.addAll(photos);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView photo;
        public ViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.carousel_image_view);
        }

        public ImageView getPhoto() {
            return photo;
        }
    }
}

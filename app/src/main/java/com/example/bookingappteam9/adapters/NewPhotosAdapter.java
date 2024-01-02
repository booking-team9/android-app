package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;
import com.example.bookingappteam9.model.Photo;
import com.google.android.material.chip.Chip;

import java.util.List;

public class NewPhotosAdapter extends RecyclerView.Adapter<NewPhotosAdapter.ViewHolder> {
    private List<Photo> photos;

    public NewPhotosAdapter(List<Photo> photos){
        this.photos = photos;
    }
    public void addPhoto(Photo photo){
        this.photos.add(photo);
        notifyItemInserted(this.photos.size()-1);
    }

    public List<Photo> getPhotos(){
        return this.photos;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(photos.get(position).getName());
        holder.getRemove().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.remove(holder.getBindingAdapterPosition());
                notifyItemRemoved(holder.getBindingAdapterPosition());
            }
        });
        holder.getPhoto().setImageBitmap(photos.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView photo;
        private final Button remove;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = (TextView) view.findViewById(R.id.new_photo_name);
            photo = (ImageView) view.findViewById(R.id.new_photo_item);
            remove = (Button) view.findViewById(R.id.new_photo_remove);
         }

        public TextView getName() {
            return name;
        }

        public ImageView getPhoto() {
            return photo;
        }

        public Button getRemove() {
            return remove;
        }
    }

}

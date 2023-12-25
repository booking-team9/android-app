package com.example.bookingappteam9.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam9.R;

import java.util.List;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private List<Pair<String ,String>> availabilities;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView price;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            date = (TextView) view.findViewById(R.id.date_range);
            price = (TextView) view.findViewById(R.id.price);
        }

        public TextView getDate() {
            return date;
        }
        public TextView getPrice(){
            return price;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param av String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public AvailabilityAdapter(List<Pair<String, String>> av) {
        availabilities = av;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getDate().setText(availabilities.get(position).first);
        viewHolder.getPrice().setText(availabilities.get(position).second);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return availabilities.size();
    }

    public void addSlot(Pair<String, String> slot){
        this.availabilities.add(slot);
    }
}

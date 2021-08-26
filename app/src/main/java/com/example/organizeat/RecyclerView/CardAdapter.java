package com.example.organizeat.RecyclerView;

import android.app.Activity;

import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizeat.CardItem;
import com.example.organizeat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter linked to the RecyclerView of the homePage, that extends a custom Adapter
 */
public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> implements Filterable {

    //list that can be filtered
    private List<CardItem> cardItemListFiltered;
    //list that contains ALL the element added by the user
    private List<CardItem> cardItemList;
    //I will use it to get the drawable
    private Activity activity;
    //listener attached to the onclick event for the item in tùhe RecyclerView
    private OnItemListener listener;

    public CardAdapter(Activity activity, OnItemListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent   ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardViewHolder(layoutView, listener);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect
     * the item at the given position.
     *
     * @param holder   ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem currentCardItem = cardItemList.get(position);

        String image_path = currentCardItem.getImageResource();
        if (image_path.contains("ic_")) {
            Drawable drawable = ContextCompat.getDrawable(activity, activity.getResources()
                    .getIdentifier(image_path, "drawable",
                            activity.getPackageName()));
            holder.imageCardView.setImageDrawable(drawable);
        }

        holder.recipeTextView.setText(currentCardItem.getRecipe());
        holder.categoryTextView.setText(currentCardItem.getCategory());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    @Override
    public Filter getFilter() {
        return cardFilter;
    }

    private final Filter cardFilter = new Filter() {
        /**
         * Called to filter the data according to the constraint
         * @param constraint constraint used to filtered the data
         * @return the result of the filtering
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CardItem> filteredList = new ArrayList<>();

            //if you have no constraint --> return the full list
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(cardItemListFiltered);
            } else {
                //else apply the filter and return a filtered list
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CardItem item : cardItemListFiltered) {
                    if (item.getCategory().toLowerCase().contains(filterPattern) ||
                            item.getRecipe().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        /**
         * Called to publish the filtering results in the user interface
         * @param constraint constraint used to filter the data
         * @param results the result of the filtering
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cardItemList.clear();
            List<?> result = (List<?>) results.values;
            for (Object object : result) {
                if (object instanceof CardItem) {
                    cardItemList.add((CardItem) object);
                }
            }
            //warn the adapter that the dare are changed after the filtering
            notifyDataSetChanged();
        }
    };

    public void setData(List<CardItem> list){
        this.cardItemList = new ArrayList<>(list);
        this.cardItemListFiltered = new ArrayList<>(list);
        notifyDataSetChanged();
    }
}

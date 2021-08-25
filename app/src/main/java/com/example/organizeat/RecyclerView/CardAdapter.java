package com.example.organizeat.RecyclerView;

import android.app.Activity;

import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class CardAdapter extends RecyclerView.Adapter<CardViewHolder>{

    //list that contains ALL the element added by the user
    private List <CardItem> cardItemList = new ArrayList<>();

    //I will use it to get the drawable
    private Activity activity;

    public CardAdapter(Activity activity, List<CardItem> cardItemList) {
        this.activity = activity;
        this.cardItemList = cardItemList;
    }

    /**
     *
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new CardViewHolder(layoutView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.ViewHolder.itemView to reflect
     * the item at the given position.
     *
     * @param holder ViewHolder which should be updated to represent the contents of the item at
     *               the given position in the data set.
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
}

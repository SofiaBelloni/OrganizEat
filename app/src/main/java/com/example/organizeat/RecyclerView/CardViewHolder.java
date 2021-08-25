package com.example.organizeat.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizeat.R;

/**
 * A ViewHolder describes an item view and the metadata about its place within the RecyclerView.
 */
public class CardViewHolder extends RecyclerView.ViewHolder{

    ImageView imageCardView;
    TextView recipeTextView;
    TextView categoryTextView;

    private OnItemListener itemListener;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        imageCardView = itemView.findViewById(R.id.recipeImage);
        recipeTextView = itemView.findViewById(R.id.recipeTextView);
        categoryTextView = itemView.findViewById(R.id.categoryTextView);
    }

}

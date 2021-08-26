package com.example.organizeat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    private TextView recipe;
    private TextView description;
    private TextView category;
    private TextView ingredients;
    private TextView yield;
    private TextView cooking_time;
    private TextView directions;

    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().invalidateOptionsMenu();
        super.onViewCreated(view, savedInstanceState);
        this.recipe = view.findViewById(R.id.title);
        this.category = view.findViewById(R.id.category);
        this.description = view.findViewById(R.id.description);
        this.cooking_time = view.findViewById(R.id.time);
        this.yield = view.findViewById(R.id.yield);
        this.ingredients = view.findViewById(R.id.ingredients);
        this.directions = view.findViewById(R.id.directions);
        if(getActivity() != null){
            Utilities.setUpToolBar((AppCompatActivity)getActivity());
        };
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.recipe_app_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.app_bar_share){
            View view = getView();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.getText().toString()  + "\n" +
                    description.getText().toString()  + "\n" +
                    view.getContext().getString(R.string.category) + ": " + category.getText().toString()  + "\n" +
                    view.getContext().getString(R.string.time_for_cooking) + ": "  + cooking_time.getText().toString()  + "\n" +
                    view.getContext().getString(R.string.yield) + ": "  + yield.getText().toString() + "\n" +
                    view.getContext().getString(R.string.recipe_ingredient) + ": "  + ingredients.getText().toString() + "\n" +
                    view.getContext().getString(R.string.directions) + ": "  + directions.getText().toString() + "\n");
            if((view.getContext() != null) && (sendIntent.resolveActivity(getContext().getPackageManager()) != null)){
                view.getContext().startActivity(Intent.createChooser(sendIntent, null));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

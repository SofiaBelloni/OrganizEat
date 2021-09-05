package com.example.organizeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.ListViewModel;
import com.example.organizeat.ViewModel.ShoppingListViewModel;

public class DetailsFragment extends Fragment {

    private TextView recipe;
    private TextView description;
    private TextView category;
    private TextView ingredients;
    private TextView yield;
    private TextView cooking_time;
    private TextView directions;

    private ImageView recipeImageView;

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
        super.onViewCreated(view, savedInstanceState);
        this.recipe = view.findViewById(R.id.title);
        this.category = view.findViewById(R.id.category);
        this.description = view.findViewById(R.id.description);
        this.cooking_time = view.findViewById(R.id.time);
        this.yield = view.findViewById(R.id.yield);
        this.ingredients = view.findViewById(R.id.ingredients);
        this.directions = view.findViewById(R.id.directions);
        this.recipeImageView = view.findViewById(R.id.recipe_image);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolBar((AppCompatActivity)getActivity(), "");

            ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getSelected().observe(getViewLifecycleOwner(), cardItem -> {
                recipe.setText(cardItem.getRecipe());
                description.setText(cardItem.getDescription());
                category.setText(listViewModel.categoryName());
                yield.setText(cardItem.getYield());
                ingredients.setText(cardItem.getIngredients());
                directions.setText(cardItem.getDirections());
                cooking_time.setText(cardItem.getCooking_time());

                String image_path = cardItem.getImageResource();
                if (image_path.contains("ic_")){
                    Drawable drawable = ContextCompat.getDrawable(activity,
                            activity.getResources().getIdentifier(image_path,"drawable",
                                    activity.getPackageName()));
                    recipeImageView.setImageDrawable(drawable);
                } else {
                    Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(image_path));
                    if (bitmap != null) {
                        recipeImageView.setImageBitmap(bitmap);
                        recipeImageView.setBackgroundColor(Color.WHITE);
                    }
                }
            });
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.recipe_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_share:
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

            case R.id.app_bar_back:
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
                break;

            case R.id.app_bar_delete:
                ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(ListViewModel.class);
                listViewModel.deleteSelected();
                Toast.makeText(getActivity(),getView().getContext().getString(R.string.recipe_delete), Toast.LENGTH_SHORT).show();
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
                break;

            case R.id.app_bar_edit:
                Utilities.insertFragment((AppCompatActivity)getActivity(), new EditFragment(), "EditFragment");
                break;
            case R.id.app_bar_add_chart:
                String string = this.ingredients.getText().toString();
                String[] parts = {};
                if (string.contains("\n")) {
                    parts = string.split("\n");
                }
                ShoppingListViewModel shoppingListViewModel = new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(ShoppingListViewModel.class);
                if (parts.length>0){
                    for(String part: parts)
                        shoppingListViewModel.addListItem(new ListItem(part));
                } else if(string.length()>0) {
                    shoppingListViewModel.addListItem(new ListItem(string));
                }
                Toast.makeText(getActivity(),getView().getContext().getString(R.string.added_to_list), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
}

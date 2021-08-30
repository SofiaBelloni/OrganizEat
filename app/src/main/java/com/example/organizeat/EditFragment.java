package com.example.organizeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.AddViewModel;
import com.example.organizeat.ViewModel.ListViewModel;

import java.io.IOException;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;

public class EditFragment extends Fragment {

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
        return inflater.inflate(R.layout.add_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.recipe = view.findViewById(R.id.recipeTextInputEditText);
        this.category = view.findViewById(R.id.categoryAutoCompleteTextView);
        this.description = view.findViewById(R.id.descriptionTextInputEditText);
        this.cooking_time = view.findViewById(R.id.timeTextInputEditText);
        this.yield = view.findViewById(R.id.yieldTextInputEditText);
        this.ingredients = view.findViewById(R.id.ingredientTextInputEditText);
        this.directions = view.findViewById(R.id.directionsTextInputEditText);
        this.recipeImageView = view.findViewById(R.id.imageView);
        Activity activity = getActivity();

        view.findViewById(R.id.captureButton).setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        });
        ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
        if(activity != null){
            Utilities.setUpToolBar((AppCompatActivity)getActivity());
            listViewModel.getSelected().observe(getViewLifecycleOwner(), cardItem -> {
                recipe.setText(cardItem.getRecipe());
                description.setText(cardItem.getDescription());
                category.setText(cardItem.getCategory());
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
        listViewModel.getBitMap().observe(getViewLifecycleOwner(), bitmap -> this.recipeImageView.setImageBitmap(bitmap));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_recipe_top_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_back:
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
                break;
            case R.id.app_bar_done:
                View view = getView();
                ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(ListViewModel.class);
                try {
                    Bitmap bitmap = listViewModel.getBitMap().getValue();
                    String imageUriString;
                    if( bitmap != null){
                        //Method to save the image in device's gallery
                        imageUriString = String.valueOf(Utilities.saveImage(bitmap, getActivity()));
                        //Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT);
                    }else {
                        imageUriString = "ic_launcher_foreground";
                    }

                    listViewModel.updateSelected(imageUriString, this.recipe.getText().toString(), this.description.getText().toString(),
                            "", this.ingredients.getText().toString(), this.cooking_time.getText().toString(),
                            this.directions.getText().toString(), this.yield.getText().toString());

                    listViewModel.setBitMap(null);
                    //back to the previous fragment (Home)
                    ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

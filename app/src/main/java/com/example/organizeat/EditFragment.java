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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.AddViewModel;
import com.example.organizeat.ViewModel.CategoryViewModel;
import com.example.organizeat.ViewModel.ListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;
import static com.example.organizeat.Utilities.REQUEST_IMAGE_GALLERY;

public class EditFragment extends Fragment {

    private TextView recipe;
    private TextView description;
    private Spinner category_spinner;
    private TextView ingredients;
    private TextView yield;
    private TextView cooking_time;
    private TextView directions;
    private ImageView recipeImageView;
    private int category_id;

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
        this.category_spinner = view.findViewById(R.id.category_spinner);
        this.description = view.findViewById(R.id.descriptionTextInputEditText);
        this.cooking_time = view.findViewById(R.id.timeTextInputEditText);
        this.yield = view.findViewById(R.id.yieldTextInputEditText);
        this.ingredients = view.findViewById(R.id.ingredientTextInputEditText);
        this.directions = view.findViewById(R.id.directionsTextInputEditText);
        this.recipeImageView = view.findViewById(R.id.imageView);
        Activity activity = getActivity();

        view.findViewById(R.id.captureButton).setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            popupMenu.getMenuInflater().inflate(R.menu.pop_up_gallery_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.take_picture:
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
                            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        break;
                    case R.id.select_picture:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if(pickPhoto.resolveActivity(activity.getPackageManager()) != null)
                            activity.startActivityForResult(pickPhoto , REQUEST_IMAGE_GALLERY);
                        break;
                    default:
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

        CategoryViewModel categoryViewModel =  new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(CategoryViewModel.class);
        List<String> list = new ArrayList<>(categoryViewModel.getCategoriesName());
        List<Category> categories = categoryViewModel.getCategories();
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, list);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(adapter);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_id = categories.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
        ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
        if(activity != null){
            Utilities.setUpToolBar((AppCompatActivity)getActivity(), view.getContext().getString(R.string.edit));
            listViewModel.getSelected().observe(getViewLifecycleOwner(), cardItem -> {
                recipe.setText(cardItem.getRecipe());
                description.setText(cardItem.getDescription());
                category_id = cardItem.getCategory();
                for( Category cat: categories){
                    if(category_id == cat.getId()){
                        category_spinner.setSelection(categories.indexOf(cat));
                    }
                }
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
                        addViewModel.setBitMap(bitmap);
                        recipeImageView.setBackgroundColor(Color.WHITE);
                    }
                }
            });
        }
        addViewModel.getBitMap().observe(getViewLifecycleOwner(), bitmap -> this.recipeImageView.setImageBitmap(bitmap));
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
                AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(AddViewModel.class);
                ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(ListViewModel.class);
                try {
                    Bitmap bitmap = addViewModel.getBitMap().getValue();
                    String imageUriString;
                    if( bitmap != null){
                        //Method to save the image in device's gallery
                        imageUriString = String.valueOf(Utilities.saveImage(bitmap, getActivity()));
                        //Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT);
                    }else {
                        imageUriString = "ic_launcher_foreground";
                    }
                    //TODO
                    listViewModel.updateSelected(imageUriString, this.recipe.getText().toString(), this.description.getText().toString(),
                            category_id, this.ingredients.getText().toString(), this.cooking_time.getText().toString(),
                            this.directions.getText().toString(), this.yield.getText().toString());

                    addViewModel.setBitMap(null);
                    Toast.makeText(getActivity(),getView().getContext().getString(R.string.edited_recipe), Toast.LENGTH_SHORT).show();
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

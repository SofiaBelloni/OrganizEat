package com.example.organizeat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.AddViewModel;
import com.example.organizeat.ViewModel.CategoryViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;
import static com.example.organizeat.Utilities.REQUEST_IMAGE_GALLERY;

public class AddFragment extends Fragment {

    private TextView recipe;
    private TextView description;
    private Spinner category_spinner;
    private TextView ingredients;
    private TextView yield;
    private TextView cooking_time;
    private TextView directions;
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
        final Activity activity = getActivity();
        if(activity!=null){
            Utilities.setUpToolBar((AppCompatActivity)activity, view.getContext().getString(R.string.new_recipe));
        }

        Button captureImage =  view.findViewById(R.id.captureButton);
        captureImage.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), captureImage);
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

        ImageView imageView = view.findViewById(R.id.imageView);
        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
        addViewModel.getBitMap().observe(getViewLifecycleOwner(), bitmap -> imageView.setImageBitmap(bitmap));

        this.recipe = view.findViewById(R.id.recipeTextInputEditText);
        this.description = view.findViewById(R.id.descriptionTextInputEditText);
        this.category_spinner = view.findViewById(R.id.category_spinner);
        this.cooking_time = view.findViewById(R.id.timeTextInputEditText);
        this.yield = view.findViewById(R.id.yieldTextInputEditText);
        this.ingredients = view.findViewById(R.id.ingredientTextInputEditText);
        this.directions = view.findViewById(R.id.directionsTextInputEditText);

        CategoryViewModel categoryViewModel =  new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(CategoryViewModel.class);
        List<String> list = new ArrayList<>(categoryViewModel.getCategoriesName());
        if(list.isEmpty()){
            categoryViewModel.addCategory(new Category(view.getResources().getString(R.string.default_category), addViewModel.getUser().getEmail()));
            list = new ArrayList<>(categoryViewModel.getCategoriesName());
        }
        List<Category> listCategory = new ArrayList<>(categoryViewModel.getCategories());
        category_id = listCategory.get(0).getId();
        //create an ArrayAdaptar from the String Array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, list);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(adapter);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_id = listCategory.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_recipe_top_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_close).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.app_bar_done){
            View view = getView();
            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity()).get(AddViewModel.class);
            try {
                Bitmap bitmap = addViewModel.getBitMap().getValue();
                String imageUriString;
                if( bitmap != null){
                    //Method to save the image in device's gallery
                    imageUriString = String.valueOf(Utilities.saveImage(bitmap, getActivity()));
                }else {
                    imageUriString = "ic_launcher_foreground";
                }

                addViewModel.addCardItem(new CardItem(imageUriString, this.recipe.getText().toString(), this.description.getText().toString(),
                        category_id, this.ingredients.getText().toString(), this.cooking_time.getText().toString(),
                        this.directions.getText().toString(), this.yield.getText().toString(), addViewModel.getUser().getEmail()));
                addViewModel.setBitMap(null);
                Toast.makeText(getActivity(), getView().getContext().getString(R.string.added_recipe), Toast.LENGTH_SHORT).show();
                //back to the previous fragment (Home)
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(item.getItemId()== R.id.app_bar_back){
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }


}

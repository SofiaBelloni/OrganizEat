package com.example.organizeat;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.DataBase.CardItemRepository;
import com.example.organizeat.ViewModel.AddViewModel;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;

public class AddFragment extends Fragment {

    private TextView recipe;
    private TextView description;
    private TextView category;
    private TextView ingredients;
    private TextView yield;
    private TextView cooking_time;
    private TextView directions;

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
        List<String> items = Arrays.asList("Antipasto", "Primo", "Secondo", "Dolce");
        ArrayAdapter adapter = new ArrayAdapter(requireContext(), R.layout.list_item, items);
        AutoCompleteTextView textView = (AutoCompleteTextView)view.findViewById(R.id.categoryAutoCompleteTextView);
        textView.setAdapter(adapter);
        final Activity activity = getActivity();
        if(activity!=null){
            Utilities.setUpToolBar((AppCompatActivity)activity);
        }

        view.findViewById(R.id.captureButton).setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null)
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        });

        ImageView imageView = view.findViewById(R.id.imageView);
        AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
        addViewModel.getBitMap().observe(getViewLifecycleOwner(), bitmap -> imageView.setImageBitmap(bitmap));

        this.recipe = view.findViewById(R.id.recipeTextInputEditText);
        this.description = view.findViewById(R.id.descriptionTextInputEditText);
        //this.category = view.findViewById(R.id.category);
        this.cooking_time = view.findViewById(R.id.timeTextInputEditText);
        this.yield = view.findViewById(R.id.yieldTextInputEditText);
        this.ingredients = view.findViewById(R.id.ingredientTextInputEditText);
        this.directions = view.findViewById(R.id.directionsTextInputEditText);

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
                        "", this.ingredients.getText().toString(), this.cooking_time.getText().toString(),
                        this.directions.getText().toString(), this.yield.getText().toString()));
                addViewModel.setBitMap(null);
                //back to the previous fragment (Home)
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(item.getItemId()==R.id.app_bar_back){
            ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }


}

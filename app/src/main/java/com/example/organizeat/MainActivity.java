package com.example.organizeat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;

import com.example.organizeat.RecyclerView.CardAdapter;
import com.example.organizeat.ViewModel.AddViewModel;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "HomeFragment";

    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private AddViewModel addViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), FRAGMENT_TAG);

        this.addViewModel = new ViewModelProvider(this).get(AddViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            if (extras != null){
                Bitmap imageBitMap = (Bitmap)extras.get("data");
                addViewModel.setBitMap(imageBitMap);
            }
        }
    }
}
package com.example.organizeat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;

import com.example.organizeat.RecyclerView.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private androidx.appcompat.widget.Toolbar toolbar;

    private static final String FRAGMENT_TAG = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), FRAGMENT_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

}
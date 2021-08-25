package com.example.organizeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;

import android.os.Bundle;

import com.example.organizeat.RecyclerView.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardAdapter adapter;
    private RecyclerView recyclerView;

    private static final String FRAGMENT_TAG = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null)
            Utilities.insertFragment(this, new HomeFragment(), FRAGMENT_TAG);
    }


}
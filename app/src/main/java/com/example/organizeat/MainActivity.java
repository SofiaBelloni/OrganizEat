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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        setRecyclerView(this);
    }

    private void setRecyclerView(final Activity activity){
        //Set up the recycler view
        this.recyclerView = activity.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        List<CardItem> list = new ArrayList<>();
        list.add(new CardItem("ic_baseline_android_24", "Recipe", "Desc", "Category"));
        this.adapter = new CardAdapter(this, list);
        this.recyclerView.setAdapter(this.adapter);
   }
}
package com.example.organizeat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizeat.RecyclerView.CardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String LOG = "Home-Fragment_LAB";

    private  CardAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();
        if (activity!=null){
            setRecyclerView(activity);

            FloatingActionButton floatingActionButton = view.findViewById((R.id.fab_add));

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(), "AddFragment");
                }
            });
        } else {
            Log.e(LOG, "Activity is null");
        }
    }

    private void setRecyclerView(final Activity activity){
        //Set up the recycler view
        this.recyclerView = activity.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        List<CardItem> list = new ArrayList<>();
        list.add(new CardItem("ic_baseline_android_24", "Recipe", "Desc",
                "Category", "Ing", "5 ore", "vhvhv", "4 persone"));
        list.add(new CardItem("ic_baseline_android_24", "Recipe", "Desc",
                "Category", "Ing", "5 ore", "vhvhv", "4 persone"));
        this.adapter = new CardAdapter(activity, list);
        this.recyclerView.setAdapter(this.adapter);
    }
}

package com.example.organizeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Utilities {

    static void insertFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        //Replace wheter is in the fragment container view with the fragment
        transaction.replace(R.id.fragment_container_view, fragment, tag);
        //Add transaction to the back stack so user can navigate back
        if(!(fragment instanceof HomeFragment)){
            transaction.addToBackStack(tag);
        }
        //commit transaction
        transaction.commit();
    }

}

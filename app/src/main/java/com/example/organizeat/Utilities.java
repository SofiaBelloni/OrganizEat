package com.example.organizeat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Utilities {

    static final int REQUEST_IMAGE_CAPTURE = 1;

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

    static void setUpToolBar(AppCompatActivity activity){
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        if(activity.getSupportActionBar() == null){
            //Set a Toolbar to act as the ActionBar for the activity
            activity.setSupportActionBar(toolbar);
        }
    }

    public static Bitmap drawableToBitMap (Drawable drawable){
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable)drawable).getBitmap();

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}

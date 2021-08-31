package com.example.organizeat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.organizeat.ViewModel.AddViewModel;
import com.google.android.material.navigation.NavigationView;

import static com.example.organizeat.Utilities.REQUEST_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "HomeFragment";

    private AddViewModel addViewModel;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.navigation_view);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_home:
                Utilities.insertFragment( this, new HomeFragment(), "HomeFragment");
                break;
            case R.id.nav_shopping_list:
                Utilities.insertFragment(this, new AddFragment(), "AddFragment");
                break;
            case R.id.nav_add_category:
                Utilities.insertFragment(this, new AddFragment(), "AddFragment");
                break;
            case R.id.nav_settings:
                Utilities.insertFragment(this, new SettingsFragment(), "SettingsFragment");
                break;
            default:
                Utilities.insertFragment(this, new HomeFragment(), "HomeFragment");
        }
        menuItem.setChecked(true);
        mDrawer.closeDrawers();
    }
}
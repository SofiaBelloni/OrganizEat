package com.example.organizeat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizeat.RecyclerView.CardAdapter;
import com.example.organizeat.RecyclerView.OnItemListener;
import com.example.organizeat.ViewModel.AddViewModel;
import com.example.organizeat.ViewModel.CategoryViewModel;
import com.example.organizeat.ViewModel.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  implements OnItemListener {

    private static final String LOG = "Home-Fragment_LAB";

    private  CardAdapter adapter;
    private RecyclerView recyclerView;
    private ListViewModel listViewModel;
    private Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
            Utilities.setUpToolBar((AppCompatActivity)getActivity(), view.getContext().getString(R.string.app_name));
            setRecyclerView(activity);
            FloatingActionButton floatingActionButton = view.findViewById((R.id.fab_add));
            floatingActionButton.setOnClickListener(v -> Utilities.insertFragment((AppCompatActivity) activity, new AddFragment(), "AddFragment"));
            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
            this.listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            //when the list of items changed, the adapter gets the new list.
            this.listViewModel.getCardItems(addViewModel.getUser().getEmail()).observe((LifecycleOwner) activity, cardItems -> adapter.setData(cardItems));
        } else {
            Log.e(LOG, "Activity is null");
        }
    }


    @Override
    public void onItemClick(int position) {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        if (appCompatActivity != null){
            this.listViewModel.select(this.listViewModel.getCardItem(position));
            Utilities.insertFragment(appCompatActivity, new DetailsFragment(), DetailsFragment.class.getSimpleName());
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        MenuItem item = menu.findItem(R.id.app_bar_search);
        menu.findItem(R.id.app_bar_close).setVisible(false);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             /**
             * Called when the user submits the query. This could be due to a key press on the keyboard
             * or due to pressing a submit button.
             * @param query the query text that is to be submitted
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /**
             * Called when the query text is changed by the user.
             * @param newText the new content of the query text field.
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setRecyclerView(final Activity activity){
        //Set up the recycler view
        this.recyclerView = activity.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        final OnItemListener listener = this;
        this.adapter = new CardAdapter(activity, listener);
        this.recyclerView.setAdapter(this.adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.app_bar_filter){
            create_filter_dialog();
            return true;
        }
        if(item.getItemId() == R.id.app_bar_close){
            adapter.getFilter().filter("");
            this.menu.findItem(R.id.app_bar_close).setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void create_filter_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getView().getContext().getString(R.string.filter_title));
        CategoryViewModel categoryViewModel = new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(CategoryViewModel.class);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.filter_dialog, null, false);
        Spinner spinner = v.findViewById(R.id.filter_spinner);;
        builder.setView(v);
        List<Category> categories = new ArrayList<>(categoryViewModel.getCategories());
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        categoryViewModel.setFilterCategory(categories.get(categories.size()-1));
        builder.setPositiveButton(getView().getContext().getString(R.string.filter), (dialog, which) -> {
            adapter.getFilter().filter(String.valueOf(((Category) spinner.getSelectedItem()).getId()));
            this.menu.findItem(R.id.app_bar_close).setVisible(true);
        });

        builder.setNegativeButton(getView().getContext().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

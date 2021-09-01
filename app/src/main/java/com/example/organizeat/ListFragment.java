package com.example.organizeat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.ListViewModel;
import com.example.organizeat.ViewModel.ShoppingListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    static ListView listView;
    static ListViewAdapter adapter;
    static List<ListItem> items = new ArrayList<>();
    static Context context;
    private ShoppingListViewModel listViewModel;
    static Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list);
        EditText input = view.findViewById(R.id.input);

        if(getActivity()!=null){
            activity = getActivity();
            Utilities.setUpToolBar((AppCompatActivity)getActivity(), view.getContext().getString(R.string.list));
            context = getActivity().getApplicationContext();
            this.listViewModel = new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(ShoppingListViewModel.class);
        }

        items = new ArrayList<>(listViewModel.getListItems());
        adapter = new ListViewAdapter(context, items);
        listView.setAdapter(adapter);

        // add item when the user presses the enter button
        view.findViewById(R.id.add).setOnClickListener(v -> {
            String text = input.getText().toString();
            if (text.length() != 0) {
                addItem(text);
                input.setText("");
                Toast.makeText(getContext(),"Added " + text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
    }

    // function to add an item given its name.
    private void addItem(String item) {
        ListItem listItem = new ListItem(item);
        listViewModel = new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(ShoppingListViewModel.class);
        listViewModel.addListItem(listItem);
        items.add(listItem);
        listView.setAdapter(adapter);
    }

    // function to remove an item given its index in the grocery list.
    public static void removeItem(int i) {
        Toast.makeText(context,"Removed " + items.get(i), Toast.LENGTH_SHORT).show();
        ShoppingListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner)activity).get(ShoppingListViewModel.class);
        listViewModel.deleteListItem(items.get(i));
        items.remove(i);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_share:
                View view = getView();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                String text = "";
                for (ListItem element : items) {
                    text = text.concat("\n"+ element.getItem());
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                if((view.getContext() != null) && (sendIntent.resolveActivity(getContext().getPackageManager()) != null)){
                    view.getContext().startActivity(Intent.createChooser(sendIntent, null));
                }
                return true;

            case R.id.app_bar_back:
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

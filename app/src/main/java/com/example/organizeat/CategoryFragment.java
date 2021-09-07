package com.example.organizeat;

import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.organizeat.ViewModel.AddViewModel;
import com.example.organizeat.ViewModel.CategoryViewModel;
import com.example.organizeat.ViewModel.ShoppingListViewModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    ArrayList<String> list = new ArrayList<>();
    ListView list_view;
    ArrayAdapter arrayAdapter;
    private CategoryViewModel categoryViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menage_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.list_view = view.findViewById(R.id.list_view);

        if(getActivity()!=null){
            Utilities.setUpToolBar((AppCompatActivity)getActivity(), view.getContext().getString(R.string.menage_category));
            this.categoryViewModel =  new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(CategoryViewModel.class);
            this.list = new ArrayList<>(this.categoryViewModel.getCategoriesName());
        }
        this.arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);
        this.list_view.setAdapter(this.arrayAdapter);
        this.list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.item_update:
                                //function for update
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                View v = LayoutInflater.from(getContext()).inflate(R.layout.category_dialog, null, false);
                                builder.setTitle(getView().getContext().getString(R.string.update_category));
                                final EditText editText = v.findViewById(R.id.etItem);
                                editText.setText(list.get(position));
                                //set custome view to dialog
                                builder.setView(v);

                                builder.setPositiveButton(view.getContext().getString(R.string.edit), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!editText.getText().toString().isEmpty()) {
                                            Category cat = categoryViewModel.getCategories().get(position);
                                            cat.setCategory(editText.getText().toString());
                                            list.set(position, editText.getText().toString());
                                            categoryViewModel.updateCategory(cat);
                                            arrayAdapter.notifyDataSetChanged();
                                        } else {
                                            editText.setError("add item here !");
                                        }
                                    }
                                });

                                builder.setNegativeButton(view.getContext().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
                                builder.show();
                                break;
                            case R.id.item_del:
                                //fucntion for del
                                if(categoryViewModel.isUsed(categoryViewModel.getCategories().get(position))){
                                    Toast.makeText(getActivity(), view.getContext().getString(R.string.category_used), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), view.getContext().getString(R.string.category_deleted), Toast.LENGTH_SHORT).show();
                                    categoryViewModel.deleteCategory(categoryViewModel.getCategories().get(position));
                                    list.remove(position);
                                    arrayAdapter.notifyDataSetChanged();
                                }

                            default:
                                break;
                        }
                        return true;
                    }
                });
                //don't forgot this
                popupMenu.show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_app_bar, menu);
        menu.findItem(R.id.app_bar_search).setVisible(false);
        menu.findItem(R.id.app_bar_close).setVisible(false);
        menu.findItem(R.id.app_bar_filter).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.app_bar_add:
                addItem();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * method for adding item
     * */
    private void addItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getView().getContext().getString(R.string.add_category));
        AddViewModel addViewModel =  new ViewModelProvider((ViewModelStoreOwner)getActivity()).get(AddViewModel.class);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.category_dialog, null, false);

        builder.setView(v);
        final EditText etItem = v.findViewById(R.id.etItem);
        builder.setPositiveButton(getView().getContext().getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!etItem.getText().toString().isEmpty()) {
                    Category newCat = new Category(etItem.getText().toString(), addViewModel.getUser().getEmail());
                    categoryViewModel.addCategory(newCat);
                    list.add(0, newCat.getCategory());
                    arrayAdapter.notifyDataSetChanged();

                } else {
                    etItem.setError("add item here !");
                }
            }
        });

        builder.setNegativeButton(getView().getContext().getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}

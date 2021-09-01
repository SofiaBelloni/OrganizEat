package com.example.organizeat.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.organizeat.DataBase.ListItemRepository;
import com.example.organizeat.ListItem;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private ListItemRepository repository;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        //read data from db
        this.repository = new ListItemRepository(application);
    }

    public void addListItem(ListItem item){ this.repository.addListItem(item);}

    public List<ListItem> getListItems(){ return this.repository.getListItemList();}

    public void deleteListItem(final ListItem listItem) {this.repository.deleteListItem(listItem);}
}

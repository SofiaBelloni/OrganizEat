package com.example.organizeat.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.organizeat.DataBase.ListItemRepository;
import com.example.organizeat.ListItem;

import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private Application application;
    private LiveData<List<ListItem>> items;
    private ListItemRepository repository;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        //read data from db
        this.repository = new ListItemRepository(application);
        this.items = this.repository.getListItemList();
    }

    public LiveData<List<ListItem>> getListItems(){ return this.items; }
}

package com.example.organizeat.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.organizeat.ListItem;

import java.util.List;

public class ListItemRepository {
    private ListItemDAO listItemDAO;
    private LiveData<List<ListItem>> listItemList;

    public ListItemRepository(Application application) {
        OrganizEatDatabase db = OrganizEatDatabase.getDatabase(application);
        listItemDAO = db.listItemDAO();
        listItemList = listItemDAO.getListItems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<ListItem>> getListItemList(){
        return listItemList;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addCardItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDAO.addListItem(listItem);
            }
        });
    }


    public void deleteCardItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDAO.deleteCardItem(listItem);
            }
        });
    }
}

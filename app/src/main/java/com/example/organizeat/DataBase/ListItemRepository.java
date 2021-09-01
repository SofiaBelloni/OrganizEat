package com.example.organizeat.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.organizeat.ListItem;

import java.util.List;

public class ListItemRepository {
    private ListItemDAO listItemDAO;

    public ListItemRepository(Application application) {
        OrganizEatDatabase db = OrganizEatDatabase.getDatabase(application);
        listItemDAO = db.listItemDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<ListItem> getListItemList(){
        return listItemDAO.getListItems();
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addListItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDAO.addListItem(listItem);
            }
        });
    }


    public void deleteListItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDAO.deleteListItem(listItem);
            }
        });
    }
}

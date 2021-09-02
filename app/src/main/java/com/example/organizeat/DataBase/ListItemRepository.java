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

    public List<ListItem> getListItemList(){
        return listItemDAO.getListItems();
    }

    public void addListItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> listItemDAO.addListItem(listItem));
    }


    public void deleteListItem(final ListItem listItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> listItemDAO.deleteListItem(listItem));
    }
}

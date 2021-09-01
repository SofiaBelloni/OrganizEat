package com.example.organizeat.DataBase;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.organizeat.ListItem;

import java.util.List;

@Dao
public interface ListItemDAO {
    // The selected on conflict strategy ignores a new CardItem
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addListItem(ListItem listItem);

    @Transaction
    @Query("SELECT * from shoppinglist ORDER BY ID DESC")
    List<ListItem> getListItems();

    @Delete
    void deleteListItem(ListItem listItem);
}

package com.example.organizeat.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.organizeat.CardItem;

import java.util.List;

@Dao
public interface CardItemDAO {
    // The selected on conflict strategy ignores a new CardItem
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCardItem(CardItem CardItem);

    @Transaction
    @Query("SELECT * from recipe WHERE recipe.user = :user ORDER BY ID DESC")
    LiveData<List<CardItem>> getCardItems(String user);

    @Transaction
    @Query("SELECT COUNT(ID) FROM recipe  WHERE recipe.user = :user")
    int getCardItemsCount(String user);

    @Update
    void updateCardItem(CardItem CardItem);

    @Query("SELECT category_name FROM category WHERE category.id = :id")
    String getCategoryName(int id);

    @Delete
    void deleteCardItem(CardItem CardItem);
}

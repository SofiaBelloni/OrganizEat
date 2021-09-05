package com.example.organizeat.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.organizeat.CardItem;
import com.example.organizeat.Category;
import com.example.organizeat.ListItem;

import java.util.List;

@Dao
public interface CategoryDAO {
    // The selected on conflict strategy ignores a new CardItem
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCategory(Category category);

    @Transaction
    @Query("SELECT * from Category ORDER BY ID DESC")
    List<Category> getCategories();

    @Transaction
    @Query("SELECT category_name from Category ORDER BY ID DESC")
    List<String> getCategoriesName();

    @Transaction
    @Query("SELECT * from recipe WHERE recipe.category = :categoryId")
    List<CardItem> getRecipeByCategory(int categoryId);

    @Delete
    void deleteCategory(Category category);

    @Update
    void updateCategory(Category category);
}
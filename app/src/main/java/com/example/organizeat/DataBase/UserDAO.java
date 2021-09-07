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
import com.example.organizeat.User;

import java.util.List;

@Dao
public interface UserDAO {
    // The selected on conflict strategy ignores a new CardItem
    // if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Transaction
    @Query("SELECT * from User WHERE User.email = :email")
    List<User> getUserByEmail(String email);

    @Transaction
    @Query("SELECT username from User WHERE user.email = :email")
    String getUserName(String email);

    @Update
    void updateUserName(User user);
}
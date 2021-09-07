package com.example.organizeat.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.organizeat.CardItem;
import com.example.organizeat.Category;
import com.example.organizeat.ListItem;
import com.example.organizeat.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CardItem.class, ListItem.class, Category.class, User.class}, version = 1)
public abstract class OrganizEatDatabase extends RoomDatabase {

    public abstract CardItemDAO cardItemDAO();
    public abstract ListItemDAO listItemDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract UserDAO userDAO();

    private static volatile OrganizEatDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static OrganizEatDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrganizEatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrganizEatDatabase.class, "organizeat_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
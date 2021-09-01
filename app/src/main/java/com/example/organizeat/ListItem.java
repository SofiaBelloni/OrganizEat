package com.example.organizeat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ShoppingList")
public class ListItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "description")
    private String item;

    public ListItem(String item) {
        this.item = item;
    }

    public String getItem(){ return this.item;}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

}

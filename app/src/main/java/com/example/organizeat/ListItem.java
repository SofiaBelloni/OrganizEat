package com.example.organizeat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ShoppingList", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "email",
        childColumns = "user")})
public class ListItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "description")
    private String item;
    @ColumnInfo(name = "user")
    private String user;

    public ListItem(String item, String user) {
        this.item = item;
        this.user = user;
    }

    public String getUser(){return  this.user;}

    public String getItem(){ return this.item;}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

}

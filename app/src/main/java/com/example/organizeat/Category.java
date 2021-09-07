package com.example.organizeat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "email",
        childColumns = "user")})
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;
    @ColumnInfo(name = "category_name")
    private String category;
    @ColumnInfo(name = "user")
    private String user;

    public Category(String category, String user) {
        this.category = category;
        this .user = user;
    }

    public String getCategory(){ return this.category;}

    public void setCategory(String name){
    }

    public String getUser(){return  this.user;}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return category;
    }
}

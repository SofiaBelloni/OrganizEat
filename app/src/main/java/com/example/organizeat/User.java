package com.example.organizeat;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @PrimaryKey()
    @ColumnInfo(name = "email")
    @NonNull
    private String email;
    @ColumnInfo(name = "username")
    private String name;
    @ColumnInfo(name = "password")
    private String password;

    public User(String email, String name, String password) {

        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

}

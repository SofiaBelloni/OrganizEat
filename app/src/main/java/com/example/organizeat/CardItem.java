package com.example.organizeat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


/**
 * Class which represents every card item with its information (image, place, data, description)
 */
@Entity(tableName = "Recipe", foreignKeys =
        {@ForeignKey(entity = Category.class,
        parentColumns = "ID",
        childColumns = "category"),
        @ForeignKey(entity = User.class,
                parentColumns = "email",
                childColumns = "user")})
public class CardItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private  int id;
    @ColumnInfo(name = "image")
    private String imageResource;
    @ColumnInfo(name = "title")
    private String recipe;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "category")
    private int category;
    @ColumnInfo(name = "ingredients")
    private String ingredients;
    @ColumnInfo(name = "yield")
    private String yield;
    @ColumnInfo(name = "cooking_time")
    private String cooking_time;
    @ColumnInfo(name = "directions")
    private String directions;
    @ColumnInfo(name = "user")
    private String user;

    public CardItem(String imageResource, String recipe, String description, int category, String ingredients, String cooking_time, String directions, String yield, String user) {
        this.imageResource = imageResource;
        this.recipe = recipe;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.cooking_time = cooking_time;
        this.yield = yield;
        this.directions = directions;
        this.user = user;
    }

    public void update(String imageResource, String recipe, String description, int category, String ingredients, String cooking_time, String directions, String yield) {
        this.imageResource = imageResource;
        this.recipe = recipe;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.cooking_time = cooking_time;
        this.yield = yield;
        this.directions = directions;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getDescription() {
        return description;
    }

    public int getCategory() {
        return category;
    }

    public String getIngredients() { return ingredients; }

    public String getYield() { return yield; }

    public String getCooking_time() { return cooking_time;}

    public String getDirections() { return directions;}

    public String getUser() { return user;}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

}

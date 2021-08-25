package com.example.organizeat;

/**
 * Class which represents every card item with its information (image, place, data, description)
 */
public class CardItem {

    private String imageResource;

    private String recipe;

    private String description;

    private String category;
    private String ingredients;
    private String yield;
    private String cooking_time;

    private String directions;

    public CardItem(String imageResource, String recipe, String description, String category, String ingredients, String cooking_time, String directions, String yield) {
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

    public String getCategory() {
        return category;
    }

    public String getIngredients() { return ingredients; }

    public String getYield() { return yield; }

    public String getCooking_time() { return cooking_time;}

    public String getDirections() { return directions;}

}

package com.example.organizeat;

/**
 * Class which represents every card item with its information (image, place, data, description)
 */
public class CardItem {

    private String imageResource;

    private String recipe;

    private String description;

    private String category;

    public CardItem(String imageResource, String recipe, String description, String category) {
        this.imageResource = imageResource;
        this.recipe = recipe;
        this.description = description;
        this.category = category;
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

}

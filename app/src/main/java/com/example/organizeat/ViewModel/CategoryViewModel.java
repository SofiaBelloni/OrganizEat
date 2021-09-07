package com.example.organizeat.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.organizeat.CardItem;
import com.example.organizeat.Category;
import com.example.organizeat.DataBase.CategoryRepository;
import com.example.organizeat.DataBase.ListItemRepository;
import com.example.organizeat.ListItem;
import com.example.organizeat.User;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository repository;
    private Category filterCategory;
    User user;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        //read data from db
        this.repository = new CategoryRepository(application);
    }

    public void setUser(User user){this.user = user;}

    public User getUser(){return this.user;}

    public void addCategory(Category category){ this.repository.addCategory(category);}

    public boolean isUsed(Category category){ return !this.repository.getRecipeByCategory(category.getId(), user.getEmail()).isEmpty();}

    public List<Category> getCategories(){ return this.repository.getCategories(user.getEmail());}

    public List<String> getCategoriesName(){ return this.repository.getCategoriesName(user.getEmail());}

    public void updateCategory(Category category){ this.repository.updateCategory(category); }

    public void deleteCategory(final Category category) {this.repository.deleteCategory(category);}

    public void setFilterCategory(Category category){this.filterCategory = category;}

    public int getFilterCategoryId(){ return this.filterCategory.getId();}

    public List<CardItem> getRecipeByCategory(Category category){return this.repository.getRecipeByCategory(category.getId(),user.getEmail());}
}

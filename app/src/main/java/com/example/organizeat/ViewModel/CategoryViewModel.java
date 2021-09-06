package com.example.organizeat.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.organizeat.CardItem;
import com.example.organizeat.Category;
import com.example.organizeat.DataBase.CategoryRepository;
import com.example.organizeat.DataBase.ListItemRepository;
import com.example.organizeat.ListItem;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository repository;
    private Category filterCategory;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        //read data from db
        this.repository = new CategoryRepository(application);
    }

    public void addCategory(Category category){ this.repository.addCategory(category);}

    public boolean isUsed(Category category){ return !this.repository.getRecipeByCategory(category.getId()).isEmpty();}

    public List<Category> getCategories(){ return this.repository.getCategories();}

    public List<String> getCategoriesName(){ return this.repository.getCategoriesName();}

    public void updateCategory(Category category){ this.repository.updateCategory(category); }

    public void deleteCategory(final Category category) {this.repository.deleteCategory(category);}

    public void setFilterCategory(Category category){this.filterCategory = category;}

    public int getFilterCategoryId(){ return this.filterCategory.getId();}

    public List<CardItem> getRecipeByCategory(Category category){return this.repository.getRecipeByCategory(category.getId());}
}

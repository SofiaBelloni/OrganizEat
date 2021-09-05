package com.example.organizeat.DataBase;

import android.app.Application;

import com.example.organizeat.CardItem;
import com.example.organizeat.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;

    public CategoryRepository(Application application) {
        OrganizEatDatabase db = OrganizEatDatabase.getDatabase(application);
        categoryDAO = db.categoryDAO();
    }

    public List<Category> getCategories(){
        return categoryDAO.getCategories();
    }

    public List<String> getCategoriesName(){
        return categoryDAO.getCategoriesName();
    }

    public List<CardItem> getRecipeByCategory(int categoryId){return categoryDAO.getRecipeByCategory(categoryId);}

    public void addCategory(final Category category) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> categoryDAO.addCategory(category));
    }


    public void deleteCategory(final Category category) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> categoryDAO.deleteCategory(category));
    }

    public void updateCategory(final Category category) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> categoryDAO.updateCategory(category));
    }
}

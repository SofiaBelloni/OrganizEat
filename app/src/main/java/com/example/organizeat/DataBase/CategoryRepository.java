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

    public List<Category> getCategories(String user){
        return categoryDAO.getCategories(user);
    }

    public List<String> getCategoriesName(String user){
        return categoryDAO.getCategoriesName(user);
    }

    public List<CardItem> getRecipeByCategory(int categoryId, String user){return categoryDAO.getRecipeByCategory(categoryId, user);}

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

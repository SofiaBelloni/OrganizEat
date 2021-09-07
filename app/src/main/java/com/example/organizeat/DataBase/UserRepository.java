package com.example.organizeat.DataBase;

import android.app.Application;

import com.example.organizeat.CardItem;
import com.example.organizeat.User;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;

    public UserRepository(Application application) {
        OrganizEatDatabase db = OrganizEatDatabase.getDatabase(application);
        userDAO = db.userDAO();
    }

    public List<User> getUserByEmail(String email){ return userDAO.getUserByEmail(email); }

    public String getUserName(String email){
        return userDAO.getUserName(email);
    }

    public void updateUserName(User user){ userDAO.updateUserName(user); }

    public void addUser(final User user) {
        OrganizEatDatabase.databaseWriteExecutor.execute(() -> userDAO.addUser(user));
    }

}

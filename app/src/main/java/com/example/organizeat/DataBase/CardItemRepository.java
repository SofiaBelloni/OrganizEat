package com.example.organizeat.DataBase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.organizeat.CardItem;

import java.util.List;

public class CardItemRepository {
    private CardItemDAO cardItemDAO;
    private LiveData<List<CardItem>> cardItemList;

    public CardItemRepository(Application application) {
        OrganizEatDatabase db = OrganizEatDatabase.getDatabase(application);
        cardItemDAO = db.cardItemDAO();
        cardItemList = cardItemDAO.getCardItems();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<CardItem>> getCardItemList(){
        return cardItemList;
    }

    public String getCategoryName(int id){return cardItemDAO.getCategoryName(id);}

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addCardItem(final CardItem CardItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.addCardItem(CardItem);
            }
        });
    }

    public void updateCardItem(final CardItem CardItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.updateCardItem(CardItem);
            }
        });
    }

    public void deleteCardItem(final CardItem CardItem) {
        OrganizEatDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.deleteCardItem(CardItem);
            }
        });
    }
}

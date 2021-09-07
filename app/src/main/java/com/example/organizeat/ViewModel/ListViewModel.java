package com.example.organizeat.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.CardItem;
import com.example.organizeat.DataBase.CardItemRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final MutableLiveData<CardItem> itemSelected = new MutableLiveData<>();
    private LiveData<List<CardItem>> cardItems;
    private CardItemRepository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CardItemRepository(application);
    }

    public String categoryName(){return this.repository.getCategoryName(this.itemSelected.getValue().getCategory());}

    public String categoryNameById(int id){return this.repository.getCategoryName(id);}

    public void select(CardItem cardItem){ this.itemSelected.setValue(cardItem); }

    public LiveData<CardItem> getSelected(){ return this.itemSelected; }

    public void deleteSelected(){ this.repository.deleteCardItem(this.itemSelected.getValue());}

    public void updateSelected(String imageResource, String recipe, String description, int category, String ingredients, String cooking_time, String directions, String yield){
        this.itemSelected.getValue().update(imageResource, recipe, description, category, ingredients, cooking_time, directions, yield);
        this.repository.updateCardItem(this.itemSelected.getValue());
    }

    public LiveData<List<CardItem>> getCardItems(String username){
        this.cardItems = this.repository.getCardItemList(username);
        return  cardItems;}

    public int getCardItemsCount(String username){return this.repository.getCardItemsCount(username);}

    public CardItem getCardItem(int position){
        return cardItems.getValue() == null ? null : cardItems.getValue().get(position);
    }
}

package com.example.organizeat.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.CardItem;
import com.example.organizeat.DataBase.CardItemRepository;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final MutableLiveData<CardItem> itemSelected = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> imageBitMap = new MutableLiveData<>();
    private LiveData<List<CardItem>> cardItems;
    private CardItemRepository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CardItemRepository(application);
        this.cardItems = this.repository.getCardItemList();
    }

    public LiveData<Bitmap> getBitMap() {
        return this.imageBitMap;
    }

    public void setBitMap(Bitmap bitmap) { this.imageBitMap.setValue(bitmap);}

    public void select(CardItem cardItem){ this.itemSelected.setValue(cardItem); }

    public LiveData<CardItem> getSelected(){ return this.itemSelected; }

    public void deleteSelected(){ this.repository.deleteCardItem(this.itemSelected.getValue());}

    public void updateSelected(String imageResource, String recipe, String description, String category, String ingredients, String cooking_time, String directions, String yield){
        this.itemSelected.getValue().update(imageResource, recipe, description, category, ingredients, cooking_time, directions, yield);
        this.repository.updateCardItem(this.itemSelected.getValue());
    }

    public LiveData<List<CardItem>> getCardItems(){ return this.cardItems; }

    public CardItem getCardItem(int position){
        return cardItems.getValue() == null ? null : cardItems.getValue().get(position);
    }
}

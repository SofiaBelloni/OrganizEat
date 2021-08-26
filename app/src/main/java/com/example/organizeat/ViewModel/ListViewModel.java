package com.example.organizeat.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.CardItem;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final MutableLiveData<CardItem> itemSelected = new MutableLiveData<>();
    private MutableLiveData<List<CardItem>> cardItems;

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void select(CardItem cardItem){ itemSelected.setValue(cardItem); }

    public LiveData<CardItem> getSelected(){ return this.itemSelected; }

    public LiveData<List<CardItem>> getCardItems(){
        if(this.cardItems == null){
            this.cardItems = new MutableLiveData<>();
            loadItems();
        }
        return this.cardItems;
    }
    
    public void addCardItem(CardItem item){
        ArrayList<CardItem> list = new ArrayList<>();
        list.add(item);
        if(this.cardItems.getValue() != null)
            list.addAll(this.cardItems.getValue());
        cardItems.setValue(list);
    }

    public CardItem getCardItem(int position){
        return cardItems.getValue() == null ? null : cardItems.getValue().get(position);
    }

    private void loadItems(){
        //asynchronous operation to fetch cardItems
        addCardItem(new CardItem("ic_baseline_android_24", "Recipe", "Desc1",
                "Antipasto", "Ing", "5 ore", "vhvhv", "4 persone"));
        addCardItem(new CardItem("ic_baseline_android_24", "Prova", "Desc",
                "primo", "Ing", "5 ore", "vhvhv", "4 persone"));
        
    }
}

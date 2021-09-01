package com.example.organizeat.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.CardItem;
import com.example.organizeat.DataBase.CardItemRepository;

public class AddViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitMap = new MutableLiveData<>();
    private CardItemRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        this.repository = new CardItemRepository(application);
    }

    public LiveData<Bitmap> getBitMap() {
        return this.imageBitMap;
    }

    public void setBitMap(Bitmap bitmap) { this.imageBitMap.setValue(bitmap);}

    public void addCardItem(CardItem item){ this.repository.addCardItem(item);}

}

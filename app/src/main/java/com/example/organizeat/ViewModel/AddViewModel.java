package com.example.organizeat.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.CardItem;
import com.example.organizeat.DataBase.CardItemRepository;
import com.example.organizeat.R;
import com.example.organizeat.Utilities;

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

    public void updateCardItem(CardItem item){ this.repository.updateCardItem(item);}

}

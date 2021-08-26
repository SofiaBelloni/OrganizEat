package com.example.organizeat.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.organizeat.R;
import com.example.organizeat.Utilities;

public class AddViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitMap = new MutableLiveData<>();
    private final Application application;

    public AddViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        initializeBitMap();
    }

    public MutableLiveData<Bitmap> getBitMap() { return imageBitMap;}

    public void setBitMap(Bitmap bitmap) { this.imageBitMap.setValue(bitmap);}

    private void initializeBitMap() {
        Drawable drawable = ResourcesCompat.getDrawable(this.application.getResources(),
                R.drawable.ic_launcher_foreground, this.application.getTheme());
        Bitmap bitmap = Utilities.drawableToBitMap(drawable);
        this.imageBitMap.setValue(bitmap);
    }

}

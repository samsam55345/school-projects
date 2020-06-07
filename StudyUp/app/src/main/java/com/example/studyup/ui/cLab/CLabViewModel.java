package com.example.studyup.ui.cLab;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CLabViewModel {
    private MutableLiveData<String> mText;

    public CLabViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CLab fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

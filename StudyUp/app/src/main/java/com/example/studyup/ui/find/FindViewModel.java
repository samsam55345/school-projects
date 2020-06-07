package com.example.studyup.ui.find;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FIND fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
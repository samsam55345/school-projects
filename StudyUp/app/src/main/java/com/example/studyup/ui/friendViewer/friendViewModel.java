package com.example.studyup.ui.friendViewer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class friendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public friendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("friends");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
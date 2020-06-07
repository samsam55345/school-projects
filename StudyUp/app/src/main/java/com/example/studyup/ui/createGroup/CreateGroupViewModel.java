package com.example.studyup.ui.createGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateGroupViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateGroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MANAGE GROUPS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.studyup.ui.manageGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageGroupViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageGroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MANAGE GROUPS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.example.studyup.ui.manageGroupDetailFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageGroupDetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageGroupDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MANAGE Detail fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
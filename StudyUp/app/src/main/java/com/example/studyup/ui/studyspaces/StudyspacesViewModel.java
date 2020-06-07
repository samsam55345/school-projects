package com.example.studyup.ui.studyspaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudyspacesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StudyspacesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is BROWSE STUDY SPACES fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
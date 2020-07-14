package com.patanjali.patanjaliiasclasses.fregment.morningclass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MorningViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MorningViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
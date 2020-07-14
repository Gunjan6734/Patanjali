package com.patanjali.patanjaliiasclasses.fregment.mornningclasdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MorningdetailViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MorningdetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
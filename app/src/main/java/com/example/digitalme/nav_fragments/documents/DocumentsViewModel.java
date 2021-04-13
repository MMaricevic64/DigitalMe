package com.example.digitalme.nav_fragments.documents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentsViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public DocumentsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is documents fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }

}

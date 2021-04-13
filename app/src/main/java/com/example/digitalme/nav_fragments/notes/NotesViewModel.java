package com.example.digitalme.nav_fragments.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotesViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public NotesViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is notes fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }

}

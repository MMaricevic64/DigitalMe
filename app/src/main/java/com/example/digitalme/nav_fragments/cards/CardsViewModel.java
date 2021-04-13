package com.example.digitalme.nav_fragments.cards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardsViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public CardsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is cards fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }

}

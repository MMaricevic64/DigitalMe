package com.example.digitalme.nav_fragments.cards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardsViewModel extends AndroidViewModel {
    private CardTypeRepository repository;
    private LiveData<List<CardType>> allCardTypes;


    public CardsViewModel(@NonNull Application application) {
        super(application);
        repository = new CardTypeRepository(application);
        allCardTypes = repository.getAllCardTypes();
    }
    public void insert(CardType cardType){
        repository.insert(cardType);
    }
    public LiveData<List<CardType>> getAllCardTypes(){return allCardTypes; }
}

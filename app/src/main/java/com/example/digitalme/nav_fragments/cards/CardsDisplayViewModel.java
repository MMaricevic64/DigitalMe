package com.example.digitalme.nav_fragments.cards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardsDisplayViewModel extends AndroidViewModel {
    private CardRepository repository;

    public CardsDisplayViewModel(@NonNull Application application){
        super(application);
        repository = new CardRepository(application);
    }

    public void insert(Card card){
        repository.insert(card);
    }
    public void delete(Card card) { repository.delete(card); }
    public LiveData<List<Card>> getAllCards(int ID_card_type) { return repository.getAllCards(ID_card_type);}
}

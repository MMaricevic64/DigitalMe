package com.example.digitalme.nav_fragments.cards;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BusinessCardDisplayViewModel extends AndroidViewModel {
    private BusinessCardRepository repository;

    public BusinessCardDisplayViewModel(@NonNull Application application){
        super(application);
        repository = new BusinessCardRepository(application);
    }

    public void insert(BusinessCard businesscard){
        repository.insert(businesscard);
    }
    public void delete(BusinessCard businesscard) { repository.delete(businesscard); }
    public LiveData<List<BusinessCard>> getAllBusinessCards(int ID_card_type) { return repository.getAllBusinessCards(ID_card_type);}
}

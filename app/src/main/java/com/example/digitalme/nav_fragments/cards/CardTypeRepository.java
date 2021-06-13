package com.example.digitalme.nav_fragments.cards;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.digitalme.DigiMeDatabase;

import java.util.List;

public class CardTypeRepository {
    private CardTypeDao cardTypeDao;
    private LiveData<List<CardType>> allCardTypes;

    public CardTypeRepository(Application application) {
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        cardTypeDao = database.cardTypeDao();
        allCardTypes = cardTypeDao.getAllCardTypes();
    }

    public void insert(CardType cardType){
        new InsertCardTypeAsyncTask(cardTypeDao).execute(cardType);
    }

    public LiveData<List<CardType>> getAllCardTypes() {
        return allCardTypes;
    }

    private static class InsertCardTypeAsyncTask extends AsyncTask<CardType, Void, Void> {
        private CardTypeDao cardTypeDao;

        private InsertCardTypeAsyncTask(CardTypeDao cardTypeDao){
            this.cardTypeDao = cardTypeDao;
        }

        @Override
        protected Void doInBackground(CardType... cardTypes) {
            cardTypeDao.insert((cardTypes[0]));
            return null;
        }
    }
}

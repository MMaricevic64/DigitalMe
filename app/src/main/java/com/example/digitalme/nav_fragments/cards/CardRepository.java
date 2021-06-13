package com.example.digitalme.nav_fragments.cards;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.digitalme.DigiMeDatabase;

import java.util.List;

public class CardRepository {
    private CardDao cardDao;

    public CardRepository(Application application){
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        cardDao = database.cardDao();
    }

    public void insert(Card card) {
        new CardRepository.InsertCardAsyncTask(cardDao).execute(card);
    }
    public void delete(Card card) {
        new CardRepository.DeleteCardAsyncTask(cardDao).execute(card);
    }

    public LiveData<List<Card>> getAllCards(int ID_type_card){
        return cardDao.getAllCards(ID_type_card);
    }

    private static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        private InsertCardAsyncTask(CardDao cardDao) {
            this.cardDao= cardDao;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.insert(cards[0]);
            return null;
        }
    }

    private static class DeleteCardAsyncTask extends AsyncTask<Card, Void, Void> {
        private CardDao cardDao;
        private DeleteCardAsyncTask(CardDao cardDao) {
            this.cardDao= cardDao;
        }
        @Override
        protected Void doInBackground(Card... cards) {
            cardDao.delete(cards[0]);
            return null;
        }
    }

}

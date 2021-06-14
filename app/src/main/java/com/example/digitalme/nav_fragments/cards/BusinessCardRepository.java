package com.example.digitalme.nav_fragments.cards;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.digitalme.DigiMeDatabase;

import java.util.List;

public class BusinessCardRepository {
    private BusinessCardDao businessCardDao;

    public BusinessCardRepository(Application application){
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        businessCardDao = database.businessCardDao();
    }

    public void insert(BusinessCard businesscard) {
        new InsertBusinessCardAsyncTask(businessCardDao).execute(businesscard);
    }
    public void delete(BusinessCard businesscard) {
        new DeleteBusinessCardAsyncTask(businessCardDao).execute(businesscard);
    }

    public LiveData<List<BusinessCard>> getAllBusinessCards(int ID_type_card){
        return businessCardDao.getAllBusinessCards(ID_type_card);
    }

    private static class InsertBusinessCardAsyncTask extends AsyncTask<BusinessCard, Void, Void> {
        private BusinessCardDao businessCardDao;
        private InsertBusinessCardAsyncTask(BusinessCardDao businessCardDao) {
            this.businessCardDao= businessCardDao;
        }
        @Override
        protected Void doInBackground(BusinessCard... businessCards) {
            businessCardDao.insert(businessCards[0]);
            return null;
        }
    }

    private static class DeleteBusinessCardAsyncTask extends AsyncTask<BusinessCard, Void, Void> {
        private BusinessCardDao businessCardDao;
        private DeleteBusinessCardAsyncTask(BusinessCardDao businessCardDao) {
            this.businessCardDao= businessCardDao;
        }
        @Override
        protected Void doInBackground(BusinessCard... businessCards) {
            businessCardDao.delete(businessCards[0]);
            return null;
        }
    }
}

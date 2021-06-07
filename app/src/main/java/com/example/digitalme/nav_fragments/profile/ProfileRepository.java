package com.example.digitalme.nav_fragments.profile;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.example.digitalme.DigiMeDatabase;

public class ProfileRepository {
    private ProfileDao profileDao;
    private LiveData<Profile> profile;

    public ProfileRepository(Application application) {
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        this.profileDao = database.profileDao();
        this.profile = profileDao.getProfile();
    }

    public void insert(Profile profile){
        new InsertProfileAsyncTask(profileDao).execute(profile);
    }

    public void update(Profile profile){
        new UpdateProfileAsyncTask(profileDao).execute(profile);
    }

    public void delete(Profile profile) {
        new DeleteProfileAsyncTask(profileDao).execute(profile);
    }

    public LiveData<Profile> getProfile(){
        return profile;
    }

    //AsyncTask for inserting profile in database
    private static class InsertProfileAsyncTask extends AsyncTask<Profile,Void,Void>{
        private ProfileDao profileDao;

        public InsertProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.insert(profiles[0]);
            return null;
        }
    }

    //AsyncTask for updating profile in database
    private static class UpdateProfileAsyncTask extends AsyncTask<Profile,Void,Void>{
        private ProfileDao profileDao;

        public UpdateProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.update(profiles[0]);
            return null;
        }
    }

    //AsyncTask for deleting profile in database
    private static class DeleteProfileAsyncTask extends AsyncTask<Profile,Void,Void>{
        private ProfileDao profileDao;

        public DeleteProfileAsyncTask(ProfileDao profileDao) {
            this.profileDao = profileDao;
        }

        @Override
        protected Void doInBackground(Profile... profiles) {
            profileDao.delete(profiles[0]);
            return null;
        }
    }

}

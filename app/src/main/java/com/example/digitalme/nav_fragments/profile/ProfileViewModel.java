package com.example.digitalme.nav_fragments.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends AndroidViewModel{

    // TODO: Implement the ViewModel
    private ProfileRepository repository;
    private LiveData<Profile> profile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new ProfileRepository(application);
        profile = repository.getProfile();
    }

    public void insert(Profile profile){
        repository.insert(profile);
    }

    public void update(Profile profile){
        repository.update(profile);
    }

    public void delete(Profile profile){
        repository.delete(profile);
    }

    public LiveData<Profile> getProfile(){
        return profile;
    }
}

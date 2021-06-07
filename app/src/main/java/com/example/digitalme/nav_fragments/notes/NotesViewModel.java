package com.example.digitalme.nav_fragments.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalme.nav_fragments.profile.Profile;
import com.example.digitalme.nav_fragments.profile.ProfileRepository;

public class NotesViewModel extends AndroidViewModel {

    /// TODO: Implement the ViewModel
    //private NotesRepository repository;
    //private LiveData<Note> notes;

    public NotesViewModel(@NonNull Application application){
        super(application);
        //repository = new ProfileRepository(application);
        //notes = repository.getNotes();
    }

    public void insert(Profile profile){
        //repository.insert(profile);
    }

    public void update(Profile profile){
        //repository.update(profile);
    }

    public void delete(Profile profile){
        //repository.delete(profile);
    }

}

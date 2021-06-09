package com.example.digitalme.nav_fragments.notes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.digitalme.nav_fragments.profile.Profile;
import com.example.digitalme.nav_fragments.profile.ProfileRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    /// TODO: Implement the ViewModel
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NotesViewModel(@NonNull Application application){
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}

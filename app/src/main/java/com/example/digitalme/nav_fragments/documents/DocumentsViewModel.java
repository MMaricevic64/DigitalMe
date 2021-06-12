package com.example.digitalme.nav_fragments.documents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DocumentsViewModel extends AndroidViewModel {

    private DocumentTypeRepository repository;
    private LiveData<List<DocumentType>> allDocumentTypes;

    public DocumentsViewModel(@NonNull Application application){
        super(application);
        repository = new DocumentTypeRepository(application);
        allDocumentTypes = repository.getAllDocumentTypes();
    }

    public void insert(DocumentType documentType){
        repository.insert(documentType);
    }
    public void delete(DocumentType documentType) { repository.delete(documentType); }
    public LiveData<List<DocumentType>> getAllDocumentTypes() { return allDocumentTypes; }
}

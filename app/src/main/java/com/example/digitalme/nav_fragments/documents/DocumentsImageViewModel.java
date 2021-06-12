package com.example.digitalme.nav_fragments.documents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DocumentsImageViewModel extends AndroidViewModel {
    private DocumentRepository repository;

    public DocumentsImageViewModel(@NonNull Application application){
        super(application);
        repository = new DocumentRepository(application);
    }

    public void insert(Document document){
        repository.insert(document);
    }
    public void delete(Document document) { repository.delete(document); }
    public LiveData<List<Document>> getAllDocument(int ID_document_type) { return repository.getAllDocuments(ID_document_type);}

}

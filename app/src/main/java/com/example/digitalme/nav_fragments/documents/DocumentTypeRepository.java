package com.example.digitalme.nav_fragments.documents;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.digitalme.DigiMeDatabase;
import com.example.digitalme.nav_fragments.notes.Note;
import com.example.digitalme.nav_fragments.notes.NoteDao;
import com.example.digitalme.nav_fragments.notes.NoteRepository;

import java.util.List;

public class DocumentTypeRepository {

    private DocumentTypeDao documentTypeDao;
    private LiveData<List<DocumentType>> allDocumentTypes;

    public DocumentTypeRepository(Application application){
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        documentTypeDao = database.documentTypeDao();
        allDocumentTypes = documentTypeDao.getAllDocumentTypes();
    }

    public void insert(DocumentType documentType) {
        new DocumentTypeRepository.InsertDocumentTypeAsyncTask(documentTypeDao).execute(documentType);
    }
    public void delete(DocumentType documentType) {
        new DocumentTypeRepository.DeleteDocumentTypeAsyncTask(documentTypeDao).execute(documentType);
    }

    public LiveData<List<DocumentType>> getAllDocumentTypes() {
        return allDocumentTypes;
    }

    private static class InsertDocumentTypeAsyncTask extends AsyncTask<DocumentType, Void, Void> {
        private DocumentTypeDao documentTypeDao;
        private InsertDocumentTypeAsyncTask(DocumentTypeDao documentTypeDao) {
            this.documentTypeDao= documentTypeDao;
        }
        @Override
        protected Void doInBackground(DocumentType... documentTypes) {
            documentTypeDao.insert(documentTypes[0]);
            return null;
        }
    }

    private static class DeleteDocumentTypeAsyncTask extends AsyncTask<DocumentType, Void, Void> {
        private DocumentTypeDao documentTypeDao;
        private DeleteDocumentTypeAsyncTask(DocumentTypeDao documentTypeDao) {
            this.documentTypeDao= documentTypeDao;
        }
        @Override
        protected Void doInBackground(DocumentType... documentTypes) {
            documentTypeDao.delete(documentTypes[0]);
            return null;
        }
    }

}

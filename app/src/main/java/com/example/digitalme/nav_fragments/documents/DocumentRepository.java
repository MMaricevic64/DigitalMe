package com.example.digitalme.nav_fragments.documents;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.digitalme.DigiMeDatabase;

import java.util.List;

public class DocumentRepository {
    private DocumentDao documentDao;

    public DocumentRepository(Application application){
        DigiMeDatabase database = DigiMeDatabase.getInstance(application);
        documentDao = database.documentDao();
    }

    public void insert(Document document) {
        new DocumentRepository.InsertDocumentAsyncTask(documentDao).execute(document);
    }
    public void delete(Document document) {
        new DocumentRepository.DeleteDocumentAsyncTask(documentDao).execute(document);
    }

    public LiveData<List<Document>> getAllDocuments(int ID_type_document){
        return documentDao.getAllDocuments(ID_type_document);
    }

    private static class InsertDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
        private DocumentDao documentDao;
        private InsertDocumentAsyncTask(DocumentDao documentDao) {
            this.documentDao= documentDao;
        }
        @Override
        protected Void doInBackground(Document... documents) {
            documentDao.insert(documents[0]);
            return null;
        }
    }

    private static class DeleteDocumentAsyncTask extends AsyncTask<Document, Void, Void> {
        private DocumentDao documentDao;
        private DeleteDocumentAsyncTask(DocumentDao documentDao) {
            this.documentDao= documentDao;
        }
        @Override
        protected Void doInBackground(Document... documents) {
            documentDao.delete(documents[0]);
            return null;
        }
    }

}

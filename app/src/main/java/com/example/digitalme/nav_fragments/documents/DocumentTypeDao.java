package com.example.digitalme.nav_fragments.documents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digitalme.nav_fragments.notes.Note;

import java.util.List;

@Dao
public interface DocumentTypeDao {
    @Insert
    void insert(DocumentType documentType);

    @Delete
    void delete(DocumentType documentType);

    @Query("SELECT * FROM DocumentType")
    LiveData<List<DocumentType>> getAllDocumentTypes();
}

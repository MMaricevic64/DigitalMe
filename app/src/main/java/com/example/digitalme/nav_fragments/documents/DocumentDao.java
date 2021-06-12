package com.example.digitalme.nav_fragments.documents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DocumentDao {
    @Insert
    void insert(Document document);

    @Delete
    void delete(Document document);

    @Query("SELECT * FROM Document WHERE document_type == :document_type")
    LiveData<List<Document>> getAllDocuments(int document_type);
}

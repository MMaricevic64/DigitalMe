package com.example.digitalme.nav_fragments.documents;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = DocumentType.class,
        parentColumns = "id",
        childColumns = "Document_type",
        onDelete = ForeignKey.CASCADE))
public class Document {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "URI")
    private String picture_uri;
    @ColumnInfo(name = "Document_type")
    private int document_type;

    public Document(String picture_uri, int document_type) {
        this.picture_uri = picture_uri;
        this.document_type = document_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture_uri() {
        return picture_uri;
    }

    public int getDocument_type() {
        return document_type;
    }

    public void setPicture_uri(String picture_uri) {
        this.picture_uri = picture_uri;
    }

    public void setDocument_type(int document_type) {
        this.document_type = document_type;
    }
}

/*
foreignKeys = @ForeignKey(entity = DocumentType.class,
                                  parentColumns = "id",
                                  childColumns = "Document_type",
                                  onDelete = 1)
 */
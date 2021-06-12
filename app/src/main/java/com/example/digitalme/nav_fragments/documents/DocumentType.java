package com.example.digitalme.nav_fragments.documents;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DocumentType {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Title")
    private String title;

    public DocumentType(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

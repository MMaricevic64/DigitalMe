package com.example.digitalme;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.digitalme.nav_fragments.cards.BusinessCard;
import com.example.digitalme.nav_fragments.cards.BusinessCardDao;
import com.example.digitalme.nav_fragments.cards.Card;
import com.example.digitalme.nav_fragments.cards.CardDao;
import com.example.digitalme.nav_fragments.cards.CardType;
import com.example.digitalme.nav_fragments.cards.CardTypeDao;
import com.example.digitalme.nav_fragments.documents.Document;
import com.example.digitalme.nav_fragments.documents.DocumentDao;
import com.example.digitalme.nav_fragments.documents.DocumentType;
import com.example.digitalme.nav_fragments.documents.DocumentTypeDao;
import com.example.digitalme.nav_fragments.notes.NoteDao;
import com.example.digitalme.nav_fragments.notes.Note;
import com.example.digitalme.nav_fragments.profile.Profile;
import com.example.digitalme.nav_fragments.profile.ProfileDao;

//mozda treba updejtati verziju kada se mijenja nesto sa databesom
@Database(entities = {Profile.class, Note.class, DocumentType.class, Document.class, CardType.class, Card.class, BusinessCard.class}, version = 10, exportSchema = false)
public abstract class DigiMeDatabase extends RoomDatabase {

    private static DigiMeDatabase instance;

    //List of Dao's
    public abstract ProfileDao profileDao();
    public abstract NoteDao noteDao();
    public abstract DocumentTypeDao documentTypeDao();
    public abstract DocumentDao documentDao();
    public abstract CardTypeDao cardTypeDao();
    public abstract CardDao cardDao();
    public abstract BusinessCardDao businessCardDao();

    //Return instance of database
    public static synchronized DigiMeDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DigiMeDatabase.class,"DigiMe_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

}

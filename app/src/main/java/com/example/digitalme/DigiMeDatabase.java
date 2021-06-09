package com.example.digitalme;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.digitalme.nav_fragments.notes.NoteDao;
import com.example.digitalme.nav_fragments.notes.Note;
import com.example.digitalme.nav_fragments.profile.Profile;
import com.example.digitalme.nav_fragments.profile.ProfileDao;

//mozda treba updejtati verziju kada se mijenja nesto sa databesom
@Database(entities = {Profile.class, Note.class}, version = 2, exportSchema = false)
public abstract class DigiMeDatabase extends RoomDatabase {

    private static DigiMeDatabase instance;

    //List of Dao's
    public abstract ProfileDao profileDao();
    public abstract NoteDao noteDao();

    //Return instance of database
    public static synchronized DigiMeDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DigiMeDatabase.class,"DigiMe_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

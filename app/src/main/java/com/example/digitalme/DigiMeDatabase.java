package com.example.digitalme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.digitalme.nav_fragments.profile.Profile;
import com.example.digitalme.nav_fragments.profile.ProfileDao;

@Database(entities = {Profile.class}, version = 1)
public abstract class DigiMeDatabase extends RoomDatabase {

    private static DigiMeDatabase instance;

    //List of Dao's
    public abstract ProfileDao profileDao();

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

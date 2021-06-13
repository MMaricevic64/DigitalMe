package com.example.digitalme.nav_fragments.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardTypeDao {

    @Insert
    void insert(CardType cardType);

    @Query("SELECT * FROM CardType")
    LiveData<List<CardType>> getAllCardTypes();

}

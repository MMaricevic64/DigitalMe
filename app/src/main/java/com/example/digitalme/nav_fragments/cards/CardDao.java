package com.example.digitalme.nav_fragments.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void insert(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM Card WHERE card_type == :card_type")
    LiveData<List<Card>> getAllCards(int card_type);

}

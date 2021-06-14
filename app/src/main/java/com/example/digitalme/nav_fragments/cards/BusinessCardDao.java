package com.example.digitalme.nav_fragments.cards;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface BusinessCardDao {
    @Insert
    void insert(BusinessCard businesscard);

    @Delete
    void delete(BusinessCard businesscard);

    @Query("SELECT * FROM BusinessCard WHERE Card_type == :card_type")
    LiveData<List<BusinessCard>> getAllBusinessCards(int card_type);
}

package com.example.digitalme.nav_fragments.cards;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = CardType.class,
        parentColumns = "id",
        childColumns = "Card_type",
        onDelete = ForeignKey.CASCADE))
public class BusinessCard {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Business_card_owner")
    private String business_card_owner;
    @ColumnInfo(name = "Business_card_owner_location")
    private String business_card_owner_location;
    @ColumnInfo(name = "Business_card_email")
    private String business_card_email;
    @ColumnInfo(name = "Business_card_phone")
    private String business_card_phone;
    @ColumnInfo(name = "Business_card_company")
    private String business_card_company;
    @ColumnInfo(name = "Card_type")
    private int card_type;

    public BusinessCard(String business_card_owner, String business_card_owner_location, String business_card_email, String business_card_phone, String business_card_company, int card_type) {
        this.business_card_owner = business_card_owner;
        this.business_card_owner_location = business_card_owner_location;
        this.business_card_email = business_card_email;
        this.business_card_phone = business_card_phone;
        this.business_card_company = business_card_company;
        this.card_type = card_type;
    }

    public int getId() {
        return id;
    }

    public String getBusiness_card_owner() {
        return business_card_owner;
    }

    public String getBusiness_card_owner_location() {
        return business_card_owner_location;
    }

    public String getBusiness_card_email() {
        return business_card_email;
    }

    public String getBusiness_card_phone() {
        return business_card_phone;
    }

    public String getBusiness_card_company() {
        return business_card_company;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBusiness_card_owner(String business_card_owner) {
        this.business_card_owner = business_card_owner;
    }

    public void setBusiness_card_owner_location(String business_card_owner_location) {
        this.business_card_owner_location = business_card_owner_location;
    }

    public void setBusiness_card_email(String business_card_email) {
        this.business_card_email = business_card_email;
    }

    public void setBusiness_card_phone(String business_card_phone) {
        this.business_card_phone = business_card_phone;
    }

    public void setBusiness_card_company(String business_card_company) {
        this.business_card_company = business_card_company;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }
}

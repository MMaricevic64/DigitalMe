package com.example.digitalme.nav_fragments.cards;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = CardType.class,
        parentColumns = "id",
        childColumns = "Card_type",
        onDelete = ForeignKey.CASCADE))
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Card_description")
    private String card_description;
    @ColumnInfo(name = "Card_number")
    private String card_number;
    @ColumnInfo(name = "Card_cvv",defaultValue = "NULL")
    @Nullable
    private String card_cvv;
    @ColumnInfo(name = "Card_expiration",defaultValue = "NULL")
    @Nullable
    private String card_expiration;
    @ColumnInfo(name = "Card_owner")
    private String card_owner;
    @ColumnInfo(name = "Card_account_number",defaultValue = "NULL")
    @Nullable
    private String card_account_number;
    @ColumnInfo(name = "Card_iban",defaultValue = "NULL")
    @Nullable
    private String card_iban;
    @ColumnInfo(name = "Card_type")
    private int card_type;

    public Card(String card_description, String card_number, String card_cvv, String card_expiration, String card_owner, String card_account_number, String card_iban, int card_type) {
        this.card_description = card_description;
        this.card_number = card_number;
        this.card_cvv = card_cvv;
        this.card_expiration = card_expiration;
        this.card_owner = card_owner;
        this.card_account_number = card_account_number;
        this.card_iban = card_iban;
        this.card_type = card_type;
    }


    public int getId() {
        return id;
    }

    public String getCard_description() {
        return card_description;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getCard_cvv() {
        return card_cvv;
    }

    public String getCard_expiration() {
        return card_expiration;
    }

    public String getCard_owner() {
        return card_owner;
    }

    public String getCard_account_number() {
        return card_account_number;
    }

    public String getCard_iban() {
        return card_iban;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCard_description(String card_description) {
        this.card_description = card_description;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public void setCard_ccv(String card_cvv) {
        this.card_cvv = card_cvv;
    }

    public void setCard_expiration(String card_expiration) {
        this.card_expiration = card_expiration;
    }

    public void setCard_owner(String card_owner) {
        this.card_owner = card_owner;
    }

    public void setCard_account_number(String card_account_number) {
        this.card_account_number = card_account_number;
    }

    public void setCard_iban(String card_iban) {
        this.card_iban = card_iban;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }
}

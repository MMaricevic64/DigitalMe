package com.example.digitalme.nav_fragments.profile;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Picture")
    private String picture_uri;
    @ColumnInfo(name = "Name")
    private String name;
    @ColumnInfo(name = "Location")
    private String location;
    @ColumnInfo(name = "Email")
    private String email;
    @ColumnInfo(name = "Phone")
    private String phone;
    @ColumnInfo(name = "Company")
    private String company;

    public Profile(String picture_uri, String name, String location, String email, String phone, String company) {
        this.picture_uri = picture_uri;
        this.name = name;
        this.location = location;
        this.email = email;
        this.phone = phone;
        this.company = company;
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

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompany() {
        return company;
    }

    public void setPicture_uri(String picture_uri) {
        this.picture_uri = picture_uri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

package com.wonsang.madcampweek2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccountData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String token;
    private String email;
    public AccountData(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "\n id=>" + this.id  + ", token => " + this.token;
    }
}

package com.wonsang.madcampweek2;


import android.accounts.Account;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDataDao {
    @Query("SELECT * FROM AccountData")
    LiveData<List<AccountData>> getAll();

    @Query("SELECT COUNT(*) FROM accountdata")
    int getCount();

    @Query("SELECT * FROM AccountData LIMIT 1")
    AccountData findAccountDataLimitOne();


    @Insert
    void insert(AccountData AccountData);

    @Update
    void update(AccountData AccountData);

    @Delete
    void delete(AccountData AccountData);

    @Query("DELETE FROM AccountData")
    void deleteAll();
    @Query("SELECT * FROM AccountData")
    List<AccountData> readAll();
}

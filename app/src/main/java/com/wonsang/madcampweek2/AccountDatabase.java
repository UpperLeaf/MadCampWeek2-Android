package com.wonsang.madcampweek2;

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AccountData.class}, version = 1)
public abstract class AccountDatabase extends RoomDatabase {

    private static AccountDatabase INSTANCE;

    public abstract AccountDataDao AccountDataDao();


    public static AccountDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, AccountDatabase.class, "account-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

package com.darien.capicua.commons;

import android.app.Application;

import androidx.room.Room;

import com.darien.capicua.room.AppDatabase;

public class MyApplication extends Application {
    AppDatabase database;
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "users").build();
        instance = this;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
package com.darien.capicua.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.darien.capicua.room.daos.PictureDao;
import com.darien.capicua.room.entities.PictureEntity;

@Database(
        entities = {PictureEntity.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PictureDao userDao();
}

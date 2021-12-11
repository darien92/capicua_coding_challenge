package com.darien.capicua.room.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.darien.capicua.room.entities.PictureEntity;

import java.util.List;

@Dao
public interface PictureDao {
    @Query("SELECT * FROM pictures")
    List<PictureEntity> getAll();

    @Query("DELETE FROM pictures")
    void deleteAll();

    @Insert
    void insertAll(PictureEntity... users);

    @Query("SELECT * FROM pictures where album_id=:albumId")
    List<PictureEntity> getAlbum(int albumId);
}
package com.darien.capicua.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pictures")
public class PictureEntity {
    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "album_id")
    public int albumId;

    @ColumnInfo(name = "picture_id")
    public int pictureId;

    @ColumnInfo(name = "picture_url")
    public String pictureUrl;

    @ColumnInfo(name = "thumbnail_url")
    public String thumbnailUrl;

    @ColumnInfo(name = "title")
    public String title;

    public PictureEntity(int albumId, int pictureId, String pictureUrl, String thumbnailUrl, String title) {
        this.albumId = albumId;
        this.pictureId = pictureId;
        this.pictureUrl = pictureUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
    }
}

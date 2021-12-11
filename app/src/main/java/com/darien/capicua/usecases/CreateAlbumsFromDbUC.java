package com.darien.capicua.usecases;

import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.room.entities.PictureEntity;

import java.util.HashSet;
import java.util.List;

public class CreateAlbumsFromDbUC {
    public void createAlbumsFromDb(List<PictureEntity> pictures, HashSet<Integer> seenAlbums, List<AlbumModel> albums) {
        for (PictureEntity picture : pictures) {
            if (!seenAlbums.contains(picture.albumId)) {
                albums.add(new AlbumModel(picture.albumId, "Album " + picture.albumId, picture.thumbnailUrl + ".png"));
                seenAlbums.add(picture.albumId);
            } else {
                albums.get(albums.size() - 1).setAlbumImageUrl(picture.thumbnailUrl + ".png");
            }
        }
    }
}

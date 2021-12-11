package com.darien.capicua.usecases;

import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.retrofit.models.RequestResponseItem;

import java.util.HashSet;
import java.util.List;

public class CreateAlbumsFromRequestUC {
    public void createAlbumsFromRequestResponse(List<RequestResponseItem> items, HashSet<Integer> seenAlbums, List<AlbumModel> albums) {
        for (RequestResponseItem item : items) {
            if (!seenAlbums.contains(item.getAlbumId())) {
                albums.add(new AlbumModel(item.getAlbumId(), "Album " + item.getAlbumId(), item.getThumbnailUrl() + ".png"));
                seenAlbums.add(item.getAlbumId());
            } else {
                albums.get(albums.size() - 1).setAlbumImageUrl(item.getThumbnailUrl() + ".png");
            }
        }
    }
}

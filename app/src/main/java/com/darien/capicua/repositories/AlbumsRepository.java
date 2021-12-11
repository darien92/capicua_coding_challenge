package com.darien.capicua.repositories;

import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.retrofit.models.RequestResponseItem;
import com.darien.capicua.retrofit.service.RequestImagesService;
import com.darien.capicua.room.entities.PictureEntity;
import com.darien.capicua.room.services.AlbumsDatabaseService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlbumsRepository implements RequestImagesService.RetrofitServiceDelegate, AlbumsDatabaseService.AlbumsDatabaseServiceDelegate {
    RequestImagesService retrofitService;
    AlbumsDatabaseService databaseService;
    List<AlbumModel> albums;
    HashSet<Integer> seenAlbums;
    List<AlbumsRepositoryDelegate> listeners;

    public AlbumsRepository() {
        retrofitService = new RequestImagesService();
        databaseService = new AlbumsDatabaseService();
        retrofitService.registerListener(this);
        databaseService.registerListener(this);
        albums = new ArrayList<>();
        seenAlbums = new HashSet<>();
        listeners = new ArrayList<>();
    }

    private void createAlbumsFromRequestResponse(List<RequestResponseItem> items) {
        for (RequestResponseItem item : items) {
            if (!seenAlbums.contains(item.getAlbumId())) {
                albums.add(new AlbumModel(item.getAlbumId(), "Album " + item.getAlbumId(), item.getThumbnailUrl() + ".png"));
                seenAlbums.add(item.getAlbumId());
            } else {
                albums.get(albums.size() - 1).setAlbumImageUrl(item.getThumbnailUrl() + ".png");
            }
        }
    }

    private void createAlbumsFromDb(List<PictureEntity> pictures) {
        for (PictureEntity picture : pictures) {
            if (!seenAlbums.contains(picture.albumId)) {
                albums.add(new AlbumModel(picture.albumId, "Album " + picture.albumId, picture.thumbnailUrl + ".png"));
                seenAlbums.add(picture.albumId);
            } else {
                albums.get(albums.size() - 1).setAlbumImageUrl(picture.thumbnailUrl + ".png");
            }
        }
    }

    private void sendAlbums() {
        for (AlbumsRepositoryDelegate listener : listeners) {
            listener.onDataSynced(this, albums);
        }
    }

    public void requestPictures() {
        retrofitService.requestPhotos();
    }

    public void registerListener(AlbumsRepositoryDelegate listener) {
        listeners.add(listener);
    }

    public void unregisterListener(AlbumsRepositoryDelegate listener) {
        listeners.remove(listener);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        retrofitService.unregisterListener(this);
        databaseService.unregisterListener(this);
    }

    @Override
    public void onSuccess(RequestImagesService retrofitService, List<RequestResponseItem> items) {
        createAlbumsFromRequestResponse(items);
        List<PictureEntity> picturesToInsert = new ArrayList<>();
        for (RequestResponseItem item : items) {
            picturesToInsert.add(new PictureEntity(item.getAlbumId(), item.getId(), item.getUrl(), item.getThumbnailUrl()));
        }
        databaseService.deleteAll(picturesToInsert);
    }

    @Override
    public void onResponseError(RequestImagesService retrofitService) {
        databaseService.getAll();
    }

    @Override
    public void onCallError(RequestImagesService retrofitService) {
        databaseService.getAll();
    }

    @Override
    public void onDataInserted(AlbumsDatabaseService service) {
        sendAlbums();
    }

    @Override
    public void onDataDeleted(AlbumsDatabaseService service, List<PictureEntity> pictures) {
        databaseService.insertInDB(pictures);
    }

    @Override
    public void onDataFetched(AlbumsDatabaseService service, List<PictureEntity> pictures) {
        createAlbumsFromDb(pictures);
        sendAlbums();
    }

    public interface AlbumsRepositoryDelegate {
        void onDataSynced(AlbumsRepository pictureRepository, List<AlbumModel> albums);
    }
}

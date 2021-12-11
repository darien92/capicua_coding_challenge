package com.darien.capicua.repositories;

import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.retrofit.models.RequestResponseItem;
import com.darien.capicua.retrofit.service.RequestImagesService;
import com.darien.capicua.room.entities.PictureEntity;
import com.darien.capicua.room.services.AlbumsDatabaseService;
import com.darien.capicua.usecases.CreateAlbumsFromDbUC;
import com.darien.capicua.usecases.CreateAlbumsFromRequestUC;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AlbumsRepository implements RequestImagesService.RetrofitServiceDelegate, AlbumsDatabaseService.AlbumsDatabaseServiceDelegate {
    private final RequestImagesService retrofitService;
    private final AlbumsDatabaseService databaseService;
    private final List<AlbumModel> albums;
    private final HashSet<Integer> seenAlbums;
    private final List<AlbumsRepositoryDelegate> listeners;
    private final CreateAlbumsFromRequestUC createAlbumsFromRequestUC;
    private final CreateAlbumsFromDbUC createAlbumsFromDbUC;

    public AlbumsRepository() {
        retrofitService = new RequestImagesService();
        databaseService = new AlbumsDatabaseService();
        retrofitService.registerListener(this);
        databaseService.registerListener(this);
        albums = new ArrayList<>();
        seenAlbums = new HashSet<>();
        listeners = new ArrayList<>();
        createAlbumsFromRequestUC = new CreateAlbumsFromRequestUC();
        createAlbumsFromDbUC = new CreateAlbumsFromDbUC();
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
        createAlbumsFromRequestUC.createAlbumsFromRequestResponse(items, seenAlbums, albums);
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
        createAlbumsFromDbUC.createAlbumsFromDb(pictures, seenAlbums, albums);
        sendAlbums();
    }

    public interface AlbumsRepositoryDelegate {
        void onDataSynced(AlbumsRepository pictureRepository, List<AlbumModel> albums);
    }
}

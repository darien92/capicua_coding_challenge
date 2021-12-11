package com.darien.capicua.repositories;

import com.darien.capicua.room.entities.PictureEntity;
import com.darien.capicua.room.services.AlbumDetailsService;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailsRepository implements AlbumDetailsService.AlbumDetailServiceListener {
    private final AlbumDetailsService service;
    private final List<AlbumDetailsRepositoryListener> listeners = new ArrayList<>();

    public AlbumDetailsRepository() {
        service = new AlbumDetailsService();
        service.registerListener(this);
    }

    private void sendPictures(List<PictureEntity> pictures) {
        for (AlbumDetailsRepositoryListener listener : listeners) {
            listener.onPicturesFetched(this, pictures);
        }
    }

    public void fetchImagesFroAlbum(int albumId) {
        service.getPicturesInAlbum(albumId);
    }

    public void registerListener(AlbumDetailsRepositoryListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(AlbumDetailsRepositoryListener listener) {
        listeners.remove(listener);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        service.unregisterListener(this);
    }

    @Override
    public void onPicturesFetched(AlbumDetailsService albumDetailsService, List<PictureEntity> pictures) {
        sendPictures(pictures);
    }

    public interface AlbumDetailsRepositoryListener {
        void onPicturesFetched(AlbumDetailsRepository albumDetailsRepository, List<PictureEntity> pictures);
    }
}

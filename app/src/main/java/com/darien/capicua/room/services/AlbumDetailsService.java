package com.darien.capicua.room.services;

import android.os.AsyncTask;

import com.darien.capicua.commons.MyApplication;
import com.darien.capicua.room.AppDatabase;
import com.darien.capicua.room.daos.PictureDao;
import com.darien.capicua.room.entities.PictureEntity;

import java.util.ArrayList;
import java.util.List;

public class AlbumDetailsService {
    private final PictureDao dao;
    List<AlbumDetailServiceListener> listeners = new ArrayList<>();

    public AlbumDetailsService() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        dao = appDatabase.userDao();
    }

    public void getPicturesInAlbum(int albumId) {
        AsyncTask.execute(() -> sendAlbumDetails(dao.getAlbum(albumId)));
    }

    private void sendAlbumDetails(List<PictureEntity> pictures) {
        for (AlbumDetailServiceListener listener : listeners) {
            listener.onPicturesFetched(this, pictures);
        }
    }

    public void registerListener(AlbumDetailServiceListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(AlbumDetailServiceListener listener) {
        listeners.remove(listener);
    }

    public interface AlbumDetailServiceListener {
        void onPicturesFetched(AlbumDetailsService albumDetailsService, List<PictureEntity> pictures);
    }
}

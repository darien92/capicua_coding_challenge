package com.darien.capicua.room.services;

import android.os.AsyncTask;

import com.darien.capicua.commons.MyApplication;
import com.darien.capicua.room.AppDatabase;
import com.darien.capicua.room.daos.PictureDao;
import com.darien.capicua.room.entities.PictureEntity;

import java.util.ArrayList;
import java.util.List;

public class AlbumsDatabaseService {
    private final PictureDao dao;
    private final List<AlbumsDatabaseServiceDelegate> listeners = new ArrayList<>();

    public AlbumsDatabaseService() {
        AppDatabase appDatabase = MyApplication.getInstance().getDatabase();
        dao = appDatabase.userDao();
    }

    public void insertInDB(List<PictureEntity> pictures) {
        for(PictureEntity picture : pictures) {
            dao.insertAll(picture);
        }
        sendDataInserted();
    }

    public void deleteAll(List<PictureEntity> pictures) {
        AsyncTask.execute(() -> {
            dao.deleteAll();
            sendDataDeleted(pictures);
        });
    }

    public void getAll() {
        AsyncTask.execute(() -> sendDataFetched(dao.getAll()));
    }

    private void sendDataInserted() {
        for (AlbumsDatabaseServiceDelegate listener: listeners) {
            listener.onDataInserted(this);
        }
    }

    private void sendDataDeleted(List<PictureEntity> pictures) {
        for (AlbumsDatabaseServiceDelegate listener: listeners) {
            listener.onDataDeleted(this, pictures);
        }
    }

    private void sendDataFetched(List<PictureEntity> pictures) {
        for (AlbumsDatabaseServiceDelegate listener : listeners) {
            listener.onDataFetched(this, pictures);
        }
    }

    public void registerListener(AlbumsDatabaseServiceDelegate listener) {
        listeners.add(listener);
    }

    public void unregisterListener(AlbumsDatabaseServiceDelegate listener) {
        listeners.remove(listener);
    }

    public interface AlbumsDatabaseServiceDelegate {
        void onDataInserted(AlbumsDatabaseService service);
        void onDataDeleted(AlbumsDatabaseService service, List<PictureEntity> pictures);
        void onDataFetched(AlbumsDatabaseService service, List<PictureEntity> pictures);
    }
}

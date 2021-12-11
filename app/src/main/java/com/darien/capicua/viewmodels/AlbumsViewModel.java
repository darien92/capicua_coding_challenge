package com.darien.capicua.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.darien.capicua.commons.Globals;
import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.repositories.AlbumsRepository;

import java.util.List;

public class AlbumsViewModel extends ViewModel implements AlbumsRepository.AlbumsRepositoryDelegate {
    private final AlbumsRepository repository;
    private final MutableLiveData<List<AlbumModel>> albumModels;

    public AlbumsViewModel() {
        repository = new AlbumsRepository();
        repository.registerListener(this);
        albumModels = new MutableLiveData<>();
    }

    public void requestAlbums() {
        if(!Globals.DATA_FETCHED) { //request pictures only once per launch
            Globals.DATA_FETCHED = true;
            repository.requestPictures();
        }
    }

    public LiveData<List<AlbumModel>> getAlbumModels() {
        return albumModels;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        repository.unregisterListener(this);
    }

    @Override
    public void onDataSynced(AlbumsRepository pictureRepository, List<AlbumModel> albums) {
        albumModels.postValue(albums);
    }
}
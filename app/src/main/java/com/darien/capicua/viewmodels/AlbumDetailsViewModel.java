package com.darien.capicua.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.darien.capicua.repositories.AlbumDetailsRepository;
import com.darien.capicua.room.entities.PictureEntity;

import java.util.List;

public class AlbumDetailsViewModel implements AlbumDetailsRepository.AlbumDetailsRepositoryListener {
    private final AlbumDetailsRepository repository;
    private final MutableLiveData<List<PictureEntity>> pictures;

    public LiveData<List<PictureEntity>> getPictures() {
        return pictures;
    }

    public AlbumDetailsViewModel() {
        repository = new AlbumDetailsRepository();
        repository.registerListener(this);
        pictures = new MutableLiveData<>();
    }

    public void fetchPictures(int albumId) {
        repository.fetchImagesFroAlbum(albumId);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        repository.unregisterListener(this);
    }

    @Override
    public void onPicturesFetched(AlbumDetailsRepository albumDetailsRepository, List<PictureEntity> pictures) {
        this.pictures.postValue(pictures);
    }
}

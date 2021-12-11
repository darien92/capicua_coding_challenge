package com.darien.capicua.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.darien.capicua.R;
import com.darien.capicua.commons.Constants;
import com.darien.capicua.models.AlbumModel;
import com.darien.capicua.viewmodels.AlbumsViewModel;
import com.darien.capicua.views.adapters.AlbumsAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumsAdapter.AlbumsAdapterListener {
    private AlbumsViewModel albumsViewModel;
    private ConstraintLayout loadingView, noAlbumsViews, albumsView;
    private RecyclerView rvAlbums;
    private ProgressBar pbLoading;
    private AlbumsAdapter albumsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }
    private void initData() {
        loadingView = findViewById(R.id.loading_albums_view);
        pbLoading = findViewById(R.id.pb_loading_albums);
        noAlbumsViews = findViewById(R.id.no_albums_view);
        albumsView = findViewById(R.id.albums_container_view);
        rvAlbums = findViewById(R.id.rv_albums);
        albumsViewModel = new ViewModelProvider(this).get(AlbumsViewModel.class);
        albumsViewModel.getAlbumModels().observe(this, this::processResponse);
        albumsViewModel.requestAlbums();
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        albumsAdapter = new AlbumsAdapter();
        albumsAdapter.setOnClickListener(this);
        rvAlbums.setLayoutManager(new GridLayoutManager(this, 3));
        rvAlbums.setAdapter(albumsAdapter);
    }

    private void hideLoading() {
        pbLoading.clearAnimation();
        loadingView.setVisibility(View.GONE);
    }

    private void showNoAlbums() {
        albumsView.setVisibility(View.GONE);
        noAlbumsViews.setVisibility(View.VISIBLE);
    }

    private void processResponse(List<AlbumModel> albums) {
        hideLoading();
        if (albums.size() == 0) {
            showNoAlbums();
        } else {
            albumsAdapter.setData(albums);
        }
    }

    @Override
    public void onClick(AlbumsAdapter albumsAdapter, int albumId, String albumName) {
        Intent intent = new Intent(getBaseContext(), AlbumActivity.class);
        intent.putExtra(Constants.ALBUM_ID_KEY, albumId);
        intent.putExtra(Constants.ALBUM_NAME_KEY, albumName);
        startActivity(intent);
    }
}
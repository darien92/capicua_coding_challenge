package com.darien.capicua.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.darien.capicua.R;
import com.darien.capicua.commons.Constants;
import com.darien.capicua.room.entities.PictureEntity;
import com.darien.capicua.viewmodels.AlbumDetailsViewModel;

import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    ImageView imgBack;
    ConstraintLayout loadingContainer;
    ProgressBar pbLoading;
    RecyclerView rvPictures;
    TextView tvAlbumName;

    private AlbumDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initData(getIntent());
    }

    private void initData(Intent intent) {
        imgBack = findViewById(R.id.img_arrow_back_album_activity);
        loadingContainer = findViewById(R.id.loading_container_album_activity);
        pbLoading = findViewById(R.id.pb_loading_album_activity);
        rvPictures = findViewById(R.id.rv_album_activity);
        tvAlbumName = findViewById(R.id.tv_album_name_album_activity);
        imgBack.setOnClickListener(view -> finish());
        viewModel = new ViewModelProvider(this).get(AlbumDetailsViewModel.class);

        viewModel.getPictures().observe(this, this::receivePictures);
        tvAlbumName.setText(intent.getStringExtra(Constants.ALBUM_NAME_KEY));
        requestPictures(intent.getIntExtra(Constants.ALBUM_ID_KEY, 0));
    }

    private void requestPictures(int albumId) {
        viewModel.fetchPictures(albumId);
    }

    private void receivePictures(List<PictureEntity> pictures) {
        hideLoading();

        //TODO: handle pictures in RecyclerView
    }

    private void hideLoading() {
        pbLoading.clearAnimation();
        loadingContainer.setVisibility(View.GONE);
    }
}
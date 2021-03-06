package com.darien.capicua.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.darien.capicua.R;
import com.darien.capicua.commons.Constants;

public class ImageDetailsActivity extends AppCompatActivity {
    ImageView imgBack, imageToShow;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        imgBack = findViewById(R.id.img_back_details_activity);
        imageToShow = findViewById(R.id.img_image_details_activity);
        tvTitle = findViewById(R.id.tv_title_details_activity);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(Constants.IMAGE_URL_KEY);
        Glide.with(this).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageToShow);
        tvTitle.setText(intent.getStringExtra(Constants.IMAGE_NAME_KEY));
        imgBack.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
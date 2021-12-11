package com.darien.capicua.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.darien.capicua.R;
import com.darien.capicua.models.AlbumModel;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> {
    private final List<AlbumModel> albums = new ArrayList<>();

    public void setData(List<AlbumModel> albums) {
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bindData(albums.get(position).getAlbumImageUrl(), albums.get(position).getAlbumName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView imgAlbum;
        private final TextView tvAlbumTitle;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imgAlbum = itemView.findViewById(R.id.img_album);
            tvAlbumTitle = itemView.findViewById(R.id.tv_album_title);
        }

        public void bindData(String imgUrl, String title) {
            tvAlbumTitle.setText(title);
            Glide.with(view).load(imgUrl).into(imgAlbum);
        }
    }
}

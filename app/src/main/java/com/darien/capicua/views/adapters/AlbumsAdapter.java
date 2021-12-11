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
    private AlbumsAdapterListener listener;

    public void setOnClickListener(AlbumsAdapterListener listener) {
        this.listener = listener;
    }

    public void setData(List<AlbumModel> albums) {
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlbumViewHolder albumViewHolder = new AlbumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.album_view, parent, false));
        albumViewHolder.setOnClickListener((albumViewHolder1, albumId, albumName) -> {
            if(AlbumsAdapter.this.listener != null) {
                AlbumsAdapter.this.listener.onClick(AlbumsAdapter.this, albumId, albumName);
            }
        });
        return albumViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.bindData(albums.get(position).getAlbumImageUrl(), albums.get(position).getAlbumName(),
                albums.get(position).getAlbumId(), albums.get(position).getAlbumName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView imgAlbum;
        private final TextView tvAlbumTitle;

        private OnAlbumViewHolderClickListener listener;

        public void setOnClickListener(OnAlbumViewHolderClickListener listener) {
            this.listener = listener;
        }

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imgAlbum = itemView.findViewById(R.id.img_album);
            tvAlbumTitle = itemView.findViewById(R.id.tv_album_title);
        }

        public void bindData(String imgUrl, String title, int albumId, String albumName) {
            tvAlbumTitle.setText(title);
            Glide.with(view.getContext()).load(imgUrl).into(imgAlbum);
            imgAlbum.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClick(AlbumViewHolder.this, albumId, albumName);
                }
            });
        }
    }

    public interface OnAlbumViewHolderClickListener {
        void onClick(AlbumViewHolder albumViewHolder, int albumId, String albumName);
    }

    public interface AlbumsAdapterListener {
        void onClick(AlbumsAdapter albumsAdapter, int albumId, String albumName);
    }
}

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
import com.darien.capicua.room.entities.PictureEntity;

import java.util.ArrayList;
import java.util.List;

public class PictureInAlbumAdapter extends RecyclerView.Adapter<PictureInAlbumAdapter.PictureInAlbumViewHolder> {
    private final List<PictureEntity> pictures = new ArrayList<>();

    private PictureInAlbumAdapterListener listener;

    public void setListener(PictureInAlbumAdapterListener listener) {
        this.listener = listener;
    }

    public void setData(List<PictureEntity> albums) {
        this.pictures.addAll(albums);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PictureInAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PictureInAlbumViewHolder viewHolder = new PictureInAlbumViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.album_view, parent, false));
        viewHolder.setPictureInAlbumListener(new PictureInAlbumVHListener() {
            @Override
            public void onPictureSelected(PictureInAlbumViewHolder viewHolder, String imgUrl, String imgName) {
                if (listener != null) {
                    listener.onPictureSelected(PictureInAlbumAdapter.this, imgUrl, imgName);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureInAlbumViewHolder holder, int position) {
        holder.bindData(pictures.get(position).thumbnailUrl, pictures.get(position).pictureUrl, pictures.get(position).title);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    class PictureInAlbumViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ImageView imgAlbum;
        private final TextView tvAlbumTitle;
        private PictureInAlbumVHListener listener;

        public void setPictureInAlbumListener(PictureInAlbumVHListener listener) {
            this.listener = listener;
        }

        public PictureInAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            imgAlbum = itemView.findViewById(R.id.img_album);
            tvAlbumTitle = itemView.findViewById(R.id.tv_album_title);
        }

        public void bindData(String thumbnailUrl, String imgUrl, String title) {
            tvAlbumTitle.setText(title);
            Glide.with(view.getContext()).load(thumbnailUrl).into(imgAlbum);
            imgAlbum.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onPictureSelected(PictureInAlbumViewHolder.this, imgUrl, title);
                }
            });
        }
    }

    interface PictureInAlbumVHListener {
        void onPictureSelected(PictureInAlbumViewHolder viewHolder, String imgUrl, String imgName);
    }

    public interface PictureInAlbumAdapterListener {
        void onPictureSelected(PictureInAlbumAdapter adapter, String imgUrl, String imgName);
    }
}

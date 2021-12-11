package com.darien.capicua.models;

public class AlbumModel {
    private final int albumId;
    private final String albumName;
    private String albumImageUrl;

    public AlbumModel(int albumId, String albumName, String albumImageUrl) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumImageUrl = albumImageUrl;
    }

    public void setAlbumImageUrl(String albumImageUrl) {
        this.albumImageUrl = albumImageUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumImageUrl() {
        return albumImageUrl;
    }
}

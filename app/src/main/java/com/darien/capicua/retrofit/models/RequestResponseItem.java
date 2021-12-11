package com.darien.capicua.retrofit.models;

public class RequestResponseItem {
    private int albumId;
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;

    public Integer getAlbumId(){
        return albumId;
    }

    public Integer getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public String getThumbnailUrl(){
        return thumbnailUrl;
    }
}

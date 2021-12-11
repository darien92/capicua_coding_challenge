package com.darien.capicua.retrofit.api;

import com.darien.capicua.retrofit.models.RequestResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestImagesApi {
    @GET("/photos")
    Call<List<RequestResponseItem>> getPhotos();
}

package com.darien.capicua.retrofit.service;

import androidx.annotation.NonNull;

import com.darien.capicua.commons.Constants;
import com.darien.capicua.retrofit.api.RequestImagesApi;
import com.darien.capicua.retrofit.models.RequestResponseItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestImagesService {

    private final RequestImagesApi api;
    private final List<RetrofitServiceDelegate> listeners = new ArrayList<>();

    public RequestImagesService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RequestImagesApi.class);
    }

    public void requestPhotos() {
        Call<List<RequestResponseItem>> call = api.getPhotos();
        call.enqueue(new Callback<List<RequestResponseItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<RequestResponseItem>> call, @NonNull Response<List<RequestResponseItem>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    List<RequestResponseItem> items = response.body();
                    sendOnSuccess(items);
                    return;
                }
                //error
                sendOnResponseError();
            }

            @Override
            public void onFailure(@NonNull Call<List<RequestResponseItem>> call, @NonNull Throwable t) {
                sendOnCallError();
            }
        });
    }

    private void sendOnSuccess(List<RequestResponseItem> items) {
        for (RetrofitServiceDelegate listener : listeners) {
            listener.onSuccess(this, items);
        }
    }

    private void sendOnResponseError() {
        for (RetrofitServiceDelegate listener : listeners) {
            listener.onResponseError(this);
        }
    }

    private void sendOnCallError() {
        for (RetrofitServiceDelegate listener : listeners) {
            listener.onCallError(this);
        }
    }

    public void registerListener(RetrofitServiceDelegate listener) {
        listeners.add(listener);
    }

    public void unregisterListener(RetrofitServiceDelegate listener) {
        listeners.remove(listener);
    }

    public interface RetrofitServiceDelegate {
        void onSuccess(RequestImagesService retrofitService, List<RequestResponseItem> items);
        void onResponseError(RequestImagesService retrofitService);
        void onCallError(RequestImagesService retrofitService);
    }
}
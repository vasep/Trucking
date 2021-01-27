package com.example.trucking.HttpRequests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static final String ROOT_URL = "DummyURl.com";

    public RetroClient() {
    }

    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static JSONPlaceHolderApi getApiService() {
        return getRetroClient().create(JSONPlaceHolderApi.class);
    }
}

package com.dyckster.newsapp.data.network;

import com.dyckster.newsapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum RetrofitService {
    INSTANCE;

    private NewsApi newsApi;

    RetrofitService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        newsApi = retrofit.create(NewsApi.class);
    }

    public NewsApi getNewsApi() {
        return newsApi;
    }
}

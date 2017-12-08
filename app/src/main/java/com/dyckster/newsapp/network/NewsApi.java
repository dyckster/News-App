package com.dyckster.newsapp.network;

import android.support.annotation.IntDef;

import com.dyckster.newsapp.model.Category;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.model.Document;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @IntDef({ResponseCode.SUCCESS, ResponseCode.NOT_FOUND})
    @interface ResponseCode {
        int SUCCESS = 0;
        int NOT_FOUND = 14;
    }

    @GET("categories")
    Call<DataList<Category>> getCategories();

    @GET("categories/{id}/news")
    Call<DataList<Document>> getNews(@Path("id") long categoryId, @Query("page") int page);

    // TODO: 08.12.2017 Single document
}

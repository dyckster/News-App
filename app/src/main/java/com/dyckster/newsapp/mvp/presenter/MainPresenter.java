package com.dyckster.newsapp.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.model.Category;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.mvp.view.MainView;
import com.dyckster.newsapp.network.NewsApi;
import com.dyckster.newsapp.network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";

    public void loadCategories() {
        //check for database
        getViewState().switchLoader(true);
        RetrofitService.INSTANCE.getNewsApi().getCategories().enqueue(new Callback<DataList<Category>>() {
            @Override
            public void onResponse(@NonNull Call<DataList<Category>> call, @NonNull Response<DataList<Category>> response) {
                getViewState().switchLoader(false);
                if (!response.isSuccessful() || response.body() == null) {
                    getViewState().onCategoriesError();
                    return;
                }
                if (response.body().getCode() != NewsApi.ResponseCode.SUCCESS) {
                    getViewState().onCategoriesError();
                }
                if (response.body().getItems().isEmpty()) {
                    getViewState().onCategoriesError();
                }
                getViewState().onCategories(response.body().getItems());
            }

            @Override
            public void onFailure(Call<DataList<Category>> call, Throwable t) {
                getViewState().switchLoader(false);
                Log.e(TAG, "Failed to call:" + call.request().toString());
                getViewState().onCategoriesError();
            }
        });
    }


}

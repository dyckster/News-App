package com.dyckster.newsapp.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.data.db.NewsDatabase;
import com.dyckster.newsapp.data.network.NewsApi;
import com.dyckster.newsapp.data.network.RetrofitService;
import com.dyckster.newsapp.model.Category;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.mvp.view.MainView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private static final String TAG = "MainPresenter";

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadCategories(false);
    }

    private void loadCategories(boolean forced) {
        if (forced) {
            loadCategoriesFromServer();
        } else {
            loadCategoriesFromDatabase();
        }
    }

    private void loadCategoriesFromDatabase() {
        List<Category> categoryList = NewsDatabase.getInstance().categoriesDao().getAll();
        if (categoryList.isEmpty()) {
            Log.d(TAG, "Loading categories from server");
            loadCategoriesFromServer();
        } else {
            Log.d(TAG, "Loading categories from database");
            getViewState().onCategories(categoryList);
        }
    }

    private void loadCategoriesFromServer() {
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
                List<Category> categories = response.body().getItems();

                if (categories.isEmpty()) {
                    getViewState().onCategoriesError();
                }
                NewsDatabase.getInstance().categoriesDao().insertCategories(categories);
                getViewState().onCategories(categories);
            }

            @Override
            public void onFailure(Call<DataList<Category>> call, Throwable t) {
                getViewState().switchLoader(false);
                getViewState().onCategoriesError();
            }
        });
    }

}

package com.dyckster.newsapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.model.LoaderType;

import java.util.List;

public interface NewsListView extends MvpView {
    void onNews(List<Document> documents);

    void onEmptyNews();

    void onDataError();

    void switchLoader(LoaderType loaderType, boolean show);

}

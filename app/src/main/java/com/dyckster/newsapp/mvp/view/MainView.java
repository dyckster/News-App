package com.dyckster.newsapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.dyckster.newsapp.model.Category;

import java.util.List;

public interface MainView extends MvpView {

    void onCategories(List<Category> categories);

    void onCategoriesError();

    void switchLoader(boolean show);


}

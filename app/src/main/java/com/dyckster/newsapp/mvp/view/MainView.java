package com.dyckster.newsapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dyckster.newsapp.model.Category;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void onCategories(List<Category> categories);

    void onCategoriesError();

    void switchLoader(boolean show);


}

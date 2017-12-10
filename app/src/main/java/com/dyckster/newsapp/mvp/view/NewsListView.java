package com.dyckster.newsapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dyckster.newsapp.model.Document;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface NewsListView extends MvpView {

    void showNews(List<Document> news, boolean firstLoad);

    void showLoadingProgress();

    void hideLoadingProgress();

    void showLoadingMoreProgress();

    void hideLoadingMoreProgress();

    void openDetailsScreen(long documentId);

    void showNoNewsLayout();

    void showNoNetworkLayout();

    void hideNoNetworkLayout();

}

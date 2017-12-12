package com.dyckster.newsapp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.dyckster.newsapp.model.Document;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DocumentView extends MvpView {
    void onShortInfoLoaded(Document document);

    void onFullInfoLoaded(Document document);

    void showFullInfoLoader();

    void hideFullInfoLoader();

    void shareDocument(String shareInfo);
}

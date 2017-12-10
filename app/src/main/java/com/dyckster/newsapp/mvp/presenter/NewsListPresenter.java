package com.dyckster.newsapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.mvp.view.NewsListView;
import com.dyckster.newsapp.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    private static final int FIRST_PAGE = 0;
    private static final int PAGE_SIZE = 10;

    private int currentPage = FIRST_PAGE;
    private long categoryId;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private List<Document> news = new ArrayList<>();

    public void loadFirstPage(long categoryId) {
        clearState();
        this.categoryId = categoryId;
        isLoading = true;
        getViewState().showLoadingProgress();
        loadNextPage(FIRST_PAGE);
    }

    public void loadMoreNews() {
        if (isLoading) return;
        if (isLastPage) return;

        getViewState().showLoadingMoreProgress();
        loadNextPage(++currentPage);
    }

    private void loadNextPage(int page) {
        RetrofitService.INSTANCE.getNewsApi().getNews(categoryId, page)
                .enqueue(new Callback<DataList<Document>>() {
                    @Override
                    public void onResponse(Call<DataList<Document>> call, Response<DataList<Document>> response) {
                        if (response.isSuccessful()) {
                            onShotsLoaded(response.body().getItems());
                        } else {
                            // TODO: 09.12.2017
                        }
                    }

                    @Override
                    public void onFailure(Call<DataList<Document>> call, Throwable t) {
                        onShotsLoadedError(t);
                    }
                });
    }


    private void onShotsLoaded(List<Document> news) {
        isLoading = false;
        if (isFirstLoading()) {
            getViewState().hideLoadingProgress();
        } else {
            getViewState().hideLoadingMoreProgress();
        }

        if (news.isEmpty() && isFirstLoading()) {
            getViewState().showNoNewsLayout();
        } else if (news.isEmpty()) {
            isLastPage = true;
        } else {
            this.news.addAll(news);
            getViewState().showNews(news, isFirstLoading());
        }
    }

    private void onShotsLoadedError(Throwable throwable) {
        isLoading = false;
        // TODO: 10.12.2017
    }

    private boolean isFirstLoading() {
        return currentPage == FIRST_PAGE;
    }

    private void clearState() {
        news.clear();
        currentPage = FIRST_PAGE;
        isLoading = false;
        isLastPage = false;
    }

}

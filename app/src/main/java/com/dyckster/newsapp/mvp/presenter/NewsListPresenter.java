package com.dyckster.newsapp.mvp.presenter;

import android.support.annotation.Nullable;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.data.db.NewsDatabase;
import com.dyckster.newsapp.data.network.RetrofitService;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.mvp.view.NewsListView;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class NewsListPresenter extends MvpPresenter<NewsListView> {

    private static final int FIRST_PAGE = 0;

    private int currentPage = FIRST_PAGE;
    private long categoryId;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private boolean fromCache = false;

    public void loadNews(long categoryId, boolean forcedUpdate) {
        List<Document> news = NewsDatabase.getInstance().newsDao().getDocumentsByCategory(categoryId);
        if (news.isEmpty() || forcedUpdate) {
            loadFirstPage(categoryId);
        } else {
            fromCache = true;
            onNewsLoaded(news);
        }
    }

    private void loadFirstPage(long categoryId) {
        clearState();
        this.categoryId = categoryId;
        isLoading = true;
        getViewState().showLoadingProgress();
        loadNextPage(FIRST_PAGE);
    }

    public void loadMoreNews() {
        if (isLoading) return;
        if (isLastPage) return;
        if (fromCache) return;

        getViewState().showLoadingMoreProgress();
        loadNextPage(++currentPage);
    }

    private void loadNextPage(int page) {
        RetrofitService.INSTANCE.getNewsApi().getNews(categoryId, page)
                .enqueue(new Callback<DataList<Document>>() {
                    @Override
                    public void onResponse(Call<DataList<Document>> call, Response<DataList<Document>> response) {
                        if (response.isSuccessful()) {
                            onNewsLoaded(response.body().getItems());
                        } else {
                            onShotsLoadedError(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<DataList<Document>> call, Throwable t) {
                        onShotsLoadedError(t);
                    }
                });
    }


    private void onNewsLoaded(List<Document> news) {
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
            if (!fromCache) {
                Observable.fromIterable(news).forEach((document) -> document.setCategoryId(categoryId));
                NewsDatabase.getInstance().newsDao().insertDocuments(news);
            }
            getViewState().showNews(news, isFirstLoading());
        }
    }

    private void onShotsLoadedError(@Nullable Throwable throwable) {
        isLoading = false;
        getViewState().hideLoadingProgress();
    }

    private boolean isFirstLoading() {
        return currentPage == FIRST_PAGE;
    }

    private void clearState() {
        currentPage = FIRST_PAGE;
        isLoading = false;
        isLastPage = false;
    }

}

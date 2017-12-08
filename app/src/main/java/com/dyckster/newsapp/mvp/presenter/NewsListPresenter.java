package com.dyckster.newsapp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.model.DataList;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.model.LoaderType;
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
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private List<Document> news = new ArrayList<>();

    public void loadNews(long categoryId, int page) {
        isLoading = true;
        LoaderType loaderType = currentPage == FIRST_PAGE ? LoaderType.SWIPE : LoaderType.FOOTER;
        getViewState().switchLoader(loaderType, true);
        RetrofitService.INSTANCE.getNewsApi().getNews(categoryId, page)
                .enqueue(new Callback<DataList<Document>>() {
                    @Override
                    public void onResponse(Call<DataList<Document>> call, Response<DataList<Document>> response) {
                        getViewState().switchLoader(loaderType, false);
                        isLoading = false;
                        List<Document> moreNews = response.body().getItems();
                        if (moreNews.isEmpty()) {
                            isLastPage = true;
                        }
                        news.addAll(moreNews);
                        getViewState().onNews(news);
                    }

                    @Override
                    public void onFailure(Call<DataList<Document>> call, Throwable t) {
                        getViewState().switchLoader(loaderType, false);
                        isLoading = false;

                    }
                });
    }

    public void loadNextPage(long categoryId) {
        loadNews(categoryId, ++currentPage);
    }

    public void loadNews(long categoryId) {
        clearState();
        loadNews(categoryId, FIRST_PAGE);
    }

    private void clearState() {
        news.clear();
        currentPage = FIRST_PAGE;
        isLoading = false;
        isLastPage = false;
    }

    public boolean shouldLoadNextPage(int visibleItemCount, int firstVisibleItemPosition, int totalItemCount) {
        if (!isLoading && !isLastPage) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                return true;
            }
        }
        return false;
    }
}

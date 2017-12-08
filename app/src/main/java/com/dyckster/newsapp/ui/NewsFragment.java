package com.dyckster.newsapp.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.model.LoaderType;
import com.dyckster.newsapp.mvp.presenter.NewsListPresenter;
import com.dyckster.newsapp.mvp.view.NewsListView;
import com.dyckster.newsapp.ui.adapter.NewsAdapter;

import java.util.List;

public class NewsFragment extends MvpAppCompatFragment implements
        NewsListView {

    @InjectPresenter
    NewsListPresenter presenter;

    private static final String ARGUMENT_CATEGORY_ID = "category_id";

    public static NewsFragment newInstance(long categoryId) {
        Bundle args = new Bundle();
        args.putLong(ARGUMENT_CATEGORY_ID, categoryId);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private long categoryId;

    private RecyclerView newsRecycler;
    private SwipeRefreshLayout refreshLayout;

    private NewsAdapter newsAdapter = new NewsAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        categoryId = getArguments().getLong(ARGUMENT_CATEGORY_ID);
        initViews(v);
        presenter.loadNews(categoryId);
        return v;
    }

    private void initViews(View rootView) {
        newsRecycler = rootView.findViewById(R.id.news_recycler);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (presenter.shouldLoadNextPage(visibleItemCount, firstVisibleItemPosition, totalItemCount)) {
                    presenter.loadNextPage(categoryId);
                }
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.divider)));
        newsRecycler.addItemDecoration(dividerItemDecoration);
        newsRecycler.setAdapter(newsAdapter);

        refreshLayout = rootView.findViewById(R.id.news_refresh);
        refreshLayout.setOnRefreshListener(() -> presenter.loadNews(categoryId));
    }

    @Override
    public void onNews(List<Document> newsList) {
        newsAdapter.buildNewsList(newsList);
    }

    @Override
    public void onEmptyNews() {

    }

    @Override
    public void onDataError() {

    }

    @Override
    public void switchLoader(LoaderType loaderType, boolean show) {
        switch (loaderType) {
            case SWIPE:
                refreshLayout.setRefreshing(show);
                break;
            case FOOTER:
                newsAdapter.switchLoading(show);
                break;
        }
    }


}

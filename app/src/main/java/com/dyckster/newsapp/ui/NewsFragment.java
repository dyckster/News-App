package com.dyckster.newsapp.ui;

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
import com.dyckster.newsapp.mvp.presenter.NewsListPresenter;
import com.dyckster.newsapp.mvp.view.NewsListView;
import com.dyckster.newsapp.ui.adapters.NewsAdapter;
import com.dyckster.newsapp.util.EndlessRecyclerOnScrollListener;

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

    private SwipeRefreshLayout refreshLayout;
    private View noNetworkLayout;
    private View noDataLayout;

    private EndlessRecyclerOnScrollListener scrollListener;
    private NewsAdapter newsAdapter = new NewsAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        categoryId = getArguments().getLong(ARGUMENT_CATEGORY_ID);
        initViews(v);
        presenter.loadNews(categoryId, false);
        return v;
    }

    private void initViews(View rootView) {
        RecyclerView newsRecycler = rootView.findViewById(R.id.news_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        newsRecycler.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                presenter.loadMoreNews();
            }
        };
        newsRecycler.addOnScrollListener(scrollListener);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.divider)));
        newsRecycler.addItemDecoration(dividerItemDecoration);
        newsRecycler.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(this::openDetailsScreen);

        refreshLayout = rootView.findViewById(R.id.news_refresh);
        refreshLayout.setOnRefreshListener(this::refresh);

        noNetworkLayout = rootView.findViewById(R.id.news_network_layout);
        noNetworkLayout.setOnClickListener(view -> refresh());
        noDataLayout = rootView.findViewById(R.id.news_empty_layout);
        noDataLayout.setOnClickListener(view -> refresh());
    }

    private void refresh() {
        scrollListener.reset();
        presenter.loadNews(categoryId, true);
    }

    @Override
    public void showNews(List<Document> news, boolean firstLoad) {
        if (firstLoad) {
            newsAdapter.replaceItems(news);
        } else {
            newsAdapter.addItems(news);
        }
    }

    @Override
    public void showLoadingProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadingMoreProgress() {
        newsAdapter.setLoadingMore(true);
    }

    @Override
    public void hideLoadingMoreProgress() {
        newsAdapter.setLoadingMore(false);
    }

    @Override
    public void openDetailsScreen(long documentId) {
        DocumentActivity.start(getActivity(), documentId);
    }

    @Override
    public void showNoNewsLayout() {
        noDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoNetworkLayout() {
        noNetworkLayout.setVisibility(View.GONE);
    }

}

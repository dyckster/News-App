package com.dyckster.newsapp.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dyckster.newsapp.ui.adapters.viewholders.LoadMoreViewHolder;

public abstract class LoadMoreAdapter<T> extends FooterAdapter<T> {

    public LoadMoreAdapter(boolean withFooter) {
        super(withFooter);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new LoadMoreViewHolder(inflater, parent);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public void setLoadingMore(boolean isLoading) {
        showFooter(isLoading);
    }
}

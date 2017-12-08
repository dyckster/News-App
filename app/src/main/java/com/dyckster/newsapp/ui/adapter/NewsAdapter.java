package com.dyckster.newsapp.ui.adapter;


import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Document;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {

    private static final int ITEM_DOCUMENT = -1;
    private static final int ITEM_LOADER = -2;

    private List<Document> newsList = new ArrayList<>();

    private boolean isLoading;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_DOCUMENT:
                return new DocumentViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_document, parent, false));
            case ITEM_LOADER:
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_loader, parent, false)) {
                };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DocumentViewHolder) {
            ((DocumentViewHolder) holder).bind(newsList.get(position), position == 0);
        }

    }

    public void buildNewsList(List<Document> newsList) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return NewsAdapter.this.newsList.size();
            }

            @Override
            public int getNewListSize() {
                return newsList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return NewsAdapter.this.newsList.get(oldItemPosition).getId()
                        == newsList.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return areItemsTheSame(oldItemPosition, newItemPosition);
            }
        });
        this.newsList = newsList;
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading && position == newsList.size()) {
            return ITEM_LOADER;
        } else {
            return ITEM_DOCUMENT;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size() + (isLoading ? 1 : 0);
    }

    public void switchLoading(boolean show) {
        isLoading = show;
        if (isLoading) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }
}

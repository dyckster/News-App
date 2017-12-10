package com.dyckster.newsapp.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.ui.adapters.viewholders.DocumentViewHolder;
import com.dyckster.newsapp.util.CompareCallback;

import java.util.List;

public class NewsAdapter extends LoadMoreAdapter<Document> {

    public interface OnItemClickListener {
        void onNewsClick(long newsId);
    }

    private OnItemClickListener listener;

    public NewsAdapter() {
        super(false);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DocumentViewHolder) {
            ((DocumentViewHolder) holder).bind(getItem(position));
            ((DocumentViewHolder) holder).setOnNewsClickListener(listener);
        }
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new DocumentViewHolder(inflater, parent);
    }

    @Override
    protected CompareCallback<Document> getDiffUtilCallback(List<Document> oldList, List<Document> newList) {
        return new CompareCallback<Document>(oldList, newList) {
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return areItemsTheSame(oldItemPosition, newItemPosition);
            }
        };
    }
}

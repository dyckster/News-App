package com.dyckster.newsapp.ui.adapters;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dyckster.newsapp.util.CompareCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class FooterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = -1;

    private List<T> data;

    private boolean withFooter;

    public FooterAdapter(boolean withFooter) {
        data = new ArrayList<>();
        this.withFooter = withFooter;
    }

    public void showFooter(boolean show) {
        this.withFooter = show;
        notifyDataSetChanged();
    }

    public void addItems(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void replaceItems(List<T> data) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(getDiffUtilCallback(this.data, data));
        this.data = data;
        result.dispatchUpdatesTo(this);
    }


    public abstract RecyclerView.ViewHolder getItemViewHolder(LayoutInflater inflater, ViewGroup parent);

    public abstract RecyclerView.ViewHolder getFooterViewHolder(LayoutInflater inflater, ViewGroup parent);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_ITEM:
                return getItemViewHolder(inflater, parent);
            case TYPE_FOOTER:
                return getFooterViewHolder(inflater, parent);
        }
        throw new RuntimeException("ViewHolder not found for viewType: " + viewType);
    }

    @Override
    public int getItemCount() {
        int itemCount = data.size();
        if (withFooter) itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (withFooter && isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected CompareCallback<T> getDiffUtilCallback(List<T> oldList, List<T> newList) {
        throw new UnsupportedOperationException("Not implemented");
    }

    protected T getItem(int position) {
        return data.get(position);
    }
}

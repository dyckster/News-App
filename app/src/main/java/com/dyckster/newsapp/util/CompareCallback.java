package com.dyckster.newsapp.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import java.util.List;


public abstract class CompareCallback<T> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public CompareCallback(@NonNull List<T> oldList, @NonNull List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

}

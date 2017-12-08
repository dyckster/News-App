package com.dyckster.newsapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataList<T> {

    private int code;
    @SerializedName("list")
    private List<T> items;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}

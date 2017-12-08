package com.dyckster.newsapp.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    private long id;

    @SerializedName("name")
    private String categoryTitle;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}

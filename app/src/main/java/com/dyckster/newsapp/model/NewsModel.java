package com.dyckster.newsapp.model;

import com.google.gson.annotations.SerializedName;

public class NewsModel {
    private int code;
    @SerializedName("news")
    private Document document;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

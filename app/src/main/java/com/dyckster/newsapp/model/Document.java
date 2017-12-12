package com.dyckster.newsapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Date;

@Entity(foreignKeys =
@ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id")
        , indices = {@Index(value = "category_id")})
public class Document {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "short_description")
    private String shortDescription;
    @Nullable
    @ColumnInfo(name = "full_description")
    private String fullDescription;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "category_id")
    private long categoryId;

    //region Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }


    //endregion

    public boolean hasFullInfo() {
        return !TextUtils.isEmpty(fullDescription);
    }
}

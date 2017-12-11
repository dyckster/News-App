package com.dyckster.newsapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.dyckster.newsapp.model.Category;

import java.util.List;

@Dao
public interface CategoriesDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategories(List<Category> categories);
}

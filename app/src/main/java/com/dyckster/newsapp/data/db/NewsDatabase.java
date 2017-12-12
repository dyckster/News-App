package com.dyckster.newsapp.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.dyckster.newsapp.NewsApplication;
import com.dyckster.newsapp.model.Category;
import com.dyckster.newsapp.model.Document;

@Database(entities = {Document.class, Category.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase instance;

    private static final String DATABASE_NAME = "news-db";

    public static NewsDatabase getInstance() {
        if (instance == null) {
            synchronized (NewsDatabase.class) {
                instance = buildDatabase();
            }
        }
        return instance;
    }

    private static NewsDatabase buildDatabase() {
        return Room.databaseBuilder(NewsApplication.getInstance(), NewsDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }


    public abstract NewsDao newsDao();

    public abstract CategoriesDao categoriesDao();

}

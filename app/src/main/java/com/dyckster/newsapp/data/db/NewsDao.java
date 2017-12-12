package com.dyckster.newsapp.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dyckster.newsapp.model.Document;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * from document WHERE id = :documentId")
    Document getDocument(long documentId);

    @Query("SELECT * from document WHERE category_id = :categoryId ORDER BY date ASC ")
    List<Document> getDocumentsByCategory(long categoryId);

    @Update
    void updateDocument(Document document);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDocuments(List<Document> documents);
}

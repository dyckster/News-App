package com.dyckster.newsapp.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dyckster.newsapp.data.db.NewsDatabase;
import com.dyckster.newsapp.data.network.RetrofitService;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.model.NewsModel;
import com.dyckster.newsapp.mvp.view.DocumentView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class DocumentPresenter extends MvpPresenter<DocumentView> {

    private long documentId;
    private Document document;

    public void loadDocumentInfo(long documentId) {
        this.documentId = documentId;
        document = NewsDatabase.getInstance().newsDao().getDocument(documentId);
        if (document.hasFullInfo()) {
            getViewState().onFullInfoLoaded(document);
        } else {
            getViewState().onShortInfoLoaded(document);
            loadFullDocumentInfo();
        }
    }


    private void loadFullDocumentInfo() {
        getViewState().showFullInfoLoader();
        RetrofitService.INSTANCE.getNewsApi().getNewsDetails(documentId)
                .enqueue(new Callback<NewsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<NewsModel> call, @NonNull Response<NewsModel> response) {
                        if (response.isSuccessful()) {
                            NewsDatabase.getInstance().newsDao().updateDocument(response.body().getDocument());
                            document = response.body().getDocument();
                            getViewState().onFullInfoLoaded(document);
                            getViewState().hideFullInfoLoader();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NewsModel> call, @NonNull Throwable t) {
                        getViewState().hideFullInfoLoader();
                    }
                });
    }

    public void share() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(document.getTitle());
        stringBuilder.append("\n");
        stringBuilder.append(document.getShortDescription());
        stringBuilder.append("\n");
        stringBuilder.append("https://www.google.com");
        getViewState().shareDocument(stringBuilder.toString());
    }
}

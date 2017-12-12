package com.dyckster.newsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.mvp.presenter.DocumentPresenter;
import com.dyckster.newsapp.mvp.view.DocumentView;

public class DocumentActivity extends MvpAppCompatActivity implements
        DocumentView {

    private static final String ARGUMENT_DOCUMENT_ID = "document_id";
    private static final int NO_DOCUMENT = -1;

    @InjectPresenter
    DocumentPresenter presenter;

    public static void start(Context context, long documentId) {
        Intent starter = new Intent(context, DocumentActivity.class);
        starter.putExtra(ARGUMENT_DOCUMENT_ID, documentId);
        context.startActivity(starter);
    }

    private TextView documentTitle;
    private TextView shortDescription;
    private TextView fullDescription;

    private ProgressBar fullInfoProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        long documentId = getIntent().getLongExtra(ARGUMENT_DOCUMENT_ID, NO_DOCUMENT);
        if (documentId == NO_DOCUMENT) {
            throw new RuntimeException("Document id must be specified");
        }
        initViews();
        presenter.loadDocumentInfo(documentId);
    }

    private void initViews() {
        documentTitle = findViewById(R.id.document_title);
        shortDescription = findViewById(R.id.document_short_info);
        fullDescription = findViewById(R.id.document_full_info);
        fullInfoProgress = findViewById(R.id.document_progress);

        findViewById(R.id.document_back_button).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.document_share_button).setOnClickListener(view -> presenter.share());
    }

    @Override
    public void onShortInfoLoaded(Document document) {
        documentTitle.setText(document.getTitle());
        shortDescription.setText(document.getShortDescription());
    }

    @Override
    public void onFullInfoLoaded(Document document) {
        documentTitle.setText(document.getTitle());
        shortDescription.setText(document.getShortDescription());
        fullDescription.setText(Html.fromHtml(document.getFullDescription()));
    }

    @Override
    public void showFullInfoLoader() {
        fullInfoProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFullInfoLoader() {
        fullInfoProgress.setVisibility(View.GONE);

    }

    @Override
    public void shareDocument(String shareInfo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareInfo);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

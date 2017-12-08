package com.dyckster.newsapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Document;


public class DocumentViewHolder extends RecyclerView.ViewHolder {

    private TextView titleText;
    private TextView subtitleText;

    public DocumentViewHolder(View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.news_title);
        subtitleText = itemView.findViewById(R.id.news_subtitle);
    }

    public void bind(Document document) {
        bind(document, false);
    }

    public void bind(Document document, boolean isMain) {
        titleText.setText(document.getTitle());
        subtitleText.setText(document.getShortDescription());
    }
}

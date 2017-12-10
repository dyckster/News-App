package com.dyckster.newsapp.ui.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Document;
import com.dyckster.newsapp.ui.adapters.NewsAdapter;


public class DocumentViewHolder extends RecyclerView.ViewHolder {

    private TextView titleText;
    private TextView subtitleText;

    private NewsAdapter.OnItemClickListener listener;

    public DocumentViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_document, parent, false));
        titleText = itemView.findViewById(R.id.news_title);
        subtitleText = itemView.findViewById(R.id.news_subtitle);
    }


    public void bind(Document document) {
        titleText.setText(document.getTitle());
        subtitleText.setText(document.getShortDescription());
        if (listener != null) {
            listener.onNewsClick(document.getId());
        }
    }

    public void setOnNewsClickListener(NewsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

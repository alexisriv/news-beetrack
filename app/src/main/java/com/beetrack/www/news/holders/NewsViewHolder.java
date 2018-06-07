package com.beetrack.www.news.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beetrack.www.news.GlideApp;
import com.beetrack.www.news.R;
import com.beetrack.www.news.networking.models.Article;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView titleNewsTextView;
    private ImageView photoImageView;
    private TextView nameJournalTextView;

    public NewsViewHolder(View itemView) {
        super(itemView);
        this.titleNewsTextView = itemView.findViewById(R.id.titleNewsTextView);
        this.photoImageView = itemView.findViewById(R.id.photoImageView);
        this.nameJournalTextView = itemView.findViewById(R.id.nameJournalTextView);
    }

    public void build(Article article) {
        this.titleNewsTextView.setText(article.getTitle());
        GlideApp.with(itemView)
                .load(article.getUrlToImage())
                .centerCrop()
                .placeholder(R.drawable.img_news_beetrack)
                .error(R.drawable.img_not_found)
                .into(this.photoImageView);
        this.nameJournalTextView.setText(article.getSource().getName());
    }
}

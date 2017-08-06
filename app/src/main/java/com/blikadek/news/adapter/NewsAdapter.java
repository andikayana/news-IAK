package com.blikadek.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blikadek.news.R;
import com.blikadek.news.model.ArticlesItem;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by M13x5aY on 06/08/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    List<ArticlesItem> mNewsList;

    public NewsAdapter(List<ArticlesItem> mNewsList) {
        this.mNewsList = mNewsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        ArticlesItem news = mNewsList.get(position);
        holder.bind(news);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    //ViewHolder
    static class NewsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivNewsPhoto) ImageView ivNewsPhoto;
        @BindView(R.id.tvNewsTitle) TextView tvNewsTitle;
        @BindView(R.id.tvNewsDesc) TextView tvNewsDesc;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ArticlesItem newsitem) {
            tvNewsTitle.setText(newsitem.getTitle());
            tvNewsDesc.setText(newsitem.getDescription());
            Glide.with(ivNewsPhoto.getContext())
                    .load(newsitem.getUrlToImage())
                    .into(ivNewsPhoto);
        }
    }

}

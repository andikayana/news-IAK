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

    private List<ArticlesItem> articlesItemList;
    private NewsClickListener mNewsClickListener;

    public NewsAdapter(List<ArticlesItem> articlesItemList) {
        this.articlesItemList = articlesItemList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( NewsViewHolder holder, final int position) {
        holder.bind(articlesItemList.get(position));
        holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mNewsClickListener != null){
                     mNewsClickListener.onItemNewsClicked(
                             articlesItemList.get(position)
                     );
                 }
             }
        });
    }

    public void setItemClickListener(NewsClickListener clickListener) {
        if (clickListener != null) {
            mNewsClickListener = clickListener;
        }
    }

    @Override
    public int getItemCount() {
        return articlesItemList.size();
    }

    public void setData(List<ArticlesItem> datas){
        this.articlesItemList.clear();
        articlesItemList.addAll(datas);
        notifyDataSetChanged();
    }


    //ViewHolder untuk adapter
    static class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivNewsPhoto) ImageView ivNewsPhoto;
        @BindView(R.id.tvNewsTitle) TextView tvNewsTitle;
        @BindView(R.id.tvNewsDesc) TextView tvNewsDesc;
        @BindView(R.id.tvAuthor) TextView tvAuthor;
        @BindView(R.id.tvPublishedAt) TextView tvPublishedAt;
        @BindView(R.id.tvReadMOre) TextView tvReadMore;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ArticlesItem newsitem) {
            tvNewsTitle.setText(newsitem.getTitle());
            tvNewsDesc.setText(newsitem.getDescription());
            tvAuthor.setText(newsitem.getAuthor());
            tvPublishedAt.setText(newsitem.getPublishedAt());
            Glide.with(ivNewsPhoto.getContext())
                    .load(newsitem.getUrlToImage())
                    .into(ivNewsPhoto);

        }

    }
}

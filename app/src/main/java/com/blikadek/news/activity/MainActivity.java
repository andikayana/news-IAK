package com.blikadek.news.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blikadek.news.R;
import com.blikadek.news.adapter.NewsAdapter;
import com.blikadek.news.model.ArticlesItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

       mLinearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );

        mAdapter = new NewsAdapter(getDummyData());
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setAdapter(mAdapter);



    }

    private List<ArticlesItem> getDummyData() {
        List<ArticlesItem> dummyList = new ArrayList<>();

        for (int i=0; i < 10; i ++) {
            ArticlesItem dummyNews = new ArticlesItem();
            dummyNews.setTitle(String.valueOf(i)+ getString(R.string.dummy_news_title));
            dummyNews.setDescription(getString(R.string.dummy_news_desc));
            dummyNews.setUrlToImage("https://tctechcrunch2011.files.wordpress.com/2017/08/aug_chart_1.png?w=764&h=400&crop=1");

            dummyList.add(dummyNews);
        }
        return dummyList;
    }
}

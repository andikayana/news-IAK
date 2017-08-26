package com.blikadek.news.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.blikadek.news.R;
import com.blikadek.news.model.ArticlesItem;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.webView) WebView mWebView;
    private static final String KEY_EXTRA_NEWS="news";
    private ArticlesItem mAtriclesItem;

    public static void strat(Context context, String newJson){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_NEWS, newJson);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupWebView();

    }

    public void setupWebView(){
        mWebView.setWebViewClient(new WebViewClient());
        //mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setWebChromeClient(new WebChromeClient());

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            String newJson = getIntent().getStringExtra(KEY_EXTRA_NEWS);
            mAtriclesItem= new ArticlesItem().fromJson(newJson);
            mWebView.loadUrl(mAtriclesItem.getUrl());
            //Toast.makeText(this, "Show news " + mAtriclesItem.getTitle(), Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }

     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

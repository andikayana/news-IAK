package com.blikadek.news.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blikadek.news.R;
import com.blikadek.news.model.ArticlesItem;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.webView) WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private static final String KEY_EXTRA_NEWS="news";
    private ArticlesItem mAtriclesItem;

    public static void strat(Context context, ArticlesItem articlesItem){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_EXTRA_NEWS, articlesItem);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setWebChromeClient(new WebChromeClient());

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            mAtriclesItem= getIntent().getParcelableExtra(KEY_EXTRA_NEWS);
            setupWebView();
            mWebView.loadUrl(mAtriclesItem.getUrl());

            //setprogressbar
            progressBar.setMax(100);
            //Toast.makeText(this, "Show news " + mAtriclesItem.getTitle(), Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }


    }

    public void setupWebView(){
        //mWebView.setWebViewClient(new WebViewClient());
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.setHorizontalScrollBarEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "onProgres : " + String.valueOf(newProgress));
                progressBar.setProgress(newProgress );
                progressBar.setVisibility( newProgress == 100 ? View.GONE : View.VISIBLE);
               /* if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                } else{
                    progressBar.setVisibility(View.VISIBLE);
                }*/

                super.onProgressChanged(view, newProgress);
            }
        });

        WebSettings webSettings = mWebView.getSettings();

        //enable zoom
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);

        mWebView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

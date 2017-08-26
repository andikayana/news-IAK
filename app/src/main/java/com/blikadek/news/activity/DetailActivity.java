package com.blikadek.news.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blikadek.news.R;
import com.blikadek.news.model.ArticlesItem;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.webView) WebView mWebView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fabFavorite) FloatingActionButton fabFavorite;
    @BindView(R.id.nestedScrollView) NestedScrollView nestedScrollView;
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

        //mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.setWebChromeClient(new WebChromeClient());

        if (getIntent().hasExtra(KEY_EXTRA_NEWS)){
            mAtriclesItem= getIntent().getParcelableExtra(KEY_EXTRA_NEWS);
            setupWebView();
            mWebView.loadUrl(mAtriclesItem.getUrl());


            //setprogressbar
            progressBar.setMax(100);

            setupActionBAr();
            //Toast.makeText(this, "Show news " + mAtriclesItem.getTitle(), Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }


        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_action_name);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        getSupportActionBar().setTitle(mAtriclesItem.getTitle());
        getSupportActionBar().setSubtitle(mAtriclesItem.getUrl());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupActionBAr();
        setupFab();


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

        WebSettings webViewSettings = mWebView.getSettings();

        //enable zoom
        webViewSettings.setSupportZoom(true);
        webViewSettings.setBuiltInZoomControls(true);
        webViewSettings.setDisplayZoomControls(true);
        //enable javascript
        mWebView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupActionBAr(){
        setSupportActionBar(toolbar);
       ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            return;
        }

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_action_name);
        actionBar.setHomeAsUpIndicator(drawable);

        actionBar.setTitle(mAtriclesItem.getTitle());
        actionBar.setSubtitle(mAtriclesItem.getUrl());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


    }

    public void setupFab(){
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ( oldScrollY > scrollY ){
                    fabFavorite.show();
                } else {
                    fabFavorite.hide();
                }
            }
        });
    }
}

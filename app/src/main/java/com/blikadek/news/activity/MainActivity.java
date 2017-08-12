package com.blikadek.news.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blikadek.news.BuildConfig;
import com.blikadek.news.R;
import com.blikadek.news.adapter.NewsAdapter;
import com.blikadek.news.model.ApiResponse;
import com.blikadek.news.model.ArticlesItem;
import com.blikadek.news.rest.ApiClient;
import com.blikadek.news.rest.ApiService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecycleView;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mAdapter;
    private List<ArticlesItem> mListArticle = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();

    private final String NEWS_SOURCE="techcrunch";


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

        mAdapter = new NewsAdapter(mListArticle);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setAdapter(mAdapter);


        getData();


    }

    public void getData() {
        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ApiResponse> apiResponseCall = apiService.getTechCrunchArticle(
                NEWS_SOURCE,
                BuildConfig.API_KEY
        );

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null) {
                    mListArticle = apiResponse.getArticles();
                    mAdapter.setData(mListArticle);
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }


        });

    }

   /* public void getDataFromApi() {

        final String URL = "https://newsapi.org/v1/articles?source=techcrunch&apikey=8158a9d4ca6b4ac9bdd36e76ef426a1c";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);

                        try {
                            ApiResponse mApiResponse = gson.fromJson(response, ApiResponse.class);

                            mAdapter = new NewsAdapter(mApiResponse.getArticles());
                            mRecycleView.setLayoutManager(mLinearLayoutManager);
                            mRecycleView.setAdapter(mAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        queue.add(stringRequest);
    }*/

   /* private List<ArticlesItem> getDummyData() {
        List<ArticlesItem> dummyList = new ArrayList<>();

        for (int i=0; i < 10; i ++) {
            ArticlesItem dummyNews = new ArticlesItem();
            dummyNews.setTitle(String.valueOf(i)+ getString(R.string.dummy_news_title));
            dummyNews.setDescription(getString(R.string.dummy_news_desc));
            dummyNews.setUrlToImage("https://tctechcrunch2011.files.wordpress.com/2017/08/aug_chart_1.png?w=764&h=400&crop=1");

            dummyList.add(dummyNews);
        }
        return dummyList;
    }*/
}

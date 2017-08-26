package com.blikadek.news.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.blikadek.news.BuildConfig;
import com.blikadek.news.R;
import com.blikadek.news.adapter.NewsAdapter;
import com.blikadek.news.adapter.NewsClickListener;
import com.blikadek.news.model.ApiResponse;
import com.blikadek.news.model.ArticlesItem;
import com.blikadek.news.rest.ApiClient;
import com.blikadek.news.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsClickListener {


    private static final String TAG = MainActivity.class.getSimpleName();

    /*Recycler view perlu: VIewHolder, Adapter, LayoutManager */
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    NewsAdapter mAdapterDummy;
    NewsAdapter mAdapterApi;
    private List<ArticlesItem> mListArticle = new ArrayList<>();

    private final String NEWS_SOURCE="techcrunch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //SETUp Adapter
        mAdapterDummy = new NewsAdapter(GetDummyArticlesItem());
        mAdapterApi = new NewsAdapter(mListArticle);

        mAdapterApi.setItemClickListener(this);

        //SETUP RECYCLERVIEW
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //mRecyclerView.setAdapter(mAdapterDummy);
        mRecyclerView.setAdapter(mAdapterApi);

        getData();
    }

    private List<ArticlesItem> GetDummyArticlesItem(){
        List<ArticlesItem> result = new ArrayList<>();
        for(int i=0; i<10; i++){
            ArticlesItem item = new ArticlesItem();
            item.setTitle("Ini Merupakakan Title untuk menampilkan max line dari textview");
            item.setDescription("Ini merupakan deskripsi yang merupakan data random, yang bisa di copas. Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.Ini merupakan deskripsi yang merupakan data random, yang bisa di copas.");
            item.setUrlToImage("https://tctechcrunch2011.files.wordpress.com/2017/08/aug_chart_1.png?w=764&h=400&crop=1");
            result.add(item);
        }
        return result;
    }

    public void getData() {
        ApiService apiService = ApiClient.getRetrofitClient().create(ApiService.class);
        Call<ApiResponse> apiResponseCall = apiService.getTechCrunchArticle(
                NEWS_SOURCE,
                BuildConfig.API_KEY
        );

        Log.d(TAG, "getData: API_KEY " + BuildConfig.API_KEY);

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null) {
                    mListArticle = apiResponse.getArticles();
                    mAdapterApi.setData(mListArticle);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void onItemNewsClicked(ArticlesItem newsItem) {
        DetailActivity.strat(this, newsItem.toJson());
    }

}

package com.blikadek.news.rest;


import com.blikadek.news.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("articles")
     Call<ApiResponse> getTechCrunchArticle(
            @Query("source") String source,
            @Query("apiKey") String apiKey
    );
}

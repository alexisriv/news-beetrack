package com.beetrack.www.news.networking.interfaces;

import com.beetrack.www.news.networking.models.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INews {

    @GET("top-headlines")
    Call<Page> getTopNews(@Query("apiKey") String apiKey, @Query("country") String country);
}

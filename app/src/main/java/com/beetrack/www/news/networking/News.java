package com.beetrack.www.news.networking;

import android.util.Log;

import com.beetrack.www.news.BuildConfig;
import com.beetrack.www.news.networking.core.BaseService;
import com.beetrack.www.news.networking.interfaces.INews;
import com.beetrack.www.news.networking.models.Page;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends BaseService {

    public News() {
        super();
    }

    public void getNewsTop() {
        getService(INews.class).getTopNews(BuildConfig.API_KEY, "US").enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                if (response.isSuccessful())
                    EventBus.getDefault().post(response.body());
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.getMessage());
                t.printStackTrace();
            }
        });
    }
}

package com.beetrack.www.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beetrack.www.news.networking.News;
import com.beetrack.www.news.networking.models.Page;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private News news = new News();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.init();
    }

    private void init() {
        this.tabLayout = this.findViewById(R.id.tabLayout);
        this.viewPager = this.findViewById(R.id.newsViewPager);
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        NewsAdapter newsAdapter = new NewsAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(newsAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
    }

}

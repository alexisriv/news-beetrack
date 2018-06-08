package com.beetrack.www.news.mvp.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.beetrack.www.news.R;
import com.beetrack.www.news.adapters.NewsPagerAdapter;
import com.beetrack.www.news.mvp.interfaces.MvpMain;
import com.beetrack.www.news.mvp.presenters.MainPresenter;
import com.beetrack.www.news.networking.News;

public class MainActivity extends AppCompatActivity implements MvpMain.View {

    private MvpMain.Presenter presenter =  new MainPresenter();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private News news = new News();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.presenter.initView(this);
        this.init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.tabLayout = this.findViewById(R.id.tabLayout);
        this.viewPager = this.findViewById(R.id.newsViewPager);
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(newsPagerAdapter);
        this.tabLayout.setupWithViewPager(viewPager);
    }

}

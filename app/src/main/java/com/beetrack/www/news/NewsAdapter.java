package com.beetrack.www.news;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beetrack.www.news.fragments.ListNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends FragmentPagerAdapter {

    private static final int COUNT_FRAGMENT = 2;
    private static String[] titlesTab = {"Top","Like"};

    public NewsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ListNewsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return COUNT_FRAGMENT;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titlesTab[position];
    }
}

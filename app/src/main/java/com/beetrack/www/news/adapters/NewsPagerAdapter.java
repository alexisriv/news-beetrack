package com.beetrack.www.news.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beetrack.www.news.mvp.views.fragments.ListNewsFragment;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private static final int COUNT_FRAGMENT = 2;
    private static String[] titlesTab = {"Top","Like"};

    public NewsPagerAdapter(FragmentManager fm) {
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

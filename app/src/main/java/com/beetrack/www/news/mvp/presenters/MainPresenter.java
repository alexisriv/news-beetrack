package com.beetrack.www.news.mvp.presenters;

import com.beetrack.www.news.mvp.interfaces.MvpMain;

public class MainPresenter implements MvpMain.Presenter {

    private MvpMain.View view;

    @Override
    public void initView(MvpMain.View view) {
        this.view = view;
    }
}

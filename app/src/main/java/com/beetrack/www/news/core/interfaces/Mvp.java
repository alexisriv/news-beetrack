package com.beetrack.www.news.core.interfaces;

public interface Mvp {

    interface View {

    }

    interface Presenter<T> {
        void initView(T view);
    }
}

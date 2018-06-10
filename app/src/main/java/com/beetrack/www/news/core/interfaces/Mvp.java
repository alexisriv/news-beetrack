package com.beetrack.www.news.core.interfaces;

public interface Mvp {

    interface View {

    }

    interface Presenter<T> {

        /**
         * Method in charge of initializing the view in the presenter.
         * @param view
         */
        void initView(T view);
    }
}

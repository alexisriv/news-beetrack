package com.beetrack.www.news.mvp.interfaces;

import com.beetrack.www.news.core.interfaces.Mvp;

public interface MvpMain {

    interface View extends Mvp.View {

    }

    interface Presenter extends Mvp.Presenter<View> {

    }

}

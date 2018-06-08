package com.beetrack.www.news.mvp.interfaces;

import com.beetrack.www.news.core.interfaces.Mvp;
import com.beetrack.www.news.networking.models.Article;

public interface MvpNews {

    interface View extends Mvp.View {
        void progressDialog();

        void setTitleInToolbar(String title);

        void loadWebView(String url);

        void setThumb(boolean b);

        void toastShow(int id, int length);
    }

    interface Presenter extends Mvp.Presenter<View> {

        void initSessionDB();

        void setArticle(Article article);

        void toDisplay() throws Exception;

        void likeArticle();
    }
}

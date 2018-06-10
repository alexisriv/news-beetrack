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

        /**
         * Method in charge of initializing Dao sessions and database objects.
         */
        void initSessionDB();

        /**
         * Method in charge of initializing the article in the presenter.
         * @param article
         */
        void setArticle(Article article);

        /**
         * Method in charge of showing the initial behavior of components of the view.
         * @throws Exception
         */
        void toDisplay() throws Exception;

        /**
         * Method in charge of saving or deleting news.
         */
        void likeArticle();
    }
}

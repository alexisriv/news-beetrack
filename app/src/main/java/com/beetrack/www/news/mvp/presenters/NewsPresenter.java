package com.beetrack.www.news.mvp.presenters;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.beetrack.www.news.R;
import com.beetrack.www.news.core.AppNews;
import com.beetrack.www.news.mvp.interfaces.MvpNews;
import com.beetrack.www.news.models.ArticleDB;
import com.beetrack.www.news.models.ArticleDBDao;
import com.beetrack.www.news.models.DaoSession;
import com.beetrack.www.news.models.SourceDB;
import com.beetrack.www.news.models.SourceDBDao;
import com.beetrack.www.news.networking.models.Article;

public class NewsPresenter implements MvpNews.Presenter {

    private MvpNews.View view;

    private Article article;

    private DaoSession daoSession;
    private ArticleDBDao articleDBDao;
    private SourceDBDao sourceDBDao;

    @Override
    public void initView(MvpNews.View view) {
        this.view = view;
    }

    @Override
    public void initSessionDB() {
        this.daoSession = ((AppNews) ((Activity) view).getApplication()).getDaoSession();
        this.articleDBDao = daoSession.getArticleDBDao();
        this.sourceDBDao = daoSession.getSourceDBDao();
    }

    @Override
    public void toDisplay() throws Exception {
        if(article == null)
            throw new Exception("Article is null");

        this.view.setTitleInToolbar(article.getSource().getName());
        this.view.progressDialog();
        this.view.loadWebView(article.getUrl());
        this.view.setThumb(existsArticle());

    }

    @Override
    public void setArticle(Article article){
        this.article = article;
    }

    @Override
    public void likeArticle(){
        if (existsArticle()) {
            this.clearArticle();
            return;
        }

        this.saveArticle();
    }

    public boolean existsArticle(){
        return articleDBDao.queryBuilder().where(ArticleDBDao.Properties.Url.eq(article.getUrl())).build().list().size() > 0 ? true : false;
    }

    private void saveArticle() {
        SourceDB sourceDB = new SourceDB();
        sourceDB.setIdSource(this.article.getSource().getId());
        sourceDB.setName(this.article.getSource().getName());

        ArticleDB articleDB = new ArticleDB();
        articleDB.setAuthor(this.article.getAuthor());
        articleDB.setTitle(this.article.getTitle());
        articleDB.setDescription(this.article.getDescription());
        articleDB.setUrl(this.article.getUrl());
        articleDB.setUrlToImage(this.article.getUrlToImage());
        articleDB.setPublishedAt(this.article.getPublishedAt());

        try {
            this.articleDBDao.getDatabase().beginTransaction();

            this.sourceDBDao.save(sourceDB);
            articleDB.setSourceId(sourceDB.getId());
            articleDB.setSource(sourceDB);

            this.articleDBDao.save(articleDB);
            this.articleDBDao.getDatabase().setTransactionSuccessful();

            this.view.setThumb(true);
            this.view.toastShow(R.string.saved, Toast.LENGTH_SHORT);

        } catch (Exception e) {
            this.view.toastShow(R.string.error_when_saving, Toast.LENGTH_SHORT);
            Log.e(getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            articleDBDao.getDatabase().endTransaction();
        }
    }

    private void clearArticle() {
        ArticleDB articleDB = articleDBDao.queryBuilder().where(ArticleDBDao.Properties.Url.eq(article.getUrl())).build().unique();
        try {
            this.articleDBDao.getDatabase().beginTransaction();
            this.articleDBDao.delete(articleDB);
            this.sourceDBDao.delete(articleDB.getSource());
            this.articleDBDao.getDatabase().setTransactionSuccessful();

            this.view.toastShow(R.string.removed, Toast.LENGTH_SHORT);
            this.view.setThumb(false);

        } catch (Exception e) {
            this.view.toastShow(R.string.error_when_removing, Toast.LENGTH_SHORT);
            Log.e(getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            articleDBDao.getDatabase().endTransaction();
        }
    }
}

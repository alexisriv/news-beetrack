package com.beetrack.www.news;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.beetrack.www.news.models.ArticleDB;
import com.beetrack.www.news.models.ArticleDBDao;
import com.beetrack.www.news.models.DaoSession;
import com.beetrack.www.news.models.SourceDB;
import com.beetrack.www.news.models.SourceDBDao;
import com.beetrack.www.news.networking.models.Article;

public class NewsActivity extends AppCompatActivity {

    public static final String URL_NEWS = "url.news";

    private WebView webView;
    private ProgressDialog progressDialog;
    private FloatingActionButton fab;

    private Article article;
    private DaoSession daoSession;
    ArticleDBDao articleDBDao;
    SourceDBDao sourceDBDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = getIntent().getExtras();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        this.daoSession = ((AppNews) getApplication()).getDaoSession();

        if (bundle != null) {
            article = (Article) bundle.getSerializable(URL_NEWS);

            getSupportActionBar().setTitle(article.getSource().getName());
            progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.img_news_beetrack);
            progressDialog.setMessage(getResources().getString(R.string.msg_info_wait));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            progressDialog.show();
            webView = (WebView) findViewById(R.id.newsWebView);
            loadWebView(article.getUrl());
            articleDBDao = daoSession.getArticleDBDao();
            sourceDBDao = daoSession.getSourceDBDao();
            if (existsArticle())
                fab.setImageResource(R.drawable.img_thumb_down);
            else
                fab.setImageResource(R.drawable.img_thumb_up);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveArticle(article);
                }
            });
        } else {
            Toast.makeText(this, R.string.msg_warning_no_data, Toast.LENGTH_SHORT).show();
            fab.setVisibility(View.GONE);
        }
    }


    public void loadWebView(String url) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveArticle(Article article) {
        if (existsArticle()) {
            this.clearArticle();
            return;
        }

        SourceDB sourceDB = new SourceDB();
        sourceDB.setIdSource(article.getSource().getId());
        sourceDB.setName(article.getSource().getName());

        ArticleDB articleDB = new ArticleDB();
        articleDB.setAuthor(article.getAuthor());
        articleDB.setTitle(article.getTitle());
        articleDB.setDescription(article.getDescription());
        articleDB.setUrl(article.getUrl());
        articleDB.setUrlToImage(article.getUrlToImage());
        articleDB.setPublishedAt(article.getPublishedAt());

        try {
            articleDBDao.getDatabase().beginTransaction();

            sourceDBDao.save(sourceDB);
            articleDB.setSourceId(sourceDB.getId());
            articleDB.setSource(sourceDB);

            articleDBDao.save(articleDB);
            articleDBDao.getDatabase().setTransactionSuccessful();
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
            fab.setImageResource(R.drawable.img_thumb_down);
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_when_saving, Toast.LENGTH_SHORT).show();
            Log.e(getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            articleDBDao.getDatabase().endTransaction();
        }
    }

    private void clearArticle() {
        ArticleDB articleDB = articleDBDao.queryBuilder().where(ArticleDBDao.Properties.Url.eq(article.getUrl())).build().unique();
        try {
            articleDBDao.getDatabase().beginTransaction();


            articleDBDao.delete(articleDB);
            sourceDBDao.delete(articleDB.getSource());

            articleDBDao.getDatabase().setTransactionSuccessful();
            Toast.makeText(this, R.string.removed, Toast.LENGTH_SHORT).show();
            fab.setImageResource(R.drawable.img_thumb_up);
        } catch (Exception e) {
            Toast.makeText(this, R.string.error_when_removing, Toast.LENGTH_SHORT).show();
            Log.e(getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        } finally {
            articleDBDao.getDatabase().endTransaction();
        }
    }

    public boolean existsArticle() {
        return articleDBDao.queryBuilder().where(ArticleDBDao.Properties.Url.eq(article.getUrl())).build().list().size() > 0 ? true : false;
    }
}

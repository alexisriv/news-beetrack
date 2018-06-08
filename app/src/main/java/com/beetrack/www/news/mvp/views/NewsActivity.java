package com.beetrack.www.news.mvp.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.beetrack.www.news.R;
import com.beetrack.www.news.mvp.interfaces.MvpNews;
import com.beetrack.www.news.mvp.presenters.NewsPresenter;
import com.beetrack.www.news.networking.models.Article;

public class NewsActivity extends AppCompatActivity implements MvpNews.View {

    public static final String ARTICLE = "article.model";

    private MvpNews.Presenter newsPresenter = new NewsPresenter();

    private WebView webView;
    private ProgressDialog progressDialog;
    private FloatingActionButton fabThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Bundle bundle = getIntent().getExtras();
        this.newsPresenter.initView(this);
        this.newsPresenter.setArticle(bundle!=null?(Article) bundle.getSerializable(ARTICLE):null);
        this.newsPresenter.initSessionDB();
        this.init();
        try {
            this.newsPresenter.toDisplay();
        } catch (Exception e) {
            this.toastShow(R.string.msg_warning_no_data,  Toast.LENGTH_SHORT);
            fabThumb.setVisibility(View.GONE);
            e.printStackTrace();
        }

    }

    @Override
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
    public void setThumb(boolean b) {
        fabThumb.setImageResource(b?R.drawable.img_thumb_down:R.drawable.img_thumb_up);
    }

    @Override
    public void setTitleInToolbar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.img_news_beetrack);
        progressDialog.setMessage(getResources().getString(R.string.msg_info_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }


    @Override
    public void toastShow(int id, int length) {
        Toast.makeText(this, id, length).show();
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


    private void init() {
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.fabThumb = this.findViewById(R.id.fab);
        this.webView = this.findViewById(R.id.newsWebView);
    }

    public void onClickFabThumb(View view) {
        this.newsPresenter.likeArticle();
    }
}

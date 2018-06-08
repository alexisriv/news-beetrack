package com.beetrack.www.news.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beetrack.www.news.core.AppNews;
import com.beetrack.www.news.mvp.views.NewsActivity;
import com.beetrack.www.news.R;
import com.beetrack.www.news.adapters.NewsRecyclerAdapter;
import com.beetrack.www.news.holders.NewsViewHolder;
import com.beetrack.www.news.models.ArticleDB;
import com.beetrack.www.news.models.ArticleDBDao;
import com.beetrack.www.news.models.DaoSession;
import com.beetrack.www.news.networking.News;
import com.beetrack.www.news.networking.models.Article;
import com.beetrack.www.news.networking.models.Page;
import com.beetrack.www.news.networking.models.Source;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ListNewsFragment extends Fragment {

    private static final String TYPE_NEWS = "type.news";
    private static final int TYPE_NEWS_TOP = 0;
    private static final int TYPE_NEWS_LIKE = 1;

    private RecyclerView listNewsRecyclerView;
    private NewsRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;

    private News news = new News();
    private int typeNews;
    private DaoSession daoSession;

    public ListNewsFragment() {
        // Required empty public constructor
    }

    public static ListNewsFragment newInstance(int typeNews) {
        ListNewsFragment fragment = new ListNewsFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_NEWS, typeNews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            typeNews = getArguments().getInt(TYPE_NEWS, TYPE_NEWS_TOP);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (TYPE_NEWS_TOP == typeNews) {
            EventBus.getDefault().register(this);
            this.news.getNewsTop();
        } else {
            getArticlesDB();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsInUI(Page page) {
        this.adapter.setArticles(page.getArticles());
        this.adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickItemRecycler(NewsViewHolder.OnClickNews onClickNews){
        Intent intent = new Intent(getActivity().getApplicationContext(), NewsActivity.class);
        intent.putExtra(NewsActivity.ARTICLE, onClickNews.getArticle());
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_news, container, false);
        this.init(view);
        return view;
    }

    private void init(View view) {
        this.listNewsRecyclerView = view.findViewById(R.id.listNewsRecyclerView);
        this.listNewsRecyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(view.getContext());
        this.listNewsRecyclerView.setLayoutManager(this.layoutManager);

        this.adapter = new NewsRecyclerAdapter(new ArrayList<Article>());
        this.listNewsRecyclerView.setAdapter(this.adapter);
    }

    private void getArticlesDB() {
        this.daoSession = ((AppNews) getActivity().getApplication()).getDaoSession();
        ArticleDBDao articleDBDao = daoSession.getArticleDBDao();
        List<ArticleDB> articleDBS = articleDBDao.queryBuilder().orderDesc(ArticleDBDao.Properties.Id).build().list();
        List<Article> articles = new ArrayList<>();
        for (ArticleDB articleDB: articleDBS){
            articles.add(new Article(new Source(articleDB.getSource().getIdSource(), articleDB.getSource().getName()), articleDB.getAuthor(), articleDB.getTitle(), articleDB.getDescription(), articleDB.getUrl(), articleDB.getUrlToImage(), articleDB.getPublishedAt()));
        }
        adapter.setArticles(articles);
        adapter.notifyDataSetChanged();
    }


}

package com.beetrack.www.news.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beetrack.www.news.R;
import com.beetrack.www.news.networking.models.Page;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ListNewsFragment extends Fragment {

    private static final String TYPE_NEWS = "type.news";
    private static final int TYPE_NEWS_TOP = 0;
    private static final int TYPE_NEWS_LIKE = 1;

    private TextView typeFragmentTextView;

    private int typeNews;


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
        if (TYPE_NEWS_TOP == typeNews)
            EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsInUI(Page page) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_news, container, false);

        this.typeFragmentTextView = view.findViewById(R.id.typeFragmentTextView);
        switch (typeNews) {
            case TYPE_NEWS_TOP:
                this.typeFragmentTextView.setText("TOP");
                break;
            case TYPE_NEWS_LIKE:
                this.typeFragmentTextView.setText("LIKE");
                break;
        }
        return view;
    }
}

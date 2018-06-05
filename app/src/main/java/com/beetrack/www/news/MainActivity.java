package com.beetrack.www.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.beetrack.www.news.networking.News;
import com.beetrack.www.news.networking.models.Page;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private News news = new News();

    TextView helloTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloTextView = findViewById(R.id.helloTextView);
        helloTextView.setText("Loading...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        news.getNewsTop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsInUI(Page page) {
        helloTextView.setText(page.getStatus());
    }
}

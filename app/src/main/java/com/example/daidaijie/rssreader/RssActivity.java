package com.example.daidaijie.rssreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.daidaijie.rssreader.adapter.RssAdapter;
import com.example.daidaijie.rssreader.model.RssModel;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RssActivity extends BaseActivity {

    public static final String TAG = "RssActivity";

    private static final String EXTRA_URL = "RssActivity.URL";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rssItemRecyclerView)
    RecyclerView mRssItemRecyclerView;
    @BindView(R.id.rssRefreshLayout)
    SwipeRefreshLayout mRssRefreshLayout;

    String uri = "";
    RssAdapter mRssAdapter;

    RSSFeed mRSSFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uri = getIntent().getStringExtra(EXTRA_URL);

        mRssAdapter = new RssAdapter(this, null);
        mRssItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRssItemRecyclerView.setAdapter(mRssAdapter);

        mRssRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRss();
            }
        });

        mRssRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRssRefreshLayout.setRefreshing(true);
                getRss();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rss;
    }

    private void getRss() {
        Observable.just(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, RSSFeed>() {
                    @Override
                    public RSSFeed call(String s) {
                        RSSReader rssReader = new RSSReader();
                        try {
                            return rssReader.load(s);
                        } catch (RSSReaderException e) {
                            Log.e(TAG, "call: " + e.getMessage());
                            return null;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RSSFeed>() {
                    @Override
                    public void onCompleted() {
                        mRssRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRssRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(RSSFeed rssFeed) {
                        mRSSFeed = rssFeed;
                        if (mRSSFeed != null) {
                            mRssAdapter.setRSSItems(mRSSFeed.getItems());
                            RssModel.getInstance().mRSSItems = mRSSFeed.getItems();
                            mRssAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public static Intent getIntent(Context context, String url) {
        Intent intent = new Intent(context, RssActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

}

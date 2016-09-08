package com.example.daidaijie.rssreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.daidaijie.rssreader.bean.SimpleRssItem;

import org.mcsoxford.rss.RSSItem;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class FavDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.titleTextView)
    TextView mTitleTextView;
    @BindView(R.id.timeTextView)
    TextView mTimeTextView;
    @BindView(R.id.descTextView)
    TextView mDescTextView;
    @BindView(R.id.linkWebView)
    WebView mLinkWebView;

    SimpleRssItem mRSSItem;

    private static final String EXTRA_RSS_ITEM = "com.example.daidaijie.rssreader.RssDetailActivity" +
            ".SimpleRssItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRSSItem = (SimpleRssItem) getIntent().getSerializableExtra(EXTRA_RSS_ITEM);

        mTitleTextView.setText(mRSSItem.getTilte().trim());
        mDescTextView.setText(mRSSItem.getDesc());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");

        mTimeTextView.setText(formatter.format(mRSSItem.getPubDate()));

        WebSettings ws = mLinkWebView.getSettings();
//        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(true);
        ws.setDisplayZoomControls(true);

        mLinkWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mLinkWebView.loadUrl(mRSSItem.getLink().toString());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fav_detail;
    }

    public static Intent getIntent(Context context, SimpleRssItem simpleRssItem) {
        Intent intent = new Intent(context, FavDetailActivity.class);
        intent.putExtra(EXTRA_RSS_ITEM, simpleRssItem);
        return intent;
    }
}

package com.example.daidaijie.rssreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.daidaijie.rssreader.bean.SimpleRssItem;
import com.example.daidaijie.rssreader.model.MFavModel;
import com.example.daidaijie.rssreader.model.RssModel;
import com.example.daidaijie.rssreader.util.SnackbarUtil;

import org.mcsoxford.rss.RSSItem;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class RssDetailActivity extends BaseActivity {

    int mRSSItemPosition;

    RSSItem mRSSItem;

    private static final String EXTRA_RSS_ITEM_POSITION = "com.example.daidaijie.rssreader.RssDetailActivity" +
            ".mRSSItemPosition";
    public static final String TAG = "RssDetailActivity";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        mRSSItem = RssModel.getInstance().mRSSItems.get(getIntent().getIntExtra(EXTRA_RSS_ITEM_POSITION, 0));

        mTitleTextView.setText(mRSSItem.getTitle().trim());
        mDescTextView.setText(mRSSItem.getDescription());

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
        return R.layout.activity_rss_detail;
    }

    public static Intent getIntent(Context context, int position) {
        Intent intent = new Intent(context, RssDetailActivity.class);
        intent.putExtra(EXTRA_RSS_ITEM_POSITION, position);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ment_rss, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_fav) {
            MFavModel.getInstance().getRSSItems().add(0, new SimpleRssItem(mRSSItem));
            MFavModel.getInstance().save();
            SnackbarUtil.ShortSnackbar(mTitleTextView, "收藏成功", SnackbarUtil.Confirm).show();
        }

        return super.onOptionsItemSelected(item);
    }
}

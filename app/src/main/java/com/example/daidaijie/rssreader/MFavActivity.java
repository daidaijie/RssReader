package com.example.daidaijie.rssreader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.daidaijie.rssreader.adapter.FavRssAdapter;
import com.example.daidaijie.rssreader.adapter.RssAdapter;
import com.example.daidaijie.rssreader.bean.RssSource;
import com.example.daidaijie.rssreader.bean.SimpleRssItem;
import com.example.daidaijie.rssreader.model.MFavModel;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;

import java.util.List;

import butterknife.BindView;

public class MFavActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rssItemRecyclerView)
    RecyclerView mRssItemRecyclerView;

    FavRssAdapter mFavRssAdapter;

    List<SimpleRssItem> mRSSItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRSSItems = MFavModel.getInstance().getRSSItems();

        mFavRssAdapter = new FavRssAdapter(this, mRSSItems);
        mRssItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRssItemRecyclerView.setAdapter(mFavRssAdapter);

        mFavRssAdapter.setOnLongClickListener(new FavRssAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                String[] items = new String[]{"删除"};
                AlertDialog alertDialog = new AlertDialog.Builder(MFavActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MFavModel.getInstance().getRSSItems().remove(which);
                                MFavModel.getInstance().save();
                                mFavRssAdapter.setRSSItems(mRSSItems);
                                mFavRssAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mfav;
    }
}

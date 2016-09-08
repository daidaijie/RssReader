package com.example.daidaijie.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.daidaijie.rssreader.adapter.MyRssListAdapter;
import com.example.daidaijie.rssreader.adapter.RssListAdapter;
import com.example.daidaijie.rssreader.bean.HttpResult;
import com.example.daidaijie.rssreader.bean.RssSource;
import com.example.daidaijie.rssreader.model.MFavModel;
import com.example.daidaijie.rssreader.model.UserLogin;
import com.example.daidaijie.rssreader.model.UserModel;
import com.example.daidaijie.rssreader.service.RssItemService;
import com.example.daidaijie.rssreader.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyRssListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rssItemRecyclerView)
    RecyclerView mRssItemRecyclerView;
    @BindView(R.id.rssRefreshLayout)
    SwipeRefreshLayout mRssRefreshLayout;

    private MyRssListAdapter mMyRssListAdapter;

    private List<RssSource> mRssSources;

    public static final String TAG = "MyRssListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);

        mRssSources = new ArrayList<>();

        mMyRssListAdapter = new MyRssListAdapter(this, mRssSources);
        mRssItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRssItemRecyclerView.setAdapter(mMyRssListAdapter);

        mRssRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRssList();
            }
        });

        mRssRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRssRefreshLayout.setRefreshing(true);
                getRssList();
            }
        });

    }

    private void getRssList() {
        RssItemService rssItemService = UserLogin.getInstance().mRetrofit.create(RssItemService.class);
        rssItemService.getRssSource(UserModel.getInstance().getUsername(), "false")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<RssSource>>>() {
                    @Override
                    public void onCompleted() {
                        mMyRssListAdapter.setRssSources(mRssSources);
                        mMyRssListAdapter.notifyDataSetChanged();
                        mRssRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        SnackbarUtil.ShortSnackbar(
                                mRssItemRecyclerView, e.getMessage(), SnackbarUtil.Alert
                        ).show();
                        mRssRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(HttpResult<List<RssSource>> listHttpResult) {
                        Log.e(TAG, "onNext: " + listHttpResult.getData().size());
                        Log.e(TAG, "onNext: " + listHttpResult.getCode());
                        if (listHttpResult.getCode() == 200) {
                            mRssSources = listHttpResult.getData();
                        } else {
                            SnackbarUtil.ShortSnackbar(
                                    mRssItemRecyclerView, listHttpResult.getMessage(), SnackbarUtil.Alert
                            ).show();
                        }
                    }
                });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_rss_list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ment_my_rss, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_to_fav) {
            Intent intent = new Intent(this, MFavActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_manager) {
            Intent intent = new Intent(this, RssListActivity.class);
            startActivityForResult(intent, 200);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == 200) {
            mRssRefreshLayout.setRefreshing(true);
            getRssList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

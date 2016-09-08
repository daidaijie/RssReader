package com.example.daidaijie.rssreader;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.daidaijie.rssreader.adapter.RssAdapter;
import com.example.daidaijie.rssreader.adapter.RssListAdapter;
import com.example.daidaijie.rssreader.bean.HttpResult;
import com.example.daidaijie.rssreader.bean.RssSource;
import com.example.daidaijie.rssreader.model.UserLogin;
import com.example.daidaijie.rssreader.model.UserModel;
import com.example.daidaijie.rssreader.service.RegisterService;
import com.example.daidaijie.rssreader.service.RssItemService;
import com.example.daidaijie.rssreader.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RssListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rssItemRecyclerView)
    RecyclerView mRssItemRecyclerView;
    @BindView(R.id.rssRefreshLayout)
    SwipeRefreshLayout mRssRefreshLayout;

    RssListAdapter mRssListAdapter;

    private List<RssSource> mRssSources;

    private List<RssSource> mHasRssSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRssSources = new ArrayList<>();

        mRssListAdapter = new RssListAdapter(this, mRssSources);
        mRssItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRssItemRecyclerView.setAdapter(mRssListAdapter);

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
        rssItemService.getRssSource(UserModel.getInstance().getUsername(), "true")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<RssSource>>>() {
                    @Override
                    public void onCompleted() {
                        mRssListAdapter.setRssSources(mRssSources);
                        mRssListAdapter.notifyDataSetChanged();
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
        return R.layout.activity_rss_list;
    }
}

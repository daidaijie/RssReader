package com.example.daidaijie.rssreader;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.daidaijie.rssreader.adapter.RssListAdapter;
import com.example.daidaijie.rssreader.bean.HttpResult;
import com.example.daidaijie.rssreader.bean.Login;
import com.example.daidaijie.rssreader.bean.RssSource;
import com.example.daidaijie.rssreader.model.UserLogin;
import com.example.daidaijie.rssreader.model.UserModel;
import com.example.daidaijie.rssreader.service.AddSubscribe;
import com.example.daidaijie.rssreader.service.DeleteSubscribe;
import com.example.daidaijie.rssreader.service.RssItemService;
import com.example.daidaijie.rssreader.util.LoadingDialogBuiler;
import com.example.daidaijie.rssreader.util.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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


    private AlertDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRssSources = new ArrayList<>();
        setResult(200);

        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(this, getResources().getColor(R.color.colorAccent));

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

        mRssListAdapter.setOnItemClickListner(new RssListAdapter.OnItemClickListner() {
            @Override
            public void onClick(int position) {
                if (mRssSources.get(position).getIs_order() == 0) {
                    add(position);
                } else {
                    delete(position);
                }
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

    private void add(int position) {
        mLoadingDialog.show();
        final RssSource rssSource = mRssSources.get(position);
        AddSubscribe addSubscribe = UserLogin.getInstance().mRetrofit.create(AddSubscribe.class);
        addSubscribe.add(UserModel.getInstance().getUsername(), rssSource.getType_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {
                        mLoadingDialog.dismiss();
                        SnackbarUtil.ShortSnackbar(
                                mRssItemRecyclerView, "添加成功", SnackbarUtil.Confirm
                        ).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingDialog.dismiss();
                        SnackbarUtil.ShortSnackbar(
                                mRssItemRecyclerView, e.getMessage(), SnackbarUtil.Alert
                        ).show();
                    }


                    @Override
                    public void onNext(Login login) {

                        if (login.getCode() == 200) {
                            rssSource.setIs_order(1);
                            mRssListAdapter.notifyDataSetChanged();
                        } else {
                            SnackbarUtil.ShortSnackbar(
                                    mRssItemRecyclerView, login.getMessage(), SnackbarUtil.Alert
                            ).show();
                        }
                    }
                });

    }

    private void delete(int position) {
        mLoadingDialog.show();
        final RssSource rssSource = mRssSources.get(position);
        DeleteSubscribe deleteSubscribe = UserLogin.getInstance().mRetrofit.create(DeleteSubscribe.class);
        deleteSubscribe.delete(UserModel.getInstance().getUsername(), rssSource.getType_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {
                        mLoadingDialog.dismiss();
                        SnackbarUtil.ShortSnackbar(
                                mRssItemRecyclerView, "删除成功", SnackbarUtil.Confirm
                        ).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingDialog.dismiss();
                        SnackbarUtil.ShortSnackbar(
                                mRssItemRecyclerView, e.getMessage(), SnackbarUtil.Alert
                        ).show();
                    }


                    @Override
                    public void onNext(Login login) {

                        if (login.getCode() == 200) {
                            rssSource.setIs_order(0);
                            mRssListAdapter.notifyDataSetChanged();
                        } else {
                            SnackbarUtil.ShortSnackbar(
                                    mRssItemRecyclerView, login.getMessage(), SnackbarUtil.Alert
                            ).show();
                        }
                    }
                });

    }
}

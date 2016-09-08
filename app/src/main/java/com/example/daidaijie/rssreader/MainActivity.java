package com.example.daidaijie.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.daidaijie.rssreader.bean.User;
import com.example.daidaijie.rssreader.model.UserLogin;
import com.example.daidaijie.rssreader.service.LoginService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class MainActivity extends BaseActivity {

    @BindView(R.id.login)
    MaterialLoginView mLogin;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogin.setListener(new MaterialLoginViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {

            }

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                Intent intent = new Intent(MainActivity.this, RssActivity.class);
                startActivity(intent);
                LoginService loginService = UserLogin.getInstance().mRetrofit.create(LoginService.class);
                loginService.login("test", "test")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                            }

                            @Override
                            public void onNext(User user) {
                                Log.e(TAG, "onNext: " + user.toString());
                            }
                        });


            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}

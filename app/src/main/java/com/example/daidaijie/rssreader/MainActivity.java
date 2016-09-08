package com.example.daidaijie.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.daidaijie.rssreader.bean.Login;
import com.example.daidaijie.rssreader.bean.Register;
import com.example.daidaijie.rssreader.model.UserLogin;
import com.example.daidaijie.rssreader.model.UserModel;
import com.example.daidaijie.rssreader.service.LoginService;
import com.example.daidaijie.rssreader.service.RegisterService;
import com.example.daidaijie.rssreader.util.LoadingDialogBuiler;
import com.example.daidaijie.rssreader.util.SnackbarUtil;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shem.com.materiallogin.MaterialLoginView;
import shem.com.materiallogin.MaterialLoginViewListener;

public class MainActivity extends BaseActivity {

    @BindView(R.id.login)
    MaterialLoginView mLogin;

    public static final String TAG = "MainActivity";

    private AlertDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mLoadingDialog = LoadingDialogBuiler.getLoadingDialog(this, getResources().getColor(R.color.colorAccent));


        mLogin.setListener(new MaterialLoginViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                mLoadingDialog.show();

                final String username = registerUser.getEditText().getText().toString();
                final String password = registerPass.getEditText().getText().toString();
                String rePassword = registerPassRep.getEditText().getText().toString();

                if (username.isEmpty()) {
                    SnackbarUtil.ShortSnackbar(mLogin, "用户名不能为空", SnackbarUtil.Warning).show();
                    return;
                } else if (password.isEmpty()) {
                    SnackbarUtil.ShortSnackbar(mLogin, "密码不能为空", SnackbarUtil.Warning).show();
                    return;
                } else if (rePassword.isEmpty()) {
                    SnackbarUtil.ShortSnackbar(mLogin, "再次密码不能为空", SnackbarUtil.Warning).show();
                    return;
                } else if (!password.equals(rePassword)) {
                    SnackbarUtil.ShortSnackbar(mLogin, "两次密码输入不一致", SnackbarUtil.Warning).show();
                    return;
                }


                RegisterService registerService = UserLogin.getInstance().mRetrofit.create(RegisterService.class);
                registerService.register(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Register>() {
                            @Override
                            public void onCompleted() {
                                UserModel.getInstance().setUsername(username);
                                UserModel.getInstance().setPassword(password);
                                mLoadingDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mLoadingDialog.dismiss();
                                Log.e(TAG, "onError: " + e.getMessage());
                            }

                            @Override
                            public void onNext(Register register) {
                                if (register.getCode() == 200) {
                                    SnackbarUtil.LongSnackbar(
                                            mLogin, register.getMessage(), SnackbarUtil.Confirm
                                    ).show();
                                } else {
                                    SnackbarUtil.LongSnackbar(
                                            mLogin, register.getMessage(), SnackbarUtil.Alert
                                    ).show();
                                }
                            }
                        });

            }

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                mLoadingDialog.show();

                String username = loginUser.getEditText().getText().toString();
                String password = loginPass.getEditText().getText().toString();
                Log.e(TAG, "onLogin: " + username);
                Log.e(TAG, "onLogin: " + password);

                LoginService loginService = UserLogin.getInstance().mRetrofit.create(LoginService.class);
                loginService.login(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Login>() {
                            @Override
                            public void onCompleted() {
                                mLoadingDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mLoadingDialog.dismiss();
                                Log.e(TAG, "onError: " + e.getMessage());
                            }

                            @Override
                            public void onNext(Login login) {

                                if (login.getCode() == 200) {
                                    Intent intent = new Intent(MainActivity.this, RssListActivity.class);
                                    startActivity(intent);
                                } else {
                                    SnackbarUtil.LongSnackbar(
                                            mLogin, login.getMessage(), SnackbarUtil.Alert
                                    ).show();
                                }
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

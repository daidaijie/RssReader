package com.example.daidaijie.rssreader.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daidaijie.rssreader.App;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daidaijie on 2016/9/7.
 */
public class UserLogin {

    public Retrofit mRetrofit;

    private static UserLogin ourInstance = new UserLogin();

    public static UserLogin getInstance() {
        return ourInstance;
    }

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;

    private String baseUrl;

    public static final String BASE_URL = "baseUrl";

    private UserLogin() {

        mSharedPreferences = App.getContext().getSharedPreferences("Fav", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        baseUrl = mSharedPreferences.getString(BASE_URL, "");
        if (baseUrl.isEmpty()) {
            baseUrl = "http://172.22.82.218:8080/Rss/";
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mEditor.putString(BASE_URL, baseUrl);
        mEditor.commit();
    }
}

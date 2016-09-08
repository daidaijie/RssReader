package com.example.daidaijie.rssreader.model;

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

    private UserLogin() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://172.22.82.218:8080/Rss/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}

package com.example.daidaijie.rssreader.service;

import com.example.daidaijie.rssreader.bean.Login;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by daidaijie on 2016/9/8.
 */
public interface DeleteSubscribe {

    @Headers({
            "Accept" + ": " + "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8   ",
            "Accept-Encoding" + ": " + "gzip, deflate, sdch",
            "Accept-Language" + ": " + "zh-CN,zh;q=0.8",
            "Cache-Control" + ": " + "max-age=0",
            "Connection" + ": " + "keep-alive",
            "Cookie" + ": " + "JSESSIONID=6F45F1EFAB123FD2BEAD47699CAA79B8",
            "Host" + ": " + "172.22.82.218:8080",
            "Upgrade-Insecure-Requests" + ": " + "1",
    })
    @GET("DeleteSubscribe")
    Observable<Login> delete(@Query("user_name") String username, @Query("type_id") int type_id);


}

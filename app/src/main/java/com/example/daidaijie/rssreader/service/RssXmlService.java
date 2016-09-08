package com.example.daidaijie.rssreader.service;

import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by daidaijie on 2016/9/7.
 */
public interface RssXmlService {

    @GET("{rss}")
    Observable<String> getRssXml(@Path("rss") String rss);
}

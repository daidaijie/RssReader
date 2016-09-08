package com.example.daidaijie.rssreader.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by daidaijie on 2016/7/22.
 */
public class GsonUtil {

    private static Gson mGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static Gson getDefault() {
        return mGson;
    }

}

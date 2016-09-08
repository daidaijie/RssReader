package com.example.daidaijie.rssreader.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daidaijie.rssreader.App;
import com.example.daidaijie.rssreader.bean.SimpleRssItem;
import com.example.daidaijie.rssreader.util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class MFavModel {

    private List<SimpleRssItem> mRSSItems;

    private static final String EXTRA_RSSITEM = "com.example.daidaijie.syllabusapplication.bean.Lesson" +
            ".MFavModel.mRSSItems";

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;

    private static MFavModel ourInstance = new MFavModel();

    public static MFavModel getInstance() {
        return ourInstance;
    }

    private MFavModel() {
        mSharedPreferences = App.getContext().getSharedPreferences("Fav", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        String gsonRssItems = mSharedPreferences.getString(EXTRA_RSSITEM, "");
        if (!gsonRssItems.trim().isEmpty()) {
            mRSSItems = GsonUtil.getDefault().fromJson(gsonRssItems, new TypeToken<List<SimpleRssItem>>() {
            }.getType());
        } else {
            mRSSItems = new ArrayList<>();
        }
    }

    public List<SimpleRssItem> getRSSItems() {
        return mRSSItems;
    }

    public void setRSSItems(List<SimpleRssItem> RSSItems) {
        mRSSItems = RSSItems;
        String jsonString = GsonUtil.getDefault().toJson(mRSSItems);
        mEditor.putString(EXTRA_RSSITEM, jsonString);
        mEditor.commit();
    }

    public void save() {
        String jsonString = GsonUtil.getDefault().toJson(mRSSItems);
        mEditor.putString(EXTRA_RSSITEM, jsonString);
        mEditor.commit();
    }

}

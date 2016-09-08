package com.example.daidaijie.rssreader.model;

import org.mcsoxford.rss.RSSItem;

import java.util.List;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class RssModel {

    public List<RSSItem> mRSSItems;

    private static RssModel ourInstance = new RssModel();

    public static RssModel getInstance() {
        return ourInstance;
    }

    private RssModel() {
    }
}

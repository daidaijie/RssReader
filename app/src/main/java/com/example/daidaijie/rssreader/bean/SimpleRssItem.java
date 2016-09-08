package com.example.daidaijie.rssreader.bean;

import org.mcsoxford.rss.RSSItem;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class SimpleRssItem implements Serializable {

    private String tilte;
    private String link;
    private String desc;
    private Date pubDate;


    public SimpleRssItem(RSSItem rssItem) {
        this.desc = rssItem.getDescription();
        this.tilte = rssItem.getTitle();
        this.link = rssItem.getLink().toString();
        this.pubDate = rssItem.getPubDate();
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}

package com.example.daidaijie.rssreader.bean;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class RssSource {

    /**
     * type_id : 8
     * type_name : 中国之队
     * url : http://rss.sina.com.cn/sports/china/team.xml
     */

    private int type_id;
    private String type_name;
    private String url;
    private int is_order;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_order() {
        return is_order;
    }

    public void setIs_order(int is_order) {
        this.is_order = is_order;
    }
}

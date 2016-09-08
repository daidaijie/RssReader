package com.example.daidaijie.rssreader.bean;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class HttpResult<T> {
    /**
     * code : 200
     * message : 恭喜你，登录成功！
     */

    private int code;
    private String message;
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

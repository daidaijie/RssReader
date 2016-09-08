package com.example.daidaijie.rssreader.bean;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class Login {

    /**
     * code : 200
     * message : 恭喜你，登录成功！
     */

    private int code;
    private String message;

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

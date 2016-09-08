package com.example.daidaijie.rssreader.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.daidaijie.rssreader.App;

/**
 * Created by daidaijie on 2016/9/8.
 */
public class UserModel {

    private String username;
    private String password;

    SharedPreferences mSharedPreferences;

    SharedPreferences.Editor mEditor;

    private static UserModel ourInstance = new UserModel();

    public static UserModel getInstance() {
        return ourInstance;
    }

    private UserModel() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

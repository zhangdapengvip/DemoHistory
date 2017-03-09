package com.http.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.http.BR;

/**
 * Created by ZeroAries on 2016/4/19.
 */
public class UserInfo extends BaseObservable {
    private String userName = "我是名字";
    private String nickName = "我是昵称";

    @Bindable
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
        notifyPropertyChanged(BR.nickName);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }
}

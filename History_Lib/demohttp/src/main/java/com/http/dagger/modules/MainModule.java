package com.http.dagger.modules;

import com.http.bean.UserManager;
import com.http.dagger.annotation.ScopeActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ZeroAries on 2016/4/12.
 */
@Module
public class MainModule {
    private String info;

    public MainModule(String info) {
        this.info = info;
    }

    @Provides
    public String providesGetInfo() {
        return this.info;
    }

    @Provides
    @ScopeActivity
    public UserManager providesUserManager() {
        return new UserManager();
    }
}

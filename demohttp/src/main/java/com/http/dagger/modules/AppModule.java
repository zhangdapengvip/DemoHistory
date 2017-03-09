package com.http.dagger.modules;

import android.app.Application;
import android.content.Context;

import com.http.AppApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ZeroAries on 2016/4/11.
 */
@Module
public class AppModule {
    private AppApplication application;

    public AppModule(AppApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application providesApplication() {
        return this.application;
    }
}

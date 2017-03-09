package com.http.dagger.component;

import android.app.Application;

import com.http.dagger.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ZeroAries on 2016/4/11.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    Application application();
}

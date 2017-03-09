package com.http.dagger.component;

import com.http.activity.MainActivity;
import com.http.dagger.annotation.ScopeActivity;
import com.http.dagger.modules.ActivityModule;
import com.http.dagger.modules.MainModule;

import dagger.Component;

/**
 * Created by ZeroAries on 2016/4/12.
 */
@ScopeActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, ActivityModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}

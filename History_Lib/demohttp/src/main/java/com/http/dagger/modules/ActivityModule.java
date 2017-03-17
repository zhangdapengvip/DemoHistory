package com.http.dagger.modules;

import android.app.Activity;
import android.content.Context;

import com.http.dagger.annotation.ScopeActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ZeroAries on 2016/4/12.
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ScopeActivity
    public Activity providesActivity() {
        return this.activity;
    }

    @Provides
    @ScopeActivity
    public Context providesContext() {
        return this.activity;
    }
}

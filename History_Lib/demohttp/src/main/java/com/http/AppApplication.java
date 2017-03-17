package com.http;

import android.app.Application;

import com.http.dagger.component.AppComponent;
import com.http.dagger.component.DaggerAppComponent;
import com.http.dagger.modules.AppModule;

/**
 * Created by ZeroAries on 2016/4/11.
 */
public class AppApplication extends Application {

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getComponent(){
        return mComponent;
    }
}

package com.http.activity;

import android.app.Activity;
import android.os.Bundle;

import com.http.AppApplication;
import com.http.dagger.component.AppComponent;

/**
 * Created by ZeroAries on 2016/4/12.
 */
public class DefaultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppComponent getApplicationComponent() {
        AppApplication application = (AppApplication) getApplication();
        return application.getComponent();
    }
}

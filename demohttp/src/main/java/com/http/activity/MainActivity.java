package com.http.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;

import com.http.R;
import com.http.bean.UserInfo;
import com.http.bean.UserManager;
import com.http.dagger.component.DaggerMainComponent;
import com.http.dagger.modules.ActivityModule;
import com.http.dagger.modules.MainModule;
import com.http.databinding.ActivityMainBinding;
import com.http.demo.DemoKotlin;
import com.http.demo.DemoRxJava;
import com.http.demo.RxOperator;
import com.http.factory.RetrofitFactory;
import com.http.imp.ProtocolImp;
import com.orhanobut.logger.Logger;

import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends DefaultActivity {
    @Bind(R.id.btn_one)
    TextView btnOne;
    @Bind(R.id.btn_two)
    TextView btnTwo;
    @Bind(R.id.btn_three)
    TextView btnThree;
    @Bind(R.id.btn_four)
    TextView btnFour;
    @Bind(R.id.btn_five)
    TextView btnFive;
    @Bind(R.id.btn_six)
    TextView btnSix;
    @Bind(R.id.btn_seven)
    TextView btnSeven;
    @Bind(R.id.btn_eight)
    TextView btnEight;
    @Inject
    Activity mContext;
    @Inject
    UserManager info;
    private UserInfo mUserInfo;
    private ActivityMainBinding mDataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDagger();
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mUserInfo = new UserInfo();
        mDataBinding.setInfo(mUserInfo);
        Random random = new Random(100);
        mDataBinding.btnTwo.setOnClickListener(v ->  mUserInfo.setUserName("随机数" + random.nextInt()));
        Bitmap bitmap = null;
        Palette.from(bitmap);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

            }
        });
        Palette.Builder builder = new Palette.Builder(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

            }
        });

    }

    private void initDagger() {
        DaggerMainComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .mainModule(new MainModule("HAHA"))
                .build().inject(this);
    }

    private void retrofitTest() {
        ProtocolImp retorfit = RetrofitFactory.getRetorfit(ProtocolImp.class);

    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four,
            R.id.btn_five, R.id.btn_six, R.id.btn_seven, R.id.btn_eight})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                retrofitTest();
                break;
            case R.id.btn_two:
                mUserInfo.setUserName("随机数" + new Random(100).nextInt());
                break;
            case R.id.btn_three:
                DemoKotlin demoKotlin = new DemoKotlin();
                break;
            case R.id.btn_four:
                startActivity(new Intent(mContext, KotlinActivity.class));
                break;
            case R.id.btn_five:
                info.getUserInfo();
                Logger.e("" + info.getUserInfo());
                break;
            case R.id.btn_six:
                break;
            case R.id.btn_seven:
                break;
            case R.id.btn_eight:
                break;
        }
    }
}

package com.healthsoulmate.zkl.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivitySplashBinding;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/20.
 * 欢迎页面
 */
public class SplashActivity extends AppBaseActivity {

    private AnimatorSet mAnimatorSet;
    private ActivitySplashBinding mDataBinding;
    private long TIME_ANIMATOR = 500;
    private long TIME_JUMP = TIME_ANIMATOR + 500;


    @Override
    public int getResLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mDataBinding = (ActivitySplashBinding) viewDataBinding;
        View logoView = mDataBinding.layoutSplash;
        View backgroundView = mDataBinding.imgSplashBg;
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(TIME_ANIMATOR);
        mAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(logoView, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(logoView, "translationY", 300, 0),
                ObjectAnimator.ofFloat(backgroundView, "scaleX", 1.3f, 1.05f),
                ObjectAnimator.ofFloat(backgroundView, "scaleY", 1.3f, 1.05f)
        );
        mAnimatorSet.start();
    }

    @Override
    public void initData() {
        Observable.timer(TIME_JUMP, TimeUnit.MILLISECONDS).subscribe(l -> {
            //TODO 跳转主页面
            JumpManager.doJumpForwardFinish(mActivity, new Intent(mActivity, MainTabActivity.class));
        });
    }

    @Override
    public void onBackPressed() {

    }
}

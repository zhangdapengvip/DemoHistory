package com.healthsoulmate.zkl.ui.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.ui.fragment.home.ShoppingCartFragment;
import com.zero.library.base.uibase.AppBaseFragmentActivity;

/**
 * Created by ZeroAries on 2016/5/26.
 * 购物车
 */
public class ShoppingCartActivity extends AppBaseFragmentActivity {
    @Override
    public int getResLayout() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void initData() {

    }
}

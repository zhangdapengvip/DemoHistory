package com.healthsoulmate.zkl.forum.fragment;

import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentPalteBinding;
import com.healthsoulmate.zkl.forum.factory.FragmentFactory;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.request.DiscuzsectionPageRequest;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.zero.library.base.manager.FragmentManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/5/5.
 * 论坛板块
 */
public class PlateFragment extends AppPagerFragment {

    private static final String SAVE_TAG = PlateFragment.class.getSimpleName();
    private FragmentPalteBinding mBinding;
    private String mLocalStr;
    private SharedPreferences mUncacleSp;

    @Override
    public String getPagerTitle() {
        return "论坛板块";
    }

    @Override
    protected int getResLayout() {
        return R.layout.fragment_palte;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mBinding = (FragmentPalteBinding) inflate;
        mBinding.btnReload.setOnClickListener(v -> requestTabs());
    }

    @Override
    protected void initData() {
        mUncacleSp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference.UNCLEAR_SETTING);
        mLocalStr = UtilsSharedPreference.getStringValue(mUncacleSp, SAVE_TAG);
        DiscuzsectionPageResponse response = UtilsGson.fromJson(mLocalStr, DiscuzsectionPageResponse.class);
        if (null != response) fillTab(response);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestTabs();
    }

    private void requestTabs() {
        mLocalStr = UtilsSharedPreference.getStringValue(mUncacleSp, SAVE_TAG);
        DiscuzsectionPageRequest request = new DiscuzsectionPageRequest();
        Observable<DiscuzsectionPageResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .discuzsectionPageRequest(request);
        RetrofitUtils.request(mActivity, ob, new RetrofitUtils.ResponseListener<DiscuzsectionPageResponse>() {
            @Override
            public void beforRequest() {

            }

            @Override
            public void onSuccess(DiscuzsectionPageResponse response) {
                String netStr = UtilsGson.toJson(response);
                UtilsSharedPreference.setStringValue(mUncacleSp, SAVE_TAG, netStr);
                if (!mLocalStr.equals(netStr)) {
                    fillTab(response);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (TextUtils.isEmpty(mLocalStr)) mBinding.btnReload.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish(boolean isSuccess) {

            }
        });
    }

    private void fillTab(DiscuzsectionPageResponse response) {
        List<DiscuzsectionPageResponse.ListBean> itemList = response.getList();
        mBinding.tabsForumGroup.removeAllViews();
        int maxCount = itemList.size();
        RadioButton btnSave = (RadioButton) UtilsUi.inflate(mActivity, R.layout.item_forum_tab);
        btnSave.setText(R.string.string_store);
        btnSave.setId(R.id.tab_first);
        mBinding.tabsForumGroup.addView(btnSave);
        for (int count = 0; count < maxCount; count++) {
            DiscuzsectionPageResponse.ListBean itemInfo = itemList.get(count);
            RadioButton button = (RadioButton) UtilsUi.inflate(mActivity, R.layout.item_forum_tab);
            button.setText(itemInfo.getSectionname());
            mBinding.tabsForumGroup.addView(button);
        }
        btnSave.setChecked(true);
        mBinding.tabsForumGroup.check(R.id.tab_first);
        new FragmentManager(getActivity(), FragmentFactory.createForumFragments(itemList),
                R.id.tab_forum_content, mBinding.tabsForumGroup) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                super.onCheckedChanged(radioGroup, checkedId);
            }
        };
    }
}
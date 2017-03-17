package com.healthsoulmate.zkl.ui.fragment.home;

import android.content.SharedPreferences;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.FragmentFindBinding;
import com.healthsoulmate.zkl.ui.factory.FragmentFactory;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.response.GoodsTypePageResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zero.library.base.manager.FragmentManager;
import com.zero.library.base.retrofit.DefaultRequest;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsUi;

import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/4/21.
 * 发现界面
 */
public class FindListFragment extends AppBaseFragment {
    private static final String SAVE_TAG = FindListFragment.class.getSimpleName();

    private FragmentFindBinding mBinding;
    private SharedPreferences mUnclearSp;
    private String mLocalStr;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(View view, ViewDataBinding inflate) {
        mBinding = (FragmentFindBinding) inflate;
        mBinding.titleBar.setTitle(R.string.title_find);
        mBinding.titleBar.setLeftText(R.string.title_left_scanning, R.drawable.ic_scanning);
        mBinding.titleBar.setRightText(R.string.title_right_message, R.drawable.ic_message);
        mBinding.btnReload.setOnClickListener(v -> requestData());
    }

    @Override
    protected void initData() {
        mBinding.btnReload.setVisibility(View.GONE);
        mUnclearSp = UtilsSharedPreference.getSharedPreference(UtilsSharedPreference.UNCLEAR_SETTING);
        mLocalStr = UtilsSharedPreference.getStringValue(mUnclearSp, SAVE_TAG);
        GoodsTypePageResponse response = UtilsGson.fromJson(mLocalStr, GoodsTypePageResponse.class);
        if (null != response) fillTab(response);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        mLocalStr = UtilsSharedPreference.getStringValue(mUnclearSp, SAVE_TAG);
        DefaultRequest request = new DefaultRequest();
        Observable<GoodsTypePageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .goodsTypePageRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<GoodsTypePageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(GoodsTypePageResponse response) {
                        String netStr = UtilsGson.toJson(response);
                        UtilsSharedPreference.setStringValue(mUnclearSp, SAVE_TAG, netStr);
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

    private void fillTab(GoodsTypePageResponse response) {
        List<GoodsTypePageResponse.DatasBean> itemList = response.getDatas();
        mBinding.tabsGroup.removeAllViews();
        int maxCount = itemList.size();
        if (maxCount <= 0) return;
        int itemWidth = UtilsUi.getScreenWidth(mActivity) / maxCount;
        for (int count = 0; count < maxCount; count++) {
            GoodsTypePageResponse.DatasBean itemInfo = itemList.get(count);
            RadioButton button = (RadioButton) UtilsUi.inflate(mActivity, R.layout.item_find_tab);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(params);
            button.setText(itemInfo.getName());
            if (count == 0) {
                button.setId(R.id.tab_first);
                button.setBackgroundColor(UtilsUi.getColor(R.color.white));
            }
            ImageView loadImg = new ImageView(mActivity);
            Drawable drawable = UtilsUi.getDrawable(R.drawable.ic_tab_normal);
            drawable.setBounds(0, 0, UtilsUi.dipAdaptationPx(mActivity, 20), UtilsUi.dipAdaptationPx(mActivity, 20));
            button.setCompoundDrawables(null, drawable, null, null);
            Picasso.with(mActivity)
                    .load(itemInfo.getSelectedImage())
                    .resizeDimen(R.dimen.dimen_20, R.dimen.dimen_20)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.ic_tab_normal)
                    .error(R.drawable.ic_tab_normal)
                    .into(loadImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            Drawable drawable = loadImg.getDrawable();
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            button.setCompoundDrawables(null, drawable, null, null);
                        }

                        @Override
                        public void onError() {

                        }
                    });
            mBinding.tabsGroup.addView(button);
        }
        mBinding.tabsGroup.check(R.id.tab_first);
        new FragmentManager(getActivity(), FragmentFactory.createFindItemFragments(itemList),
                R.id.tab_find_content, mBinding.tabsGroup) {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                super.onCheckedChanged(radioGroup, checkedId);
                for (int index = 0; index < radioGroup.getChildCount(); index++) {
                    View child = radioGroup.getChildAt(index);
                    if (child.getId() == checkedId) {
                        child.setBackgroundColor(UtilsUi.getColor(R.color.white));
                    } else {
                        child.setBackgroundColor(UtilsUi.getColor(R.color.app_background_color));
                    }
                }
            }
        };
    }
}
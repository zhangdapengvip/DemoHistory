package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityPhotoPreviewBinding;
import com.healthsoulmate.zkl.databinding.ItemPhotoPreviewBinding;
import com.healthsoulmate.zkl.forum.bean.ImgInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.uibase.AppPagerAdapter;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;

/**
 * Created by ZeroAries on 2016/5/5.
 * 大图预览
 */
public class PhotoPreviewActivity extends AppBaseActivity {
    public static final String POSITION = "POSITION";
    public static final String HIDENDELETE = "HIDENDELETE";
    private ActivityPhotoPreviewBinding mBinding;
    private int currentPosition = 0;
    private ArrayList<ImgInfo> mItemList = new ArrayList<>();
    private boolean isHiden;

    @Override
    public int getResLayout() {
        return R.layout.activity_photo_preview;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityPhotoPreviewBinding) viewDataBinding;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mItemList = intent.getParcelableArrayListExtra(AppConstants.PARCELABLE_KEY);
        currentPosition = intent.getIntExtra(POSITION, 0);
        isHiden = intent.getBooleanExtra(HIDENDELETE, false);
        AppPagerAdapter<ImgInfo> adapter = new AppPagerAdapter<ImgInfo>(mItemList) {
            @Override
            protected View initItemView(ImgInfo info, int position) {
                ItemPhotoPreviewBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                        R.layout.item_photo_preview, null, false);
                if (null == info.getUri()) {
                    Picasso.with(mActivity)
                            .load(info.getPath())
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.bg_transparent)
                            .error(R.drawable.bg_transparent)
                            .into(inflate.ivPhoto);
                } else {
                    Picasso.with(mActivity)
                            .load(info.getUri())
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.bg_transparent)
                            .error(R.drawable.bg_transparent)
                            .into(inflate.ivPhoto);
                }
                return inflate.getRoot();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        mBinding.vpPhoto.setAdapter(adapter);
        mBinding.vpPhoto.setOffscreenPageLimit(3);
        mBinding.vpPhoto.setCurrentItem(currentPosition);
        mBinding.tvBack.setOnClickListener(v -> jumpBack());
        mBinding.tvBack.setText((currentPosition + 1) + "/" + mItemList.size());
        mBinding.vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                mBinding.tvBack.setText((currentPosition + 1) + "/" + mItemList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        UtilsUi.setVisibility(mBinding.tvDelete, !isHiden);
        mBinding.tvDelete.setOnClickListener(v -> {
            mItemList.remove(currentPosition);
            if (currentPosition > 0) {
                currentPosition = currentPosition - 1;
            }
            if (mItemList.size() <= 0) {
                jumpBack();
            }
            mBinding.tvBack.setText((currentPosition + 1) + "/" + mItemList.size());
            adapter.notifyDataSetChanged();
            mBinding.vpPhoto.setCurrentItem(currentPosition);
        });
    }

    @Override
    public void onBackPressed() {
        jumpBack();
    }

    private void jumpBack() {
        setResult(RESULT_OK, new Intent().putParcelableArrayListExtra(AppConstants.PARCELABLE_KEY, mItemList));
        JumpManager.doJumpBack(mActivity);
    }
}

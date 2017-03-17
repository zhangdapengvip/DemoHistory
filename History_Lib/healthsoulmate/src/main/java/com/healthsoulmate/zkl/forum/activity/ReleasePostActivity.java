package com.healthsoulmate.zkl.forum.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.TableRow;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityReleasePostBinding;
import com.healthsoulmate.zkl.databinding.ItemAddImageviewBinding;
import com.healthsoulmate.zkl.databinding.ItemImageviewBinding;
import com.healthsoulmate.zkl.forum.bean.ImgInfo;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.ProtocolConstants;
import com.healthsoulmate.zkl.forum.protocol.response.DiscuzsectionPageResponse;
import com.healthsoulmate.zkl.ui.bean.Constants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.squareup.picasso.Picasso;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.PhotoActivity;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.UtilsZip;
import com.zero.library.base.utils.picasso.transformations.RoundedCornersTransformation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/5/11.
 * 发布帖子
 */
public class ReleasePostActivity extends PhotoActivity {

    public static final String ZIP_NAME = "images.zip";
    private static final int MAX_WIDTH = 320;
    private static final int MAX_HEIGHT = 480;

    private ActivityReleasePostBinding mBinding;
    private ArrayList<ImgInfo> mImgList = new ArrayList<>();
    private int mMaxImgCount = 9;
    private static final int MAX_ROWS = 4;
    private static final int PHOTO_RESULT = 4001;
    private DiscuzsectionPageResponse.ListBean mBlockInfo;
    private Subscription mSubscription;

    @Override
    public int getResLayout() {
        return R.layout.activity_release_post;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBlockInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null == mBlockInfo) JumpManager.doJumpBack(mActivity);
        mBinding = (ActivityReleasePostBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_release_post);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.btn_release_post);
        mBinding.titleBar.setRightListener(v -> {
            ArrayList<File> resultList = new ArrayList<>();
            mSubscription = Observable.just(1).map(integer -> {
                try {
                    for (ImgInfo info : mImgList) {
                        resultList.add(info.getFile());
                    }
                    if (resultList.size() <= 0) {
                        return true;
                    }
                    File[] fileArr = resultList.toArray(new File[resultList.size()]);
                    UtilsZip.zipFiles(new File(DirConstants.DEFAULT_IMG_DIR + ZIP_NAME), null, fileArr);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    AppToast.show(mActivity, R.string.error_zip_error);
                    return false;
                }
            }).subscribe(result -> {
                if (result) {
                    releasePost();
                }
            });
        });
        initImgContent(mImgList);
    }

    private void releasePost() {
        String title = mBinding.tvTitle.getText().toString().trim();
        String content = mBinding.tvContent.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, mBinding.tvTitle) ||
                UtilsString.checkEmpty(mActivity, mBinding.tvContent)) {
            return;
        }
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(ProtocolConstants.PKDISCUZSECTION, mBlockInfo.getPkDiscuzsection());
        parameterMap.put(ProtocolConstants.TITLE, title);
        parameterMap.put(ProtocolConstants.CONTENT, content);
        parameterMap.put(ProtocolConstants.PKUSER, loginInfo.getDatas().getPkUser());
        File file = new File(DirConstants.DEFAULT_IMG_DIR + ZIP_NAME);
        if (file.exists()) parameterMap.put(ProtocolConstants.IMAGEFILE, file);

        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .postsSavePostsRequest(RetrofitUtils.getRequesetMap(parameterMap));
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, R.string.toast_release_success);
                        JumpManager.doJumpBack(mActivity);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    private void initImgContent(ArrayList<ImgInfo> mImgList) {
        ItemAddImageviewBinding addInflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout
                .item_add_imageview, null, false);
        addInflate.ivActionPhoto.setOnClickListener(v -> {
            UtilsUi.hideSoftInput(mActivity);
            showPhotoWindow();
        });

        mBinding.tlImgContent.removeAllViews();
        TableRow tableRow = null;
        for (int position = 0; position < mImgList.size(); position++) {
            if (position % MAX_ROWS == 0) {
                tableRow = new TableRow(mActivity);
                mBinding.tlImgContent.addView(tableRow);
            }
            ItemImageviewBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                    R.layout.item_imageview, null, false);
            int currentPosition = position;
            inflate.bindingContent.setOnClickListener(v -> {
                Intent intent = new Intent(mActivity, PhotoPreviewActivity.class);
                intent.putParcelableArrayListExtra(AppConstants.PARCELABLE_KEY, mImgList);
                intent.putExtra(PhotoPreviewActivity.POSITION, currentPosition);
                JumpManager.doJumpForwardWithResult(mActivity, intent, PHOTO_RESULT);
            });
            Picasso.with(mActivity)
                    .load(mImgList.get(position).getUri())
                    .resizeDimen(R.dimen.dimen_60, R.dimen.dimen_60)
                    .config(Bitmap.Config.RGB_565)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(5, 0))
                    .placeholder(R.drawable.ic_default_img)
                    .error(R.drawable.ic_default_img)
                    .into(inflate.ivInfo);
            tableRow.addView(inflate.getRoot());
        }
        if (null == tableRow || tableRow.getChildCount() == MAX_ROWS) {
            tableRow = new TableRow(mActivity);
            mBinding.tlImgContent.addView(tableRow);
        }
        if (mImgList.size() < mMaxImgCount) {
            tableRow.addView(addInflate.getRoot());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO_RESULT) {
            ArrayList<ImgInfo> resultList = data.getParcelableArrayListExtra(AppConstants.PARCELABLE_KEY);
            mImgList.clear();
            mImgList.addAll(resultList);
            initImgContent(resultList);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscription) mSubscription.unsubscribe();
    }

    @Override
    protected void imageBack(Uri uri, String path) {
        if (TextUtils.isEmpty(path)) {
            AppToast.show(mActivity, R.string.toast_img_error);
            return;
        }
        String name = SystemClock.elapsedRealtime() + ".jpg";
        Observable.just(1).observeOn(Schedulers.newThread()).subscribe(v -> {
            UtilsBitmap.compressBitmapToFile(path, DirConstants.DEFAULT_IMG_DIR, name, MAX_WIDTH, MAX_HEIGHT);
        });
        mImgList.add(new ImgInfo(path, uri, new File(DirConstants.DEFAULT_IMG_DIR + name)));
        initImgContent(mImgList);
    }
}

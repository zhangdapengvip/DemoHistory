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
import com.healthsoulmate.zkl.databinding.ActivityReplyCommentBinding;
import com.healthsoulmate.zkl.databinding.ItemAddImageviewBinding;
import com.healthsoulmate.zkl.databinding.ItemImageviewBinding;
import com.healthsoulmate.zkl.forum.bean.ImgInfo;
import com.healthsoulmate.zkl.forum.protocol.ForumImp;
import com.healthsoulmate.zkl.forum.protocol.ProtocolConstants;
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
 * Created by ZeroAries on 2016/5/4.
 * 评论回复界面
 */
public class ReplyCommentActivity extends PhotoActivity {

    public static final String ZIP_NAME = "images.zip";
    private static final int MAX_WIDTH = 320;
    private static final int MAX_HEIGHT = 480;

    private ActivityReplyCommentBinding mBinding;
    private ArrayList<ImgInfo> mImgList = new ArrayList<>();
    private int mMaxImgCount = 6;
    private static final int MAX_ROWS = 4;
    private static final int PHOTO_RESULT = 4001;
    private String mPkPost;
    private Subscription mSubscription;

    @Override
    public int getResLayout() {
        return R.layout.activity_reply_comment;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mPkPost = getIntent().getStringExtra(AppConstants.EXTRA_STRING);
        if (TextUtils.isEmpty(mPkPost)) JumpManager.doJumpBack(mActivity);
        mBinding = (ActivityReplyCommentBinding) viewDataBinding;
        mBinding.titleBar.setTitle("评论");
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.send);
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
                    sendReply();
                }
            });
        });
        initImgContent(mImgList);
    }

    private void sendReply() {
        String replyContent = mBinding.etReplyContent.getText().toString().trim();
        if (TextUtils.isEmpty(replyContent)) {
            AppToast.show(mActivity, "请输入回复内容");
            return;
        }
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put(ProtocolConstants.PKPOSTS, mPkPost);
        parameterMap.put(ProtocolConstants.PKUSER, loginInfo.getDatas().getPkUser());
        parameterMap.put(ProtocolConstants.CONTENT, replyContent);
        File file = new File(DirConstants.DEFAULT_IMG_DIR + ZIP_NAME);
        if (file.exists()) parameterMap.put(ProtocolConstants.IMAGEFILE, file);

        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ForumImp.class)
                .discussSaveRequest(RetrofitUtils.getRequesetMap(parameterMap));
        RetrofitUtils.request(mActivity, ob, mBinding.titleBar.getRightView(),
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        setResult(RESULT_OK);
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
        addInflate.ivActionPhoto.setOnClickListener(v -> showPhotoWindow());

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscription) mSubscription.unsubscribe();
    }

}

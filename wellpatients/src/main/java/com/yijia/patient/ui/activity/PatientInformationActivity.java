package com.yijia.patient.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yijia.patient.R;
import com.yijia.patient.ui.bean.InformationInfo;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.HelpDetailRequest;
import com.yijia.patient.ui.protocol.request.ConstantsRequest;
import com.yijia.patient.ui.protocol.response.HelpDetailRsponse;
import com.yijia.patient.ui.protocol.response.HelpGetResponse;
import com.yijia.patient.ui.protocol.response.HospitalPageResponse;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.PhotoActivity;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.UtilsZip;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.TitleBarView;
import com.zero.library.base.utils.picasso.UtilsPicasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/3/14.
 * 特需——寻求帮助——求助信息
 */
public class PatientInformationActivity extends PhotoActivity implements View.OnClickListener {

    private static final int MAX_WIDTH = 240;
    private static final int MAX_HEIGHT = 320;
    public static final int MAX_IMAGE_SIZE = 3;
    public static final String ZIP_NAME = "images.zip";

    private ArrayList<InformationInfo> mViewList;
    private int mCheckPosition = -1;
    private Subscription mSubscription;
    private View mCommitView;
    private Serializable mInfoData;
    private HospitalPageResponse.Page.Rows mHospitalInfo;
    private HelpGetResponse.Datas mHelpInfo;
    private Subscriber<? super DefaultResponse> mCommitSub;
    private Subscriber<? super HelpDetailRsponse> mLoadSub;
    private AppAlertDialog mSuccessDialog;
    private TextView mTvTitle;
    private EditText mEtBaseInfo;


    @Override
    public int getResLayout() {
        return R.layout.activity_patient_information;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        mInfoData = intent.getSerializableExtra(AppConstants.PARCELABLE_KEY);
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_patient_information);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mCommitView = findViewById(R.id.tv_commit);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mEtBaseInfo = (EditText) findViewById(R.id.et_base_info);
        mCommitView.setOnClickListener(this);
    }

    private void initAddView(boolean isCommit) {
        mViewList = new ArrayList<>();
        InformationInfo info1 = new InformationInfo();
        info1.btnId = R.id.tv_base_information;
        info1.groupId = R.id.ll_base_content;
        info1.itemView = findViewById(info1.btnId);
        info1.groupView = (LinearLayout) findViewById(info1.groupId);
        mViewList.add(info1);

        InformationInfo info2 = new InformationInfo();
        info2.btnId = R.id.tv_patients_information;
        info2.groupId = R.id.ll_information_content;
        info2.itemView = findViewById(info2.btnId);
        info2.groupView = (LinearLayout) findViewById(info2.groupId);
        mViewList.add(info2);

        InformationInfo info3 = new InformationInfo();
        info3.btnId = R.id.tv_first;
        info3.groupId = R.id.ll_first_content;
        info3.itemView = findViewById(info3.btnId);
        info3.groupView = (LinearLayout) findViewById(info3.groupId);
        mViewList.add(info3);

        InformationInfo info4 = new InformationInfo();
        info4.btnId = R.id.tv_second;
        info4.groupId = R.id.ll_second_content;
        info4.itemView = findViewById(info4.btnId);
        info4.groupView = (LinearLayout) findViewById(info4.groupId);
        mViewList.add(info4);

        InformationInfo info5 = new InformationInfo();
        info5.btnId = R.id.tv_patientbase;
        info5.groupId = R.id.ll_patientbase_content;
        info5.itemView = findViewById(info5.btnId);
        info5.groupView = (LinearLayout) findViewById(info5.groupId);
        mViewList.add(info5);

        InformationInfo info6 = new InformationInfo();
        info6.btnId = R.id.tv_patientcourse;
        info6.groupId = R.id.ll_patientcourse_content;
        info6.itemView = findViewById(info6.btnId);
        info6.groupView = (LinearLayout) findViewById(info6.groupId);
        mViewList.add(info6);
        if (isCommit) {
            for (InformationInfo info : mViewList) {
                info.itemView.setOnClickListener(this);
            }
        } else {
            mEtBaseInfo.setClickable(false);
            mEtBaseInfo.setFocusable(false);
            mEtBaseInfo.setHint("");
        }
    }

    @Override
    public void initData() {
        if (null == mInfoData) {
            JumpManager.doJumpBack(mActivity);
        } else if (mInfoData instanceof HospitalPageResponse.Page.Rows) {
            initAddView(true);
            mHospitalInfo = (HospitalPageResponse.Page.Rows) mInfoData;
            mTvTitle.setText(mHospitalInfo.getHospitalname());
        } else if (mInfoData instanceof HelpGetResponse.Datas) {
            initAddView(false);
            mHelpInfo = (HelpGetResponse.Datas) mInfoData;
            mCommitView.setVisibility(View.GONE);
            mTvTitle.setText(mHelpInfo.getTitle());
            mEtBaseInfo.setText(mHelpInfo.getComment());
            loadHelpInfo();
        }
    }

    private void loadHelpInfo() {
        HelpDetailRequest request = new HelpDetailRequest();
        request.setPkHelp(mHelpInfo.getPkHelp());
        Observable<HelpDetailRsponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).getHelpDetailRequest(request);
        mLoadSub = RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<HelpDetailRsponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(HelpDetailRsponse response) {
                        fillView(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void fillView(HelpDetailRsponse response) {
        List<HelpDetailRsponse.Datas> datas = response.getList();
        for (HelpDetailRsponse.Datas item : datas) {
            InformationInfo info = mViewList.get(item.getPicturetype() - 1);
            ImageView imageContent = (ImageView) UtilsUi.inflate(mActivity, R.layout.item_image_content);
            UtilsPicasso.loadCenterImage(mContext, item.getPictureurl(),
                    R.drawable.ic_default_news, imageContent, R.dimen.dimen_80);
            info.groupView.addView(imageContent, info.groupView.getChildCount());
        }
    }

    @Override
    public void onClick(View v) {
        if (closePhotoWindow()) return;
        for (int index = 0; index < mViewList.size(); index++) {
            InformationInfo itemInfo = mViewList.get(index);
            if (itemInfo.btnId == v.getId() && itemInfo.fileList.size() < MAX_IMAGE_SIZE) {
                mCheckPosition = index;
                showPhotoWindow();
                return;
            }
        }
        switch (v.getId()) {
            case R.id.tv_commit:
                ArrayList<File> resultList = new ArrayList<>();
                mSubscription = Observable.just(1).map(integer -> {
                    try {
                        for (InformationInfo info : mViewList) {
                            resultList.addAll(info.fileList);
                        }
                        if (resultList.size() <= 0) {
                            AppToast.show(mActivity, R.string.empty_zip_error);
                            return false;
                        }
                        File[] objects = resultList.toArray(new File[resultList.size()]);
                        UtilsZip.zipFiles(new File(DirConstants.DEFAULT_IMG_DIR + ZIP_NAME), null, objects);
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        AppToast.show(mActivity, R.string.error_zip_error);
                        return false;
                    }
                }).subscribe(result -> {
                    if (result) {
                        sendToService();
                    }
                });
                break;
        }
    }

    private void sendToService() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        String title = mTvTitle.getText().toString().trim();
        String comment = mEtBaseInfo.getText().toString().trim();
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put(ConstantsRequest.HELP_IMAGEFILE, new File(DirConstants.DEFAULT_IMG_DIR + ZIP_NAME));
        requestMap.put(ConstantsRequest.HELP_TITLE, title);
        requestMap.put(ConstantsRequest.HELP_COMMENT, comment);
        requestMap.put(ConstantsRequest.HELP_PKHOSPITAL, mHospitalInfo.getPkHospital());
        requestMap.put(ConstantsRequest.PKUSER, loginInfo.getDatas().getPkUser());
        Map<String, RequestBody> params = RetrofitUtils.getRequesetMap(requestMap);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).helpSaveRequest(params);
        mCommitSub = RetrofitUtils.request(mActivity, ob, mCommitView,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        showSuccessDialog(response);
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

    private void showSuccessDialog(DefaultResponse response) {
        mSuccessDialog = UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string.dialog_saverequest_success)
                , (dialog, view) -> {
                    JumpManager.doJumpBack(mActivity);
                    dialog.dismiss();
                });
        mSuccessDialog.show();
    }


    private void addImage(Uri uri, String path) {
        if (TextUtils.isEmpty(path)) {
            AppToast.show(mActivity, R.string.toast_img_error);
            return;
        }
        if (mCheckPosition == -1) {
            closePhotoWindow();
            return;
        }
        ImageView imageContent = (ImageView) UtilsUi.inflate(mActivity, R.layout.item_image_content);
        Picasso.with(mContext)
                .load(uri)
                .resizeDimen(R.dimen.dimen_80, R.dimen.dimen_80)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(imageContent);

        InformationInfo checkInfo = mViewList.get(mCheckPosition);
        LinearLayout groupView = checkInfo.groupView;
        imageContent.setOnClickListener(clickView -> {
            groupView.removeView(clickView);
            checkInfo.fileList.remove(clickView.getTag());
        });
        String name = mCheckPosition + 1 + "_" + SystemClock.elapsedRealtime() + ".jpg";
        checkInfo.fileList.add(new File(DirConstants.DEFAULT_IMG_DIR + name));
        imageContent.setTag(new File(DirConstants.DEFAULT_IMG_DIR + name));
        groupView.addView(imageContent, groupView.getChildCount());
        Observable.just(1).observeOn(Schedulers.newThread()).subscribe(v -> {
            UtilsBitmap.compressBitmapToFile(path, DirConstants.DEFAULT_IMG_DIR, name, MAX_WIDTH, MAX_HEIGHT);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_LIBRARY:
                    if (data != null) {
                        Uri uri = data.getData();
                        String path = UtilsBitmap.getPath(mActivity, uri);
                        addImage(uri, path);
                    }
                    break;
                case TAKE_PHOTO:
                    addImage(mImgUri, mImgUri.getPath());
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSubscription) mSubscription.unsubscribe();
        if (null != mLoadSub) mLoadSub.unsubscribe();
        if (null != mCommitSub) mCommitSub.unsubscribe();
        if (null != mSuccessDialog) mSuccessDialog.dismiss();
    }
}

package com.yijia.patient.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.ConstantsRequest;
import com.yijia.patient.ui.protocol.request.GetMessageSendRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.PhotoActivity;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.view.AppSpinner;
import com.zero.library.base.view.TitleBarView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/3/14.
 * 认证
 */
public class AuthenticationActivity extends PhotoActivity {

    private static final int MAX_WIDTH = 480;
    private static final int MAX_HEIGHT = 800;
    private static final int TIME_COUNT = 60;
    private static final String AUTH_IMG = "auth.jpg";

    private ImageView mDoctorsCertificate;
    private EditText mEtName;
    private EditText mEtHospital;
    private EditText mEtOffice;
    private AppSpinner mTitle;
    private EditText mEtPhone;
    private TextView mTvGetPhoneCode;
    private EditText mEtPhoneCode;
    private View mTvCommit;
    private Subscription mGetCodeSub;
    private LoginResponse mLoginInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_authentication;
    }

    @Override
    public void initView() {
        TitleBarView mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.title_authentication);
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mDoctorsCertificate = (ImageView) findViewById(R.id.iv_doctors_certificate);
        mDoctorsCertificate.setOnClickListener(v -> showPhotoWindow());
        mEtName = (EditText) findViewById(R.id.et_user_name);
        mEtHospital = (EditText) findViewById(R.id.et_hospital);
        mEtOffice = (EditText) findViewById(R.id.et_office);
        mTitle = (AppSpinner) findViewById(R.id.spinner_title);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mTvGetPhoneCode = (TextView) findViewById(R.id.tv_get_phonecode);
        mEtPhoneCode = (EditText) findViewById(R.id.et_phone_code);
        mTvCommit = findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(v -> commitInfo());
        mTvGetPhoneCode.setOnClickListener(v -> getPhoneCode());
        mTitle.setViewBackground(R.drawable.bg_transparent);
        mTitle.setIcon(R.drawable.ic_right_down);
    }


    @Override
    public void initData() {
        mLoginInfo = UserManager.getLoginInfo();
        mEtName.setText(mLoginInfo.getDatas().getName());
        mEtHospital.setText(mLoginInfo.getDatas().getHospital());
        mEtOffice.setText(mLoginInfo.getDatas().getDepartment());
        mEtPhone.setText(mLoginInfo.getDatas().getPhone());
        mTitle.setHintText(R.string.hint_title);
        mTitle.setList(Constants.getTitleList());
        mTitle.setMaxItemCount(6);
        mTitle.setKey(mLoginInfo.getDatas().getTitle());
    }

    private void commitInfo() {
        String name = mEtName.getText().toString().trim();
        String hospital = mEtHospital.getText().toString().trim();
        String office = mEtOffice.getText().toString().trim();
        String title = mTitle.getCheckItem().getKey();
        String phone = mEtPhone.getText().toString().trim();
        String phoneCode = mEtPhoneCode.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, name, R.string.hint_name)
                || UtilsString.checkEmpty(mActivity, hospital, R.string.hint_hospital)
                || UtilsString.checkEmpty(mActivity, office, R.string.hint_office)
                || UtilsString.checkEmpty(mActivity, title, R.string.hint_title)
                || UtilsString.checkRegex(mActivity, phone, UtilsString.PHONEEGEX, R.string.hint_phone)
                || UtilsString.checkEmpty(mActivity, phoneCode, R.string.hint_authentication_phone_code)
                ) {
            return;
        }
        File file = new File(DirConstants.DEFAULT_IMG_DIR, AUTH_IMG);
        if (!file.exists()) {
            AppToast.show(mActivity, R.string.toast_img_file);
            mDoctorsCertificate.setImageResource(R.drawable.ic_large_phone);
        }
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(ConstantsRequest.IMAGEFILE, file);
        infoMap.put(ConstantsRequest.PKUSER, mLoginInfo.getDatas().getPkUser());
        infoMap.put(ConstantsRequest.NAME, name);
        infoMap.put(ConstantsRequest.HOSPITAL, hospital);
        infoMap.put(ConstantsRequest.DEPARTMENT, office);
        infoMap.put(ConstantsRequest.TITLE, title);
        infoMap.put(ConstantsRequest.PHONE, phone);
        infoMap.put(ConstantsRequest.VALIDATIONCODE, phoneCode);
        Map<String, RequestBody> requesetMap = RetrofitUtils.getRequesetMap(infoMap);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).authenticationRequest
                (requesetMap);
        RetrofitUtils.request(mActivity, ob, mTvCommit,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        UserManager.storeLoginInfo(UtilsGson.toJson(response));
                        AppToast.show(mActivity, R.string.toast_commit_success);
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

    private void getPhoneCode() {
        String phone = mEtPhone.getText().toString().trim();
        if (UtilsString.checkRegex(mActivity, phone, UtilsString.PHONEEGEX, R.string.hint_phone)) {
            return;
        }
        GetMessageSendRequest request = new GetMessageSendRequest();
        request.setPhone(phone);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).getMessageSendRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, response.getMessage());
                        codeTime();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void codeTime() {
        mTvGetPhoneCode.setClickable(false);
        mGetCodeSub = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(TIME_COUNT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    mTvGetPhoneCode.setText(getString(R.string.btn_authentication_new_phone_code, TIME_COUNT - l));
                    mTvGetPhoneCode.setBackgroundResource(R.drawable.bg_btn_uncheck);
                    if (l == TIME_COUNT - 1) {
                        mTvGetPhoneCode.setClickable(true);
                        mTvGetPhoneCode.setText(R.string.btn_authentication_get_phone_code);
                        mTvGetPhoneCode.setBackgroundResource(R.drawable.bg_btn_normal);
                    }
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
                        loadImage(uri);
                    }
                    break;
                case TAKE_PHOTO:
                    loadImage(mImgUri);
                    break;
            }
        }
    }

    private void loadImage(Uri uri) {
        Observable.just(1).observeOn(Schedulers.newThread()).subscribe(v -> {
            UtilsBitmap.compressBitmapToFile(uri.getPath(), DirConstants.DEFAULT_IMG_DIR, AUTH_IMG, MAX_WIDTH,
                    MAX_HEIGHT);
        });
        Picasso.with(mContext)
                .load(uri)
                .resizeDimen(R.dimen.dimen_80, R.dimen.dimen_80)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(mDoctorsCertificate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mGetCodeSub) {
            mGetCodeSub.unsubscribe();
        }
    }
}

package com.yijia.zkl.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.ConstantsRequest;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppActivityManager;
import com.zero.library.base.uibase.PhotoActivity;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppSpinner;
import com.zero.library.base.view.TitleBarView;
import com.zero.library.base.utils.picasso.UtilsPicasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/11.
 * 我的
 */
public class MineActiviity extends PhotoActivity {

    public static final String HEAD_PIC = "headpic.jpg";

    private TitleBarView mTitleBar;
    private ImageView mHeadPic;
    private EditText mUserName;
    private AppSpinner mDomain;
    private EditText mName;
    private EditText mhospital;
    private EditText mOffice;
    private AppSpinner mTitle;
    private EditText mPhone;
    private Bitmap mBtMap;
    private TextView mTvAuth;

    @Override
    public int getResLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public void initView() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.title_mine);
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setRightText(R.string.save);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightListener(v -> commitInfo());
        mTvAuth = (TextView) findViewById(R.id.tv_auth_type);
        mHeadPic = (ImageView) findViewById(R.id.iv_head);
        mUserName = (EditText) findViewById(R.id.et_mine_username);
        mDomain = (AppSpinner) findViewById(R.id.spinner_domain);
        mName = (EditText) findViewById(R.id.et_mine_name);
        mhospital = (EditText) findViewById(R.id.et_mine_hospital);
        mOffice = (EditText) findViewById(R.id.et_office);
        mTitle = (AppSpinner) findViewById(R.id.spinner_title);
        mPhone = (EditText) findViewById(R.id.et_mine_phone);
        mHeadPic.setOnClickListener(v -> showPhotoWindow());
        findViewById(R.id.label_change_password).setOnClickListener(v -> JumpManager.doJumpForward(mActivity, new
                Intent(mActivity, ResetPasswordActivity.class)));
        findViewById(R.id.tv_logout).setOnClickListener(v -> {
            logOut();
        });
    }

    private void logOut() {
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).logOutRequest();
        RetrofitUtils.request(mActivity, ob);
        JPushInterface.setAlias(mActivity, "+10086", null);
        UtilsSharedPreference.clear();
        AppActivityManager.exitAPP();
        JumpManager.doJumpForward(mActivity, new Intent(mActivity, LoginActivity.class));
    }


    @Override
    public void initData() {
        mDomain.setHintText(R.string.hint_champs);
        mDomain.setList(Constants.getDomainList());
        mDomain.setMaxItemCount(6);
        mTitle.setHintText(R.string.hint_title);
        mTitle.setList(Constants.getTitleList());
        mTitle.setMaxItemCount(6);
        LoginResponse loginInfo = UserManager.getLoginInfo();
        UtilsPicasso.loadCircleImage(mActivity, loginInfo.getDatas().getImage(), R.drawable.ic_default_head,
                mHeadPic, R.dimen
                        .dimen_70);
        String username = loginInfo.getDatas().getUsername();
        mUserName.setText(username);
        if (!TextUtils.isEmpty(username)) {
            mUserName.setFocusable(false);
            mUserName.setEnabled(false);
        }
        mDomain.setKey(loginInfo.getDatas().getDomain());
        mName.setText(loginInfo.getDatas().getName());
        mhospital.setText(loginInfo.getDatas().getHospital());
        mOffice.setText(loginInfo.getDatas().getDepartment());
        mTitle.setKey(loginInfo.getDatas().getTitle());
        mPhone.setText(loginInfo.getDatas().getPhone());
        if (LoginResponse.TYPE_AUTH.equals(loginInfo.getDatas().getReview())) {
            mTvAuth.setText(R.string.lable_auth);
            mTvAuth.setTextColor(UtilsUi.getColor(R.color.red_FF));
        } else {
            mTvAuth.setText(R.string.lable_unauth);
            mTvAuth.setTextColor(UtilsUi.getColor(R.color.gray_55));
        }
    }

    private void commitInfo() {
        LoginResponse commitInfo = UserManager.getLoginInfo();
        String userName = mUserName.getText().toString().trim();
        String domain = mDomain.getCheckItem().getKey();
        String name = mName.getText().toString().trim();
        String hospital = mhospital.getText().toString().trim();
        String office = mOffice.getText().toString().trim();
        String title = mTitle.getCheckItem().getKey();
        String phone = mPhone.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, userName, R.string.hint_username)
                || UtilsString.checkEmpty(mActivity, domain, R.string.hint_champs)
                || UtilsString.checkEmpty(mActivity, name, R.string.hint_name)
                || UtilsString.checkEmpty(mActivity, hospital, R.string.hint_hospital)
                || UtilsString.checkEmpty(mActivity, office, R.string.hint_office)
                || UtilsString.checkEmpty(mActivity, title, R.string.hint_title)
                || UtilsString.checkRegex(mActivity, phone, UtilsString.PHONEEGEX, R.string.hint_phone)) {
            return;
        }
        Map<String, Object> infoMap = new HashMap<>();
        File file = new File(DirConstants.DEFAULT_IMG_DIR, HEAD_PIC);
        if (file.exists()) infoMap.put(ConstantsRequest.IMAGEFILE, file);
        infoMap.put(ConstantsRequest.PKUSER, commitInfo.getDatas().getPkUser());
        infoMap.put(ConstantsRequest.USERNAME, userName);
        infoMap.put(ConstantsRequest.PHONE, phone);
        infoMap.put(ConstantsRequest.NAME, name);
        infoMap.put(ConstantsRequest.DOMAIN, domain);
        infoMap.put(ConstantsRequest.HOSPITAL, hospital);
        infoMap.put(ConstantsRequest.DEPARTMENT, office);
        infoMap.put(ConstantsRequest.TITLE, title);
        Map<String, RequestBody> requesetMap = RetrofitUtils.getRequesetMap(infoMap);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).updateUserRequest(requesetMap);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        fillInfo(response);

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

    private void fillInfo(LoginResponse response) {
        UserManager.storeLoginInfo(UtilsGson.toJson(response));
        if (TextUtils.isEmpty(response.getDatas().getPk_other())) {
            UtilsSharedPreference.setStringValue(AppConstants.SP_AUTH_INFO, response.getDatas().getUsername() + ":" +
                    UtilsSharedPreference.getStringValue(Constants.PASSWORD));
            UtilsSharedPreference.setStringValue(Constants.USER_NAME, response.getDatas().getUsername());
        }
        JumpManager.doJumpBack(mActivity);
        AppToast.show(mActivity, R.string.toast_commit_success);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_LIBRARY:
                    if (data != null) {
                        Uri uri = data.getData();
                        Intent cropIntnet = UtilsBitmap.getCropIntent(uri, 300, 300);
                        JumpManager.doJumpForwardWithResult(mActivity, cropIntnet, PHOTO_REQUEST_CUT);
                    }
                    break;
                case TAKE_PHOTO:
                    Intent cropIntnet = UtilsBitmap.getCropIntent(mImgUri, 300, 300);
                    JumpManager.doJumpForwardWithResult(mActivity, cropIntnet, PHOTO_REQUEST_CUT);
                    break;
                case PHOTO_REQUEST_CUT:
                    if (null != data) {
                        if (mBtMap != null && !mBtMap.isRecycled()) {
                            mBtMap.recycle();
                        }
                        mBtMap = data.getParcelableExtra("data");
                        UtilsBitmap.saveBitmap(mBtMap, DirConstants.DEFAULT_IMG_DIR, HEAD_PIC);
                        mHeadPic.setImageBitmap(mBtMap);
                    }
                    break;
            }
        }
    }
}

package com.healthsoulmate.zkl.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ActivityMineBinding;
import com.healthsoulmate.zkl.databinding.DialogChoiceDateBinding;
import com.healthsoulmate.zkl.forum.protocol.ProtocolConstants;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.healthsoulmate.zkl.ui.protocol.ProtocolImp;
import com.healthsoulmate.zkl.ui.protocol.response.LoginResponse;
import com.squareup.picasso.Picasso;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.PhotoActivity;
import com.zero.library.base.utils.UtilsBitmap;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.transformations.RoundedCornersTransformation;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.LineGridView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ZeroAries on 2016/6/1.
 * 我的界面
 */
public class MineActivity extends PhotoActivity {

    private ActivityMineBinding mBinding;
    private final int MAX_WIDTH = 400;
    private final String mHeadName = "headImg.jpg";

    @Override
    public int getResLayout() {
        return R.layout.activity_mine;
    }

    @Override
    public void initView(ViewDataBinding viewDataBinding) {
        mBinding = (ActivityMineBinding) viewDataBinding;
        mBinding.titleBar.setTitle(R.string.title_mine_info);
        mBinding.titleBar.setLeftText(R.string.back);
        mBinding.titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mBinding.titleBar.setRightText(R.string.save);
        mBinding.titleBar.setRightListener(v -> commitInfo());
    }

    private void commitInfo() {
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null == loginInfo) return;
        String nickName = mBinding.etNickName.getText().toString().trim();
        String birthDay = mBinding.tvMineBirth.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, mBinding.etNickName) ||
                UtilsString.checkEmpty(mActivity, mBinding.tvMineBirth)) {
            return;
        }
        File headImg = new File(DirConstants.DEFAULT_IMG_DIR, mHeadName);
        Map<String, Object> requestMap = new HashMap<>();
        if (headImg.exists()) requestMap.put(ProtocolConstants.IMAGEFILE, headImg);
        requestMap.put(ProtocolConstants.PKUSER, loginInfo.getDatas().getPkUser());
        requestMap.put(ProtocolConstants.NAME, nickName);
        requestMap.put(ProtocolConstants.SEX, mBinding.cbSexMan.isChecked() ?
                ProtocolConstants.MAN : ProtocolConstants.WOMAN);
        requestMap.put(ProtocolConstants.BIRTHDAY, birthDay);
        Map<String, RequestBody> request = RetrofitUtils.getRequesetMap(requestMap);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).userpdateUserRequest(request);
        RetrofitUtils.request(mActivity, ob, mBinding.titleBar.getRightView(),
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        AppToast.show(mActivity, "修改成功");
                        UserManager.storeLoginInfo(UtilsGson.toJson(response));
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

    @Override
    public void initData() {
        mBinding.labelIvHead.setOnClickListener(v -> showPhotoWindow());
        mBinding.tvMineBirth.setOnClickListener(v -> showDateChoice());
        mBinding.btnLogout.setOnClickListener(v -> {
            UserManager.logOutCookie(mActivity);
            UserManager.clearLoginInfo();
            JumpManager.doJumpBack(mActivity);
        });
        mBinding.labelChangePassword.setOnClickListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, ResetPasswordActivity.class))
        );
        LoginResponse loginInfo = UserManager.getLoginInfo(mActivity);
        if (null != loginInfo) {
            String headUrl = loginInfo.getDatas().getImage();
            if (!TextUtils.isEmpty(headUrl)) {
                Picasso.with(mActivity)
                        .load(headUrl)
                        .resizeDimen(R.dimen.dimen_60, R.dimen.dimen_60)
                        .config(Bitmap.Config.RGB_565)
                        .centerCrop()
                        .transform(new RoundedCornersTransformation(5, 0))
                        .placeholder(R.drawable.ic_default_head)
                        .error(R.drawable.ic_default_head)
                        .into(mBinding.ivHead);
            } else {
                mBinding.ivHead.setImageResource(R.drawable.ic_default_head);
            }
            mBinding.etNickName.setText(loginInfo.getDatas().getName());
            mBinding.cbSexMan.setChecked(ProtocolConstants.MAN.equals(loginInfo.getDatas().getSex()));
            mBinding.cbSexWoman.setChecked(ProtocolConstants.WOMAN.equals(loginInfo.getDatas().getSex()));
            mBinding.tvMineBirth.setText(loginInfo.getDatas().getBirthday());
        }
    }

    private Bitmap mBtMap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_REQUEST_CUT) {
            if (null != data) {
                if (mBtMap != null && !mBtMap.isRecycled()) {
                    mBtMap.recycle();
                }
                mBtMap = data.getParcelableExtra("data");
                Observable.just(1).observeOn(Schedulers.newThread()).subscribe(v -> {
                    UtilsBitmap.saveBitmap(mBtMap, DirConstants.DEFAULT_IMG_DIR, mHeadName);
                });
                mBinding.ivHead.setImageBitmap(mBtMap);
            } else {
                AppToast.show(mActivity, R.string.toast_img_error);
            }
        }
    }

    @Override
    protected void imageBack(Uri uri, String path) {
        Intent cropIntent = UtilsBitmap.getCropIntent(uri, MAX_WIDTH, MAX_WIDTH);
        JumpManager.doJumpForwardWithResult(mActivity, cropIntent, PHOTO_REQUEST_CUT);
    }

    private int mYear;
    private int mMonth;
    private int mDay;

    protected void showDateChoice() {
        Calendar calendar = Calendar.getInstance();
        DialogChoiceDateBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.dialog_choice_date, null, false);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        inflate.dpDate.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                (view, year, monthOfYear, dayOfMonth) -> {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                });
        inflate.dpDate.setMaxDate(calendar.getTimeInMillis());
        setPadding(inflate.dpDate);
        AppEmptyDialog dateDialog = new AppEmptyDialog(mActivity, 1);
        dateDialog.setBtnOnClickListener((dialog, view) -> {
            DecimalFormat df = new DecimalFormat("00");
            mBinding.tvMineBirth.setText(mYear + "-" + df.format(mMonth + 1) + "-" + df.format(mDay));
            dialog.dismiss();
        });
        dateDialog.setView(inflate.getRoot());
        dateDialog.show();
    }


    public void setPadding(ViewGroup group) {
        for (int index = 0; index < group.getChildCount(); index++) {
            View child = group.getChildAt(index);
            if (child instanceof ViewGroup) {
                setPadding((ViewGroup) child);
            }
            if (child instanceof LinearLayout) {
                child.setPadding(0, 0, 0, 0);
            }
            if (child instanceof NumberPicker) {
                NumberPicker picker = (NumberPicker) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
                setNumberPicker(picker);
            }
        }
    }

    public void setNumberPicker(ViewGroup numberPicker) {
        for (int index = 0; index < numberPicker.getChildCount(); index++) {
            View child = numberPicker.getChildAt(index);
            if (child instanceof ViewGroup) {
                setNumberPicker((ViewGroup) child);
            }
            if (child instanceof ImageButton) {
                ImageButton picker = (ImageButton) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
            }
            if (child instanceof EditText) {
                EditText picker = (EditText) child;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                picker.setGravity(Gravity.CENTER);
                params.setMargins(0, 0, 0, 0);
                picker.setLayoutParams(params);
                picker.setPadding(0, 0, 0, 0);
            }
        }
    }
}

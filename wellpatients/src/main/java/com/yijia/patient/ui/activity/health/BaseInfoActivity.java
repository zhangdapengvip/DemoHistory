package com.yijia.patient.ui.activity.health;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.BasicinfoDetailsRequest;
import com.yijia.patient.ui.protocol.request.BasicinfoRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.view.MultiChoiceDialog;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.Calendar;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/24.
 * 基本信息
 */
public class BaseInfoActivity extends AppBaseActivity {
    private EditText etName;
    private MultiChoiceDialog mcdSex;
    private EditText etHeight;
    private EditText etWeight;
    private MultiChoiceDialog mcdMarry;
    private EditText etNation;
    private TextView tvBirthday;
    private EditText etPlace;
    private EditText etPhone;
    private EditText etEmail;
    private LinearLayout llWomenContent;
    private EditText etMenses;
    private EditText etConceive;
    private EditText etAbortion;
    private EditText etChildbirth;
    private EditText etLiveBirth;
    private TitleBarView mTitleBar;
    private BasicinfoResponse.Basicinfo mBasicinfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_base_info;
    }

    @Override
    public void initView() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("基本信息");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText(R.string.commit);
        mTitleBar.setRightListener(v -> doCommit());

        etName = (EditText) findViewById(R.id.et_name);
        mcdSex = (MultiChoiceDialog) findViewById(R.id.mcd_sex);
        etHeight = (EditText) findViewById(R.id.et_height);
        etWeight = (EditText) findViewById(R.id.et_weight);
        mcdMarry = (MultiChoiceDialog) findViewById(R.id.mcd_marry);
        etNation = (EditText) findViewById(R.id.et_nation);
        tvBirthday = (TextView) findViewById(R.id.tv_birthday);
        etPlace = (EditText) findViewById(R.id.et_place);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        llWomenContent = (LinearLayout) findViewById(R.id.ll_women_content);
        etMenses = (EditText) findViewById(R.id.et_menses);
        etConceive = (EditText) findViewById(R.id.et_conceive);
        etAbortion = (EditText) findViewById(R.id.et_abortion);
        etChildbirth = (EditText) findViewById(R.id.et_childbirth);
        etLiveBirth = (EditText) findViewById(R.id.et_live_birth);

        mcdSex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyString = mcdSex.getKeyString();
                UtilsUi.setVisibility(llWomenContent, Constants.SEX_WOMAN.equals(keyString));
            }
        });
        tvBirthday.setOnClickListener(v ->
                UtilsDate.showDateChoice(mActivity, tvBirthday, Calendar.getInstance().getTimeInMillis()));
    }

    private void doCommit() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        String name = etName.getText().toString().trim();
        String sex = mcdSex.getKeyString();
        String marry = mcdMarry.getKeyString();
        String height = etHeight.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String nation = etNation.getText().toString().trim();
        String birthday = tvBirthday.getText().toString().trim();
        String place = etPlace.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String menses = etMenses.getText().toString().trim();
        String conceive = etConceive.getText().toString().trim();
        String abortion = etAbortion.getText().toString().trim();
        String childbirth = etChildbirth.getText().toString().trim();
        String liveBirth = etLiveBirth.getText().toString().trim();

        if (UtilsString.checkEmpty(mActivity, name, "请输入姓名") ||
                UtilsString.checkEmpty(mActivity, sex, "请选择性别") ||
                UtilsString.checkEmpty(mActivity, height, "请输入身高") ||
                UtilsString.checkEmpty(mActivity, weight, "请输入体重") ||
                UtilsString.checkEmpty(mActivity, marry, "请选择婚姻状况") ||
                UtilsString.checkEmpty(mActivity, nation, "请输入民族") ||
                UtilsString.checkEmpty(mActivity, birthday, "请选择出生日期")) {
            return;
        }

        BasicinfoRequest request = new BasicinfoRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setName(name);
        request.setSex(sex);
        request.setMarital(marry);
        request.setHeight(height);
        request.setWeight(weight);
        request.setNation(nation);
        request.setBirthday(birthday);
        request.setOriginplace(place);
        request.setPhone(phone);
        request.setEmail(email);
        request.setMenstruation(menses);
        request.setPregnant(conceive);
        request.setAbortion(abortion);
        request.setChildbirth(childbirth);
        request.setLivebirth(liveBirth);
        if (null == mBasicinfo || TextUtils.isEmpty(mBasicinfo.getPkBasicinfo())) {
            Observable<BasicinfoResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .basicinfoSaveRequest(request);
            RetrofitUtils.request(mActivity, ob, mTitleBar.getRightView(),
                    new RetrofitUtils.ResponseListener<BasicinfoResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(BasicinfoResponse response) {
                            saveSuccess(response);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onFinish(boolean isSuccess) {
                            hidenProgressDialog();
                        }
                    });
        } else {
            request.setPkBasicinfo(mBasicinfo.getPkBasicinfo());
            Observable<BasicinfoResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .basicinfoUpdateRequest(request);
            RetrofitUtils.request(mActivity, ob, mTitleBar.getRightView(),
                    new RetrofitUtils.ResponseListener<BasicinfoResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(BasicinfoResponse response) {
                            saveSuccess(response);
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
    }

    private void saveSuccess(BasicinfoResponse response) {
        HealthManager.storeBaseHealthInfo(response);
        AppToast.show(mActivity, R.string.toast_show_success);
        JumpManager.doJumpBack(mActivity);
    }

    @Override
    public void initData() {
        requestBaseHealth();
    }

    private void requestBaseHealth() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        BasicinfoDetailsRequest request = new BasicinfoDetailsRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<BasicinfoResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .basicinfoDetailsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<BasicinfoResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(BasicinfoResponse response) {
                        HealthManager.storeBaseHealthInfo(response);
                        fillView(response);
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

    private void fillView(BasicinfoResponse response) {
        mBasicinfo = response.getDatas();
        etName.setText(mBasicinfo.getName());
        mcdSex.setKeyString(mBasicinfo.getSex());
        etHeight.setText(mBasicinfo.getHeight());
        etWeight.setText(mBasicinfo.getWeight());
        mcdMarry.setKeyString(mBasicinfo.getMarital());
        etNation.setText(mBasicinfo.getNation());
        tvBirthday.setText(mBasicinfo.getBirthday());
        etPlace.setText(mBasicinfo.getOriginplace());
        etPhone.setText(mBasicinfo.getPhone());
        etEmail.setText(mBasicinfo.getEmail());
        etMenses.setText(mBasicinfo.getMenstruation());
        etConceive.setText(mBasicinfo.getPregnant());
        etAbortion.setText(mBasicinfo.getAbortion());
        etChildbirth.setText(mBasicinfo.getChildbirth());
        etLiveBirth.setText(mBasicinfo.getLivebirth());
    }
}
package com.yijia.zkl.ui.activity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.SaveRequest;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppAlertDialog;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.AppSpinner;
import com.zero.library.base.view.TitleBarView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 特需——发起招募
 */
public class LaunchRecruitActivity extends DefaultActivity {

    private AppSpinner asBaseType;
    private AppSpinner asBaseSex;
    private AppSpinner asBaseMarriage;
    private EditText etBaseIndication;
    private TextView tvBaseStarttime;
    private TextView tvBaseEndtime;
    private EditText etTestObjective;
    private AppSpinner asTestType;
    private EditText etTestType;
    private EditText etTestNumber;
    private EditText etTestMethod;
    private EditText etJoinCriteria;
    private EditText etUnjoinCriteria;
    private EditText etRelevantOrganization;
    private EditText etRelevantName;
    private EditText etRelevantContactway;
    private View mTvCommit;
    private AppAlertDialog mSuccessDialog;

    @Override
    public int getResLayout() {
        return R.layout.activity_launch_recruit;
    }


    @Override
    public void initView() {
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_launch_recruit);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        asBaseType = (AppSpinner) findViewById(R.id.as_base_type);
        asBaseSex = (AppSpinner) findViewById(R.id.as_base_sex);
        asBaseMarriage = (AppSpinner) findViewById(R.id.as_base_marriage);
        etBaseIndication = (EditText) findViewById(R.id.et_base_indication);
        tvBaseStarttime = (TextView) findViewById(R.id.tv_base_starttime);
        tvBaseStarttime.setOnClickListener(v -> showDateChoice(tvBaseStarttime));
        tvBaseEndtime = (TextView) findViewById(R.id.tv_base_endtime);
        tvBaseEndtime.setOnClickListener(v -> showDateChoice(tvBaseEndtime));
        etTestObjective = (EditText) findViewById(R.id.et_test_objective);
        asTestType = (AppSpinner) findViewById(R.id.as_test_type);
        etTestType = (EditText) findViewById(R.id.et_test_type);
        etTestNumber = (EditText) findViewById(R.id.et_test_number);
        etTestMethod = (EditText) findViewById(R.id.et_test_method);
        etJoinCriteria = (EditText) findViewById(R.id.et_join_criteria);
        etUnjoinCriteria = (EditText) findViewById(R.id.et_unjoin_criteria);
        etRelevantOrganization = (EditText) findViewById(R.id.et_relevant_organization);
        etRelevantName = (EditText) findViewById(R.id.et_relevant_name);
        etRelevantContactway = (EditText) findViewById(R.id.et_relevant_contactway);
        mTvCommit = findViewById(R.id.tv_commit);
        mTvCommit.setOnClickListener(v -> commit());
    }

    @Override
    public void initData() {
        asBaseType.setList(Constants.getRecruitType());
        asBaseType.setHintText(R.string.hint_base_type);
        asBaseSex.setList(Constants.getRecruitSex());
        asBaseSex.setHintText(R.string.hint_choice_sex);
        asBaseMarriage.setList(Constants.getRecruitMarriage());
        asBaseMarriage.setHintText(R.string.hint_choice_marriage);
        asTestType.setList(Constants.getRecruitTestType());
        asTestType.setHintText(R.string.hint_choice_texttype);
    }

    private void commit() {
        String baseType = asBaseType.getCheckItem().getKey();
        String baseSex = asBaseSex.getCheckItem().getKey();
        String baseMarriage = asBaseMarriage.getCheckItem().getKey();
        String baseIndication = etBaseIndication.getText().toString().trim();
        String baseStartTime = tvBaseStarttime.getText().toString().trim();
        String baseEndTime = tvBaseEndtime.getText().toString().trim();
        String testObjcetive = etTestObjective.getText().toString().trim();
        String testType = asTestType.getCheckItem().getKey();
        String planType = etTestType.getText().toString().trim();
        String textNumber = etTestNumber.getText().toString().trim();
        String testMethod = etTestMethod.getText().toString().trim();
        String joinCriteria = etJoinCriteria.getText().toString().trim();
        String unJoinCriteria = etUnjoinCriteria.getText().toString().trim();
        String relevantOrganization = etRelevantOrganization.getText().toString().trim();
        String relevantName = etRelevantName.getText().toString().trim();
        String relevantContactway = etRelevantContactway.getText().toString().trim();
        if (UtilsString.checkEmpty(mActivity, baseType, asBaseType.getHintText()) ||
                UtilsString.checkEmpty(mActivity, baseSex, asBaseSex.getHintText()) ||
                UtilsString.checkEmpty(mActivity, baseMarriage, asBaseMarriage.getHintText()) ||
                UtilsString.checkEmpty(mActivity, etBaseIndication) ||
                UtilsString.checkEmpty(mActivity, tvBaseStarttime) ||
                UtilsString.checkEmpty(mActivity, tvBaseEndtime) ||
                UtilsString.checkEmpty(mActivity, etTestObjective) ||
                UtilsString.checkEmpty(mActivity, testType, asTestType.getHintText()) ||
                UtilsString.checkEmpty(mActivity, etTestType) ||
                UtilsString.checkEmpty(mActivity, etTestNumber) ||
                UtilsString.checkEmpty(mActivity, etTestMethod) ||
                UtilsString.checkEmpty(mActivity, etJoinCriteria) ||
                UtilsString.checkEmpty(mActivity, etUnjoinCriteria) ||
                UtilsString.checkEmpty(mActivity, etRelevantOrganization) ||
                UtilsString.checkEmpty(mActivity, etRelevantName) ||
                UtilsString.checkEmpty(mActivity, etRelevantContactway)) {
            return;
        }

        SaveRequest request = new SaveRequest();
        request.setTextType(baseType);
        request.setTextSex(baseSex);
        request.setMarriageState(baseMarriage);
        request.setDrugIndication(baseIndication);
        request.setStartDate(baseStartTime);
        request.setOverDate(baseEndTime);
        request.setTextTarget(testObjcetive);
        request.setTextClassification(testType);
        request.setDesignType(planType);
        request.setTextNumber(textNumber);
        request.setTextMethod(testMethod);
        request.setContext(joinCriteria);
        request.setJoinRequire(unJoinCriteria);
        request.setLaunchOrg(relevantOrganization);
        request.setLaunchName(relevantName);
        request.setLaunchLink(relevantContactway);

        LoginResponse loginInfo = UserManager.getLoginInfo();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).saveRequest(request);
        RetrofitUtils.request(mActivity, ob, mTvCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse defaultResponse) {
                        showSuccessDialog();
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

    private void showSuccessDialog() {
        mSuccessDialog = UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string.dialog_saverequest_success)
                , (dialog, view) -> {
                    JumpManager.doJumpBack(mActivity);
                    dialog.dismiss();
                });
        mSuccessDialog.show();
    }


    private int mYear;
    private int mMonth;
    private int mDay;

    protected void showDateChoice(TextView textView) {
        Calendar calendar = Calendar.getInstance();
        View view = View.inflate(mActivity, R.layout.dlg_choice_date, null);
        final TextView choiceDate = (TextView) view.findViewById(R.id.tv_choice_date);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.new_act_date_picker);
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DecimalFormat df = new DecimalFormat("00");
        String weakNow = UtilsDate.getWeak(new Date(mYear - 1900, mMonth, mDay));
        mMonth += 1;
        choiceDate.setText(getString(R.string.date_string, mYear, mMonth, mDay, weakNow));
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                (view1, year, monthOfYear, dayOfMonth) -> {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDay = dayOfMonth;
                    String weak = UtilsDate.getWeak(new Date(year - 1900, monthOfYear, dayOfMonth));
                    choiceDate.setText(getString(R.string.date_string, mYear, mMonth, mDay, weak));
                });
        datePicker.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.YEAR, 5);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        setPadding(datePicker);
        AppEmptyDialog dialog = new AppEmptyDialog(mActivity, 1);
        dialog.setBtnOnClickListener((dialog1, view1) -> {
            textView.setText(getString(R.string.date_format, mYear, df.format(mMonth), df.format(mDay)));
            dialog1.dismiss();
        });
        dialog.setView(view);
        dialog.show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mSuccessDialog) mSuccessDialog.dismiss();
    }
}

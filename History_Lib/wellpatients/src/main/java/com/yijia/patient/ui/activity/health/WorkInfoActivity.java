package com.yijia.patient.ui.activity.health;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.WorkinfoRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.WorkinfoListResponse;
import com.yijia.patient.ui.view.MultiChoiceDialog;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
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
 * Created by ZeroAries on 2016/6/27.
 * 工作情况
 */
public class WorkInfoActivity extends AppBaseActivity {
    private EditText etOccupation;
    private EditText etPost;
    private TextView tvWorkinghours;
    private TextView tvWorkinghours1;
    private MultiChoiceDialog mcdIswork;
    private TextView tvFrequency;
    private EditText etFrequency;

    private TextView tvOvertime;
    private EditText etOvertime;
    private EditText etDescription;
    private EditText etOtherdescription;
    private TitleBarView mTitleBar;
    private WorkinfoListResponse.ListBean mWorkInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_workinfo;
    }

    @Override
    public void initView() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("新增工作情况");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText(R.string.commit);
        mTitleBar.setRightListener(v -> doCommit());

        etOccupation = (EditText) findViewById(R.id.et_occupation);
        etPost = (EditText) findViewById(R.id.et_post);
        tvWorkinghours = (TextView) findViewById(R.id.tv_workinghours);
        tvWorkinghours1 = (TextView) findViewById(R.id.tv_workinghours1);
        mcdIswork = (MultiChoiceDialog) findViewById(R.id.mcd_iswork);
        tvFrequency = (TextView) findViewById(R.id.tv_frequency);
        etFrequency = (EditText) findViewById(R.id.et_frequency);
        tvOvertime = (TextView) findViewById(R.id.tv_overtime);
        etOvertime = (EditText) findViewById(R.id.et_overtime);
        etDescription = (EditText) findViewById(R.id.et_description);
        etOtherdescription = (EditText) findViewById(R.id.et_otherdescription);

        mcdIswork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String isWork = mcdIswork.getText().toString().trim();
                String tagIsWork = "是";
                etFrequency.setEnabled(tagIsWork.equals(isWork));
                etOvertime.setEnabled(tagIsWork.equals(isWork));
                tvFrequency.setTextColor(UtilsUi.getColor(R.color.gray_33));
                tvOvertime.setTextColor(UtilsUi.getColor(R.color.gray_33));
                if (!tagIsWork.equals(isWork)) {
                    tvFrequency.setTextColor(UtilsUi.getColor(R.color.gray_77));
                    tvOvertime.setTextColor(UtilsUi.getColor(R.color.gray_77));
                    etFrequency.setText("");
                    etOvertime.setText("");
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        tvWorkinghours.setOnClickListener(v ->
                UtilsDate.showDateChoice(mActivity, tvWorkinghours, calendar.getTimeInMillis()));
        tvWorkinghours1.setOnClickListener(v ->
                UtilsDate.showDateChoice(mActivity, tvWorkinghours1, calendar.getTimeInMillis()));
    }

    private void doCommit() {
        String occupation = etOccupation.getText().toString().trim();
        String post = etPost.getText().toString().trim();
        String workinghours = tvWorkinghours.getText().toString().trim();
        String workinghours1 = tvWorkinghours1.getText().toString().trim();
        String iswork = mcdIswork.getKeyString();
        String frequency = etFrequency.getText().toString().trim();
        String overtime = etOvertime.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String otherdescription = etOtherdescription.getText().toString().trim();

        if (UtilsString.checkEmpty(mActivity, occupation, "请输入职业") ||
                UtilsString.checkEmpty(mActivity, post, "请输入职务") ||
                UtilsString.checkEmpty(mActivity, workinghours, "请选择工作开始日期") ||
                UtilsString.checkEmpty(mActivity, workinghours1, "请选择工作结束日期")) {
            return;
        }

        WorkinfoRequest request = new WorkinfoRequest();
        request.setOccupation(occupation);
        request.setPost(post);
        request.setWorkinghours(workinghours);
        request.setWorkinghours1(workinghours1);
        request.setIswork(iswork);
        request.setFrequency(frequency);
        request.setOvertime(overtime);
        request.setDescription(description);
        request.setOtherdescription(otherdescription);

        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        request.setPkBasicinfo(basicinfo.getDatas().getPkBasicinfo());

        if (null == mWorkInfo || TextUtils.isEmpty(mWorkInfo.getPkWorkinfo())) {
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                    workinfoSaveRequest(request);
            RetrofitUtils.request(mActivity, ob, mTitleBar.getRightView(),
                    new RetrofitUtils.ResponseListener<DefaultResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(DefaultResponse response) {
                            saveSuccess();
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
            request.setPkWorkinfo(mWorkInfo.getPkWorkinfo());
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                    workinfoUpdateRequest(request);
            RetrofitUtils.request(mActivity, ob, mTitleBar.getRightView(),
                    new RetrofitUtils.ResponseListener<DefaultResponse>() {
                        @Override
                        public void beforRequest() {
                            showProgressDialog();
                        }

                        @Override
                        public void onSuccess(DefaultResponse response) {
                            saveSuccess();
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

    private void saveSuccess() {
        AppToast.show(mActivity, R.string.toast_show_success);
        JumpManager.doJumpBack(mActivity);
    }

    @Override
    public void initData() {
        mWorkInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null != mWorkInfo) fillView();
    }

    private void fillView() {
        mTitleBar.setTitle("工作情况");
        etOccupation.setText(mWorkInfo.getOccupation());
        etPost.setText(mWorkInfo.getPost());
        tvWorkinghours.setText(mWorkInfo.getWorkinghours());
        tvWorkinghours1.setText(mWorkInfo.getWorkinghours1());
        mcdIswork.setKeyString(mWorkInfo.getIswork());
        etFrequency.setText(mWorkInfo.getFrequency());
        etOvertime.setText(mWorkInfo.getOvertime());
        etDescription.setText(mWorkInfo.getDescription());
        etOtherdescription.setText(mWorkInfo.getOtherdescription());
    }
}

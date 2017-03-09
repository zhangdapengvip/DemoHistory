package com.yijia.patient.ui.activity.health;

import android.text.TextUtils;
import android.widget.EditText;

import com.yijia.patient.R;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.FamilyDiseaseInfoRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.FamilydiseaseinfoListResponse;
import com.yijia.patient.ui.protocol.response.WorkinfoListResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsString;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/27.
 * 家庭疾病史
 */
public class FamilyDiseaseInfoActivity extends AppBaseActivity {
    private TitleBarView mTitleBar;
    private EditText etDiseasename;
    private EditText etMember;
    private EditText etAge;
    private EditText etContent;
    private FamilydiseaseinfoListResponse.ListBean mFamilyDiseaseInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_family_diseaseinfo;
    }

    @Override
    public void initView() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("新增家族疾病史");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText(R.string.commit);
        mTitleBar.setRightListener(v -> doCommit());

        etDiseasename = (EditText) findViewById(R.id.et_diseasename);
        etMember = (EditText) findViewById(R.id.et_member);
        etAge = (EditText) findViewById(R.id.et_age);
        etContent = (EditText) findViewById(R.id.et_content);

    }

    private void doCommit() {
        String diseasename = etDiseasename.getText().toString().trim();
        String member = etMember.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        FamilyDiseaseInfoRequest request = new FamilyDiseaseInfoRequest();
        request.setDiseasename(diseasename);
        request.setMember(member);
        request.setAge(age);
        request.setContent(content);

        if (UtilsString.checkEmpty(mActivity, diseasename, "请输入疾病名称") ||
                UtilsString.checkEmpty(mActivity, member, "请输入患病成员") ||
                UtilsString.checkEmpty(mActivity, age, "请输入患病年龄")) {
            return;
        }

        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        request.setPkBasicinfo(basicinfo.getDatas().getPkBasicinfo());
        if (null == mFamilyDiseaseInfo || TextUtils.isEmpty(mFamilyDiseaseInfo.getPkFamilydiseaseinfo())) {
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                    familydiseaseinfoSaveRequest(request);
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
            request.setPkFamilydiseaseinfo(mFamilyDiseaseInfo.getPkFamilydiseaseinfo());
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                    familydiseaseinfoUpdateRequest(request);
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
        mFamilyDiseaseInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null != mFamilyDiseaseInfo) fillView();
    }

    private void fillView() {
        mTitleBar.setTitle("家族疾病史");
        etDiseasename.setText(mFamilyDiseaseInfo.getDiseasename());
        etMember.setText(mFamilyDiseaseInfo.getMember());
        etAge.setText(mFamilyDiseaseInfo.getAge());
        etContent.setText(mFamilyDiseaseInfo.getContent());
    }
}

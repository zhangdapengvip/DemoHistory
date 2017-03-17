package com.yijia.patient.ui.activity.health;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.DiseaseinfoRequest;
import com.yijia.patient.ui.protocol.request.InfoGetRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.DiseaseinfoResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseActivity;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/27.
 * 疾病史
 */
public class DiseaseInfoActivity extends AppBaseActivity {
    private TitleBarView mTitleBar;
    private CheckBox cbDiabetes;
    private CheckBox cbHypertension;
    private CheckBox cbBloodfat;
    private CheckBox cbHeartdisease;
    private CheckBox cbCongenitalheartdisease;
    private CheckBox cbIschemicheartdisease;
    private CheckBox cbRheumatismheartdisease;
    private CheckBox cbSvt;
    private CheckBox cbValvulardisease;
    private CheckBox cbObesity;
    private CheckBox cbMyocarditis;
    private CheckBox cbCerebralapoplexy;
    private CheckBox cbMiocardial;
    private CheckBox cbBeats;
    private CheckBox cbAtrialfibrillation;
    private EditText etOthercardiovascular;
    private CheckBox cbTuberculosis;
    private CheckBox cbPulmonarynodules;
    private EditText etOtherrespiratory;
    private CheckBox cbGastriculcer;
    private CheckBox cbHelicobacter;
    private CheckBox cbGastricpolyp;
    private CheckBox cbAtrophicgastritis;
    private CheckBox cbIntestinalpolyp;
    private CheckBox cbHepatitis;
    private CheckBox cbUlcerativecolitis;
    private EditText etOtherdigestion;
    private CheckBox cbNephritis;
    private CheckBox cbRenalcysts;
    private EditText etOtherkidney;
    private CheckBox cbCervical;
    private CheckBox cbHyperplasiacervical;
    private CheckBox cbHysteromyoma;
    private CheckBox cbHpv;
    private CheckBox cbProstatic;
    private CheckBox cbProstatitis;
    private CheckBox cbProstaticnodule;
    private EditText etOtherreproductive;
    private CheckBox cbMastopathy;
    private CheckBox cbMasthyperplasia;
    private CheckBox cbAdenofibroma;
    private EditText etOtherbreast;
    private CheckBox cbDrugallergy;
    private CheckBox cbSurgery;
    private EditText etDrugname;
    private EditText etSurgeryname;
    private DiseaseinfoResponse.DiseaseInfo mDiseaseInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_disease_info;
    }


    @Override
    public void initView() {
        assignViews();
        mTitleBar.setTitle("疾病史");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText(R.string.commit);
        mTitleBar.setRightListener(v -> doCommit());
    }

    private void doCommit() {
        String othercardiovascular = etOthercardiovascular.getText().toString().trim();
        String otherrespiratory = etOtherrespiratory.getText().toString().trim();
        String otherdigestion = etOtherdigestion.getText().toString().trim();
        String otherkidney = etOtherkidney.getText().toString().trim();
        String otherreproductive = etOtherreproductive.getText().toString().trim();
        String otherbreast = etOtherbreast.getText().toString().trim();
        String drugname = etDrugname.getText().toString().trim();
        String surgeryname = etSurgeryname.getText().toString().trim();

        DiseaseinfoRequest request = new DiseaseinfoRequest();
        request.setDiabetes(checkToString(cbDiabetes));
        request.setHypertension(checkToString(cbHypertension));
        request.setBloodfat(checkToString(cbBloodfat));
        request.setHeartdisease(checkToString(cbHeartdisease));
        request.setCongenitalheartdisease(checkToString(cbCongenitalheartdisease));
        request.setIschemicheartdisease(checkToString(cbIschemicheartdisease));
        request.setRheumatismheartdisease(checkToString(cbRheumatismheartdisease));
        request.setSvt(checkToString(cbSvt));
        request.setValvulardisease(checkToString(cbValvulardisease));
        request.setObesity(checkToString(cbObesity));
        request.setMyocarditis(checkToString(cbMyocarditis));
        request.setCerebralapoplexy(checkToString(cbCerebralapoplexy));
        request.setMiocardial(checkToString(cbMiocardial));
        request.setBeats(checkToString(cbBeats));
        request.setAtrialfibrillation(checkToString(cbAtrialfibrillation));
        request.setTuberculosis(checkToString(cbTuberculosis));
        request.setPulmonarynodules(checkToString(cbPulmonarynodules));
        request.setGastriculcer(checkToString(cbGastriculcer));
        request.setHelicobacter(checkToString(cbHelicobacter));
        request.setGastricpolyp(checkToString(cbGastricpolyp));
        request.setAtrophicgastritis(checkToString(cbAtrophicgastritis));
        request.setIntestinalpolyp(checkToString(cbIntestinalpolyp));
        request.setHepatitis(checkToString(cbHepatitis));
        request.setUlcerativecolitis(checkToString(cbUlcerativecolitis));
        request.setNephritis(checkToString(cbNephritis));
        request.setRenalcysts(checkToString(cbRenalcysts));
        request.setCervical(checkToString(cbCervical));
        request.setHyperplasiacervical(checkToString(cbHyperplasiacervical));
        request.setHysteromyoma(checkToString(cbHysteromyoma));
        request.setHpv(checkToString(cbHpv));
        request.setProstatic(checkToString(cbProstatic));
        request.setProstatitis(checkToString(cbProstatitis));
        request.setProstaticnodule(checkToString(cbProstaticnodule));
        request.setMastopathy(checkToString(cbMastopathy));
        request.setMasthyperplasia(checkToString(cbMasthyperplasia));
        request.setAdenofibroma(checkToString(cbAdenofibroma));
        request.setDrugallergy(checkToString(cbDrugallergy));
        request.setSurgery(checkToString(cbSurgery));
        //设置输入项
        request.setOthercardiovascular(othercardiovascular);
        request.setOtherrespiratory(otherrespiratory);
        request.setOtherdigestion(otherdigestion);
        request.setOtherkidney(otherkidney);
        request.setOtherreproductive(otherreproductive);
        request.setOtherbreast(otherbreast);
        request.setDrugname(drugname);
        request.setSurgeryname(surgeryname);

        BasicinfoResponse basicInfo = HealthManager.obtainBaseHealthInfo();
        request.setPkBasicinfo(basicInfo.getDatas().getPkBasicinfo());
        if (null == mDiseaseInfo || TextUtils.isEmpty(mDiseaseInfo.getPkDiseaseinfo())) {
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .diseaseinfoSaveRequest(request);
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
            request.setPkDiseaseinfo(mDiseaseInfo.getPkDiseaseinfo());
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .diseaseinfoUpdateRequest(request);
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
        requestDiseaseInfo();
    }

    private void requestDiseaseInfo() {
        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        InfoGetRequest request = new InfoGetRequest();
        request.setPkBasicinfo(basicinfo.getDatas().getPkBasicinfo());
        Observable<DiseaseinfoResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .diseaseinfoDetailsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DiseaseinfoResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(DiseaseinfoResponse response) {
                        fillView(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        JumpManager.doJumpBack(mActivity);
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    private void fillView(DiseaseinfoResponse response) {
        mDiseaseInfo = response.getDatas();
        stringToCheck(cbDiabetes, mDiseaseInfo.getDiabetes());
        stringToCheck(cbHypertension, mDiseaseInfo.getHypertension());
        stringToCheck(cbBloodfat, mDiseaseInfo.getHeartdisease());
        stringToCheck(cbCongenitalheartdisease, mDiseaseInfo.getCongenitalheartdisease());
        stringToCheck(cbIschemicheartdisease, mDiseaseInfo.getIschemicheartdisease());
        stringToCheck(cbRheumatismheartdisease, mDiseaseInfo.getRheumatismheartdisease());
        stringToCheck(cbSvt, mDiseaseInfo.getSvt());
        stringToCheck(cbValvulardisease, mDiseaseInfo.getValvulardisease());
        stringToCheck(cbObesity, mDiseaseInfo.getObesity());
        stringToCheck(cbMyocarditis, mDiseaseInfo.getMyocarditis());
        stringToCheck(cbCerebralapoplexy, mDiseaseInfo.getCerebralapoplexy());
        stringToCheck(cbMiocardial, mDiseaseInfo.getMiocardial());
        stringToCheck(cbBeats, mDiseaseInfo.getBeats());
        stringToCheck(cbAtrialfibrillation, mDiseaseInfo.getAtrialfibrillation());
        stringToCheck(cbTuberculosis, mDiseaseInfo.getTuberculosis());
        stringToCheck(cbPulmonarynodules, mDiseaseInfo.getPulmonarynodules());
        stringToCheck(cbGastriculcer, mDiseaseInfo.getGastriculcer());
        stringToCheck(cbHelicobacter, mDiseaseInfo.getHelicobacter());
        stringToCheck(cbGastricpolyp, mDiseaseInfo.getGastricpolyp());
        stringToCheck(cbAtrophicgastritis, mDiseaseInfo.getAtrophicgastritis());
        stringToCheck(cbIntestinalpolyp, mDiseaseInfo.getIntestinalpolyp());
        stringToCheck(cbHepatitis, mDiseaseInfo.getHepatitis());
        stringToCheck(cbUlcerativecolitis, mDiseaseInfo.getUlcerativecolitis());
        stringToCheck(cbNephritis, mDiseaseInfo.getNephritis());
        stringToCheck(cbRenalcysts, mDiseaseInfo.getRenalcysts());
        stringToCheck(cbCervical, mDiseaseInfo.getCervical());
        stringToCheck(cbHyperplasiacervical, mDiseaseInfo.getHyperplasiacervical());
        stringToCheck(cbHysteromyoma, mDiseaseInfo.getHysteromyoma());
        stringToCheck(cbHpv, mDiseaseInfo.getHpv());
        stringToCheck(cbProstatic, mDiseaseInfo.getProstatic());
        stringToCheck(cbProstatitis, mDiseaseInfo.getProstatitis());
        stringToCheck(cbProstaticnodule, mDiseaseInfo.getProstaticnodule());
        stringToCheck(cbMastopathy, mDiseaseInfo.getMastopathy());
        stringToCheck(cbMasthyperplasia, mDiseaseInfo.getMasthyperplasia());
        stringToCheck(cbAdenofibroma, mDiseaseInfo.getAdenofibroma());
        stringToCheck(cbDrugallergy, mDiseaseInfo.getDrugallergy());
        stringToCheck(cbSurgery, mDiseaseInfo.getSurgery());
        etOthercardiovascular.setText(mDiseaseInfo.getOthercardiovascular());
        etOtherrespiratory.setText(mDiseaseInfo.getOtherrespiratory());
        etOtherdigestion.setText(mDiseaseInfo.getOtherdigestion());
        etOtherkidney.setText(mDiseaseInfo.getOtherkidney());
        etOtherreproductive.setText(mDiseaseInfo.getOtherreproductive());
        etOtherbreast.setText(mDiseaseInfo.getOtherbreast());
        etDrugname.setText(mDiseaseInfo.getDrugname());
        etSurgeryname.setText(mDiseaseInfo.getSurgeryname());
    }

    private String checkToString(CheckBox checkBox) {
        return checkBox.isChecked() ? Constants.CHECK : Constants.UNCHECK;
    }

    private void stringToCheck(CheckBox checkBox, String checkStr) {
        checkBox.setChecked(Constants.CHECK.equals(checkStr));
    }

    private void assignViews() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        TextView tvTabone = (TextView) findViewById(R.id.tv_tabone);
        LinearLayout llContentOne = (LinearLayout) findViewById(R.id.ll_content_one);
        cbDiabetes = (CheckBox) findViewById(R.id.cb_diabetes);
        cbHypertension = (CheckBox) findViewById(R.id.cb_hypertension);
        cbBloodfat = (CheckBox) findViewById(R.id.cb_bloodfat);
        cbHeartdisease = (CheckBox) findViewById(R.id.cb_heartdisease);
        cbCongenitalheartdisease = (CheckBox) findViewById(R.id.cb_congenitalheartdisease);
        cbIschemicheartdisease = (CheckBox) findViewById(R.id.cb_ischemicheartdisease);
        cbRheumatismheartdisease = (CheckBox) findViewById(R.id.cb_rheumatismheartdisease);
        cbSvt = (CheckBox) findViewById(R.id.cb_svt);
        cbValvulardisease = (CheckBox) findViewById(R.id.cb_valvulardisease);
        cbObesity = (CheckBox) findViewById(R.id.cb_obesity);
        cbMyocarditis = (CheckBox) findViewById(R.id.cb_myocarditis);
        cbCerebralapoplexy = (CheckBox) findViewById(R.id.cb_cerebralapoplexy);
        cbMiocardial = (CheckBox) findViewById(R.id.cb_miocardial);
        cbBeats = (CheckBox) findViewById(R.id.cb_beats);
        cbAtrialfibrillation = (CheckBox) findViewById(R.id.cb_atrialfibrillation);
        etOthercardiovascular = (EditText) findViewById(R.id.et_othercardiovascular);
        TextView tvTabtwo = (TextView) findViewById(R.id.tv_tabtwo);
        LinearLayout llContentTwo = (LinearLayout) findViewById(R.id.ll_content_two);
        cbTuberculosis = (CheckBox) findViewById(R.id.cb_tuberculosis);
        cbPulmonarynodules = (CheckBox) findViewById(R.id.cb_pulmonarynodules);
        etOtherrespiratory = (EditText) findViewById(R.id.et_otherrespiratory);
        TextView tvTabthree = (TextView) findViewById(R.id.tv_tabthree);
        LinearLayout llContentThree = (LinearLayout) findViewById(R.id.ll_content_three);
        cbGastriculcer = (CheckBox) findViewById(R.id.cb_gastriculcer);
        cbHelicobacter = (CheckBox) findViewById(R.id.cb_helicobacter);
        cbGastricpolyp = (CheckBox) findViewById(R.id.cb_gastricpolyp);
        cbAtrophicgastritis = (CheckBox) findViewById(R.id.cb_atrophicgastritis);
        cbIntestinalpolyp = (CheckBox) findViewById(R.id.cb_intestinalpolyp);
        cbHepatitis = (CheckBox) findViewById(R.id.cb_hepatitis);
        cbUlcerativecolitis = (CheckBox) findViewById(R.id.cb_ulcerativecolitis);
        etOtherdigestion = (EditText) findViewById(R.id.et_otherdigestion);
        TextView tvTabfour = (TextView) findViewById(R.id.tv_tabfour);
        LinearLayout llContentFour = (LinearLayout) findViewById(R.id.ll_content_four);
        cbNephritis = (CheckBox) findViewById(R.id.cb_nephritis);
        cbRenalcysts = (CheckBox) findViewById(R.id.cb_renalcysts);
        etOtherkidney = (EditText) findViewById(R.id.et_otherkidney);
        TextView tvTabfive = (TextView) findViewById(R.id.tv_tabfive);
        LinearLayout llContentFive = (LinearLayout) findViewById(R.id.ll_content_five);
        cbCervical = (CheckBox) findViewById(R.id.cb_cervical);
        cbHyperplasiacervical = (CheckBox) findViewById(R.id.cb_hyperplasiacervical);
        cbHysteromyoma = (CheckBox) findViewById(R.id.cb_hysteromyoma);
        cbHpv = (CheckBox) findViewById(R.id.cb_hpv);
        cbProstatic = (CheckBox) findViewById(R.id.cb_prostatic);
        cbProstatitis = (CheckBox) findViewById(R.id.cb_prostatitis);
        cbProstaticnodule = (CheckBox) findViewById(R.id.cb_prostaticnodule);
        etOtherreproductive = (EditText) findViewById(R.id.et_otherreproductive);
        TextView tvTabsix = (TextView) findViewById(R.id.tv_tabsix);
        LinearLayout llContentSix = (LinearLayout) findViewById(R.id.ll_content_six);
        cbMastopathy = (CheckBox) findViewById(R.id.cb_mastopathy);
        cbMasthyperplasia = (CheckBox) findViewById(R.id.cb_masthyperplasia);
        cbAdenofibroma = (CheckBox) findViewById(R.id.cb_adenofibroma);
        etOtherbreast = (EditText) findViewById(R.id.et_otherbreast);
        TextView tvTabseven = (TextView) findViewById(R.id.tv_tabseven);
        LinearLayout llContentSeven = (LinearLayout) findViewById(R.id.ll_content_seven);
        cbDrugallergy = (CheckBox) findViewById(R.id.cb_drugallergy);
        etDrugname = (EditText) findViewById(R.id.et_drugname);
        TextView tvTabeight = (TextView) findViewById(R.id.tv_tabeight);
        LinearLayout llContentEight = (LinearLayout) findViewById(R.id.ll_content_eight);
        cbSurgery = (CheckBox) findViewById(R.id.cb_surgery);
        etSurgeryname = (EditText) findViewById(R.id.et_surgeryname);
        TextView[] tvArr = new TextView[]{
                tvTabone, tvTabtwo, tvTabthree, tvTabfour,
                tvTabfive, tvTabsix, tvTabseven, tvTabeight
        };
        LinearLayout[] llArr = new LinearLayout[]{
                llContentOne, llContentTwo, llContentThree, llContentFour,
                llContentFive, llContentSix, llContentSeven, llContentEight
        };
        for (int index = 0; index < tvArr.length; index++) {
            LinearLayout indexContent = llArr[index];
            tvArr[index].setOnClickListener(v ->
                    UtilsUi.setVisibility(indexContent, indexContent.getVisibility() == View.GONE));
        }
    }
}

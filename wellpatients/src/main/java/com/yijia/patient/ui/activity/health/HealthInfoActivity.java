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
import com.yijia.patient.ui.protocol.request.HealthyinfoRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.HealthyInfo;
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
 * Created by ZeroAries on 2016/6/23.
 * 添加健康信息
 */
public class HealthInfoActivity extends AppBaseActivity {
    private TitleBarView mTitleBar;

    private EditText etHypotension;
    private EditText etHypertension;
    private EditText etBloodfat;
    private EditText etBloodsugar;
    private CheckBox cbIshot;
    private CheckBox cbIsonlyhot;
    private CheckBox cbIssalty;
    private CheckBox cbIsonlysalty;
    private CheckBox cbIssweet;
    private CheckBox cbIsonlysweet;
    private CheckBox cbFat;
    private CheckBox cbDietlight;
    private EditText etOtherflavors;
    private CheckBox cbCooking;
    private CheckBox cbKuaichao;
    private CheckBox cbShaokao;
    private CheckBox cbStew;
    private CheckBox cbHalogen;
    private CheckBox cbVegetableoil;
    private CheckBox cbBlendoil;
    private CheckBox cbAnimaloil;
    private CheckBox cbMixedoil;
    private EditText etMixedoilratio;
    private EditText etDrinkamount1;
    private EditText etFrequency1;
    private EditText etDrinkamount2;
    private EditText etFrequency2;
    private EditText etDrinkamount3;
    private EditText etFrequency3;
    private MultiChoiceDialog mcdTeatype;
    private MultiChoiceDialog mcdTeachroma;
    private EditText etDrinkamount4;
    private EditText etFrequency4;
    private MultiChoiceDialog mcdWinetype;
    private CheckBox cbIssmoking;
    private EditText etSmokingfrequency2;
    private EditText etSmokingfrequency1;
    private EditText etSmokingoften;
    private TextView tvSmokingdate;
    private TextView tvSmokcessationdate;
    private CheckBox cbSleeppattern;
    private MultiChoiceDialog mcdSleepquality;
    private TextView tvEarlysleeptime;
    private TextView tvLatesleeptime;
    private MultiChoiceDialog mcdInsomnia;
    private MultiChoiceDialog mcdBoilnight;
    private MultiChoiceDialog mcdEnergy;
    private EditText etOtherenergy;
    private CheckBox cbFeelingnormal;
    private CheckBox cbEasytension;
    private CheckBox cbEasyanxiety;
    private CheckBox cbEasydepressed;
    private CheckBox cbEasyangry;
    private CheckBox cbEasyexcited;
    private CheckBox cbEasyemotionallow;
    private CheckBox cbPressure;
    private CheckBox cbOppressive;
    private EditText etOtherfeeling;
    private CheckBox cbHighintensity1;
    private EditText etActivityname1;
    private CheckBox cbMediumintensity1;
    private EditText etActivityname2;
    private CheckBox cbLight1;
    private EditText etActivityname3;
    private MultiChoiceDialog mcdActivityfrequency;
    private CheckBox cbHighintensity2;
    private EditText etExercisename1;
    private CheckBox cbMediumintensity2;
    private EditText etExercisename2;
    private CheckBox cbLight2;
    private EditText etExercisename3;
    private MultiChoiceDialog mcdExercisefrequency;

    private HealthyInfo mHealthyInfo;

    @Override
    public int getResLayout() {
        return R.layout.activity_healthinfo;
    }

    @Override
    public void initView() {
        mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("健康信息");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText(R.string.commit);
        mTitleBar.setRightListener(v -> commitInfo());
        assignViews();
    }

    private void commitInfo() {
        String hypotension = etHypotension.getText().toString().trim();
        String hypertension = etHypertension.getText().toString().trim();
        String bloodsugar = etBloodsugar.getText().toString().trim();
        String bloodfat = etBloodfat.getText().toString().trim();
        String ishot = checkToString(cbIshot);
        String isonlyhot = checkToString(cbIsonlyhot);
        String issalty = checkToString(cbIssalty);
        String isonlysalty = checkToString(cbIsonlysalty);
        String issweet = checkToString(cbIssweet);
        String isonlysweet = checkToString(cbIsonlysweet);
        String fat = checkToString(cbFat);
        String dietlight = checkToString(cbDietlight);
        String otherflavors = etOtherflavors.getText().toString().trim();
        String cooking = checkToString(cbCooking);
        String kuaichao = checkToString(cbKuaichao);
        String shaokao = checkToString(cbShaokao);
        String stew = checkToString(cbStew);
        String halogen = checkToString(cbHalogen);
        String vegetableoil = checkToString(cbVegetableoil);
        String blendoil = checkToString(cbBlendoil);
        String animaloil = checkToString(cbAnimaloil);
        String mixedoil = checkToString(cbMixedoil);
        String mixedoilratio = etMixedoilratio.getText().toString().trim();
        String drinkamount1 = etDrinkamount1.getText().toString().trim();
        String frequency1 = etFrequency1.getText().toString().trim();
        String drinkamount2 = etDrinkamount2.getText().toString().trim();
        String frequency2 = etFrequency2.getText().toString().trim();
        String drinkamount3 = etDrinkamount3.getText().toString().trim();
        String frequency3 = etFrequency3.getText().toString().trim();
        String teatype = mcdTeachroma.getKeyString();
        String teachroma = mcdTeachroma.getKeyString();
        String drinkamount4 = etDrinkamount4.getText().toString().trim();
        String frequency4 = etFrequency4.getText().toString().trim();
        String winetype = mcdWinetype.getKeyString();
        String issmoking = checkToString(cbIssmoking);
        String smokingfrequency2 = etSmokingfrequency2.getText().toString().trim();
        String smokingfrequency1 = etSmokingfrequency1.getText().toString().trim();
        String smokingoften = etSmokingoften.getText().toString().trim();
        String smokingdate = tvSmokingdate.getText().toString().trim();
        String smokcessationdate = tvSmokcessationdate.getText().toString().trim();
        String sleeppattern = checkToString(cbSleeppattern);
        String sleepquality = mcdSleepquality.getKeyString();
        String earlysleeptime = tvEarlysleeptime.getText().toString().trim();
        String latesleeptime = tvLatesleeptime.getText().toString().trim();
        String insomnia = mcdInsomnia.getKeyString();
        String boilnight = mcdBoilnight.getKeyString();
        String energy = mcdEnergy.getKeyString();
        String otherenergy = etOtherenergy.getText().toString().trim();
        String feelingnormal = checkToString(cbFeelingnormal);
        String easytension = checkToString(cbEasytension);
        String easyanxiety = checkToString(cbEasyanxiety);
        String easydepressed = checkToString(cbEasydepressed);
        String easyangry = checkToString(cbEasyangry);
        String easyexcited = checkToString(cbEasyexcited);
        String easyemotionallow = checkToString(cbEasyemotionallow);
        String pressure = checkToString(cbPressure);
        String oppressive = checkToString(cbOppressive);
        String otherfeeling = etOtherfeeling.getText().toString().trim();
        String highintensity1 = checkToString(cbHighintensity1);
        String activityname1 = etActivityname1.getText().toString().trim();
        String mediumintensity1 = checkToString(cbMediumintensity1);
        String activityname2 = etActivityname2.getText().toString().trim();
        String light1 = checkToString(cbLight1);
        String activityname3 = etActivityname3.getText().toString().trim();
        String activityfrequency = mcdActivityfrequency.getKeyString();
        String highintensity2 = checkToString(cbHighintensity2);
        String exercisename1 = etExercisename1.getText().toString().trim();
        String mediumintensity2 = checkToString(cbMediumintensity2);
        String exercisename2 = etExercisename2.getText().toString().trim();
        String light2 = checkToString(cbLight2);
        String exercisename3 = etExercisename3.getText().toString().trim();
        String exercisefrequency = mcdExercisefrequency.getKeyString();

        if (UtilsString.checkEmpty(mActivity, hypotension, "请输入低压值") ||
                UtilsString.checkEmpty(mActivity, hypertension, "请输入高压值") ||
                UtilsString.checkEmpty(mActivity, bloodsugar, "请输入血糖值") ||
                UtilsString.checkEmpty(mActivity, bloodfat, "请输入血脂值")) {
            return;
        }

        HealthyinfoRequest request = new HealthyinfoRequest();
        request.setHypotension(hypotension);
        request.setHypertension(hypertension);
        request.setBloodsugar(bloodsugar);
        request.setBloodfat(bloodfat);
        request.setIshot(ishot);
        request.setIsonlyhot(isonlyhot);
        request.setIssalty(issalty);
        request.setIsonlysalty(isonlysalty);
        request.setIssweet(issweet);
        request.setIsonlysweet(isonlysweet);
        request.setFat(fat);
        request.setDietlight(dietlight);
        request.setOtherflavors(otherflavors);
        request.setCooking(cooking);
        request.setKuaichao(kuaichao);
        request.setShaokao(shaokao);
        request.setStew(stew);
        request.setHalogen(halogen);
        request.setVegetableoil(vegetableoil);
        request.setBlendoil(blendoil);
        request.setAnimaloil(animaloil);
        request.setMixedoil(mixedoil);
        request.setMixedoilratio(mixedoilratio);
        request.setDrinkamount1(drinkamount1);
        request.setFrequency1(frequency1);
        request.setDrinkamount2(drinkamount2);
        request.setFrequency2(frequency2);
        request.setDrinkamount3(drinkamount3);
        request.setFrequency3(frequency3);
        request.setTeatype(teatype);
        request.setTeachroma(teachroma);
        request.setDrinkamount4(drinkamount4);
        request.setFrequency4(frequency4);
        request.setWinetype(winetype);
        request.setIssmoking(issmoking);
        request.setSmokingfrequency2(smokingfrequency2);
        request.setSmokingfrequency1(smokingfrequency1);
        request.setSmokingoften(smokingoften);
        request.setSmokingdate(smokingdate);
        request.setSmokcessationdate(smokcessationdate);
        request.setSleeppattern(sleeppattern);
        request.setSleepquality(sleepquality);
        request.setEarlysleeptime(earlysleeptime);
        request.setLatesleeptime(latesleeptime);
        request.setInsomnia(insomnia);
        request.setBoilnight(boilnight);
        request.setEnergy(energy);
        request.setOtherenergy(otherenergy);
        request.setFeelingnormal(feelingnormal);
        request.setEasytension(easytension);
        request.setEasyanxiety(easyanxiety);
        request.setEasydepressed(easydepressed);
        request.setEasyangry(easyangry);
        request.setEasyexcited(easyexcited);
        request.setEasyemotionallow(easyemotionallow);
        request.setPressure(pressure);
        request.setOppressive(oppressive);
        request.setOtherfeeling(otherfeeling);
        request.setHighintensity1(highintensity1);
        request.setActivityname1(activityname1);
        request.setMediumintensity1(mediumintensity1);
        request.setActivityname2(activityname2);
        request.setLight1(light1);
        request.setActivityname3(activityname3);
        request.setActivityfrequency(activityfrequency);
        request.setHighintensity2(highintensity2);
        request.setExercisename1(exercisename1);
        request.setMediumintensity2(mediumintensity2);
        request.setExercisename2(exercisename2);
        request.setLight2(light2);
        request.setExercisename3(exercisename3);
        request.setExercisefrequency(exercisefrequency);

        BasicinfoResponse basicInfo = HealthManager.obtainBaseHealthInfo();
        request.setPkBasicinfo(basicInfo.getDatas().getPkBasicinfo());

        if (null == mHealthyInfo || TextUtils.isEmpty(mHealthyInfo.getPkHealthyinfo())) {
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .healthyinfoSaveRequest(request);
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
            request.setPkHealthyinfo(mHealthyInfo.getPkHealthyinfo());
            Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                    .healthyinfoUpdateRequest(request);
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
        mHealthyInfo = getIntent().getParcelableExtra(AppConstants.PARCELABLE_KEY);
        if (null != mHealthyInfo) fillView();
    }

    private void fillView() {
        etHypotension.setText(mHealthyInfo.getHypotension());
        etHypertension.setText(mHealthyInfo.getHypertension());
        etBloodsugar.setText(mHealthyInfo.getBloodsugar());
        etBloodfat.setText(mHealthyInfo.getBloodfat());
        stringToCheck(cbIshot, mHealthyInfo.getIshot());
        stringToCheck(cbIsonlyhot, mHealthyInfo.getIsonlyhot());
        stringToCheck(cbIssalty, mHealthyInfo.getIssalty());
        stringToCheck(cbIsonlysalty, mHealthyInfo.getIsonlysalty());
        stringToCheck(cbIssweet, mHealthyInfo.getIssweet());
        stringToCheck(cbIsonlysweet, mHealthyInfo.getIsonlysweet());
        stringToCheck(cbFat, mHealthyInfo.getFat());
        stringToCheck(cbDietlight, mHealthyInfo.getDietlight());
        etOtherflavors.setText(mHealthyInfo.getOtherflavors());
        stringToCheck(cbCooking, mHealthyInfo.getCooking());
        stringToCheck(cbKuaichao, mHealthyInfo.getKuaichao());
        stringToCheck(cbShaokao, mHealthyInfo.getShaokao());
        stringToCheck(cbStew, mHealthyInfo.getStew());
        stringToCheck(cbHalogen, mHealthyInfo.getHalogen());
        stringToCheck(cbVegetableoil, mHealthyInfo.getVegetableoil());
        stringToCheck(cbBlendoil, mHealthyInfo.getBlendoil());
        stringToCheck(cbAnimaloil, mHealthyInfo.getAnimaloil());
        stringToCheck(cbMixedoil, mHealthyInfo.getMixedoil());
        etMixedoilratio.setText(mHealthyInfo.getMixedoilratio());
        etDrinkamount1.setText(mHealthyInfo.getDrinkamount1());
        etFrequency1.setText(mHealthyInfo.getFrequency1());
        etDrinkamount2.setText(mHealthyInfo.getDrinkamount2());
        etFrequency2.setText(mHealthyInfo.getFrequency2());
        etDrinkamount3.setText(mHealthyInfo.getDrinkamount3());
        etFrequency3.setText(mHealthyInfo.getFrequency3());
        mcdTeatype.setKeyString(mHealthyInfo.getTeatype());
        mcdTeachroma.setKeyString(mHealthyInfo.getTeachroma());
        etDrinkamount4.setText(mHealthyInfo.getDrinkamount4());
        etFrequency4.setText(mHealthyInfo.getFrequency4());
        mcdWinetype.setKeyString(mHealthyInfo.getWinetype());
        stringToCheck(cbIssmoking, mHealthyInfo.getIssmoking());
        etSmokingfrequency2.setText(mHealthyInfo.getSmokingfrequency2());
        etSmokingfrequency1.setText(mHealthyInfo.getSmokingfrequency1());
        etSmokingoften.setText(mHealthyInfo.getSmokingoften());
        tvSmokingdate.setText(mHealthyInfo.getSmokingdate());
        tvSmokcessationdate.setText(mHealthyInfo.getSmokcessationdate());
        stringToCheck(cbSleeppattern, mHealthyInfo.getSleeppattern());
        mcdSleepquality.setKeyString(mHealthyInfo.getSleepquality());
        tvEarlysleeptime.setText(mHealthyInfo.getEarlysleeptime());
        tvLatesleeptime.setText(mHealthyInfo.getLatesleeptime());
        mcdInsomnia.setKeyString(mHealthyInfo.getInsomnia());
        mcdBoilnight.setKeyString(mHealthyInfo.getBoilnight());
        mcdEnergy.setKeyString(mHealthyInfo.getEnergy());
        etOtherenergy.setText(mHealthyInfo.getOtherenergy());
        stringToCheck(cbFeelingnormal, mHealthyInfo.getFeelingnormal());
        stringToCheck(cbEasytension, mHealthyInfo.getEasytension());
        stringToCheck(cbEasyanxiety, mHealthyInfo.getEasyanxiety());
        stringToCheck(cbEasydepressed, mHealthyInfo.getEasydepressed());
        stringToCheck(cbEasyangry, mHealthyInfo.getEasyangry());
        stringToCheck(cbEasyexcited, mHealthyInfo.getEasyexcited());
        stringToCheck(cbEasyemotionallow, mHealthyInfo.getEasyemotionallow());
        stringToCheck(cbPressure, mHealthyInfo.getPressure());
        stringToCheck(cbOppressive, mHealthyInfo.getOppressive());
        etOtherfeeling.setText(mHealthyInfo.getOtherfeeling());
        stringToCheck(cbHighintensity1, mHealthyInfo.getHighintensity1());
        etActivityname1.setText(mHealthyInfo.getActivityname1());
        stringToCheck(cbMediumintensity1, mHealthyInfo.getMediumintensity1());
        etActivityname2.setText(mHealthyInfo.getActivityname2());
        stringToCheck(cbLight1, mHealthyInfo.getLight1());
        etActivityname3.setText(mHealthyInfo.getActivityname3());
        mcdActivityfrequency.setKeyString(mHealthyInfo.getActivityfrequency());
        stringToCheck(cbHighintensity2, mHealthyInfo.getHighintensity2());
        etExercisename1.setText(mHealthyInfo.getExercisename1());
        stringToCheck(cbMediumintensity2, mHealthyInfo.getMediumintensity2());
        etExercisename2.setText(mHealthyInfo.getExercisename2());
        stringToCheck(cbLight2, mHealthyInfo.getLight2());
        etExercisename3.setText(mHealthyInfo.getExercisename3());
        mcdExercisefrequency.setKeyString(mHealthyInfo.getExercisefrequency());
    }


    private String checkToString(CheckBox checkBox) {
        return checkBox.isChecked() ? Constants.CHECK : Constants.UNCHECK;
    }

    private void stringToCheck(CheckBox checkBox, String checkStr) {
        checkBox.setChecked(Constants.CHECK.equals(checkStr));
    }


    private void assignViews() {
        etHypotension = (EditText) findViewById(R.id.et_hypotension);
        etHypertension = (EditText) findViewById(R.id.et_hypertension);
        etBloodfat = (EditText) findViewById(R.id.et_bloodfat);
        etBloodsugar = (EditText) findViewById(R.id.et_bloodsugar);
        TextView tvTabone = (TextView) findViewById(R.id.tv_tabone);
        LinearLayout llContentOne = (LinearLayout) findViewById(R.id.ll_content_one);
        cbIshot = (CheckBox) findViewById(R.id.cb_ishot);
        cbIsonlyhot = (CheckBox) findViewById(R.id.cb_isonlyhot);
        cbIssalty = (CheckBox) findViewById(R.id.cb_issalty);
        cbIsonlysalty = (CheckBox) findViewById(R.id.cb_isonlysalty);
        cbIssweet = (CheckBox) findViewById(R.id.cb_issweet);
        cbIsonlysweet = (CheckBox) findViewById(R.id.cb_isonlysweet);
        cbFat = (CheckBox) findViewById(R.id.cb_fat);
        cbDietlight = (CheckBox) findViewById(R.id.cb_dietlight);
        etOtherflavors = (EditText) findViewById(R.id.et_otherflavors);
        TextView tvTabtwo = (TextView) findViewById(R.id.tv_tabtwo);
        LinearLayout llContentTwo = (LinearLayout) findViewById(R.id.ll_content_two);
        cbCooking = (CheckBox) findViewById(R.id.cb_cooking);
        cbKuaichao = (CheckBox) findViewById(R.id.cb_kuaichao);
        cbShaokao = (CheckBox) findViewById(R.id.cb_shaokao);
        cbStew = (CheckBox) findViewById(R.id.cb_stew);
        cbHalogen = (CheckBox) findViewById(R.id.cb_halogen);
        TextView tvTabthree = (TextView) findViewById(R.id.tv_tabthree);
        LinearLayout llContentThree = (LinearLayout) findViewById(R.id.ll_content_three);
        cbVegetableoil = (CheckBox) findViewById(R.id.cb_vegetableoil);
        cbBlendoil = (CheckBox) findViewById(R.id.cb_blendoil);
        cbAnimaloil = (CheckBox) findViewById(R.id.cb_animaloil);
        cbMixedoil = (CheckBox) findViewById(R.id.cb_mixedoil);
        etMixedoilratio = (EditText) findViewById(R.id.et_mixedoilratio);
        TextView tvTabfour = (TextView) findViewById(R.id.tv_tabfour);
        LinearLayout llContentFour = (LinearLayout) findViewById(R.id.ll_content_four);
        etDrinkamount1 = (EditText) findViewById(R.id.et_drinkamount1);
        etFrequency1 = (EditText) findViewById(R.id.et_frequency1);
        etDrinkamount2 = (EditText) findViewById(R.id.et_drinkamount2);
        etFrequency2 = (EditText) findViewById(R.id.et_frequency2);
        etDrinkamount3 = (EditText) findViewById(R.id.et_drinkamount3);
        etFrequency3 = (EditText) findViewById(R.id.et_frequency3);
        mcdTeatype = (MultiChoiceDialog) findViewById(R.id.mcd_teatype);
        mcdTeachroma = (MultiChoiceDialog) findViewById(R.id.mcd_teachroma);
        etDrinkamount4 = (EditText) findViewById(R.id.et_drinkamount4);
        etFrequency4 = (EditText) findViewById(R.id.et_frequency4);
        mcdWinetype = (MultiChoiceDialog) findViewById(R.id.mcd_winetype);
        TextView tvTabfive = (TextView) findViewById(R.id.tv_tabfive);
        LinearLayout llContentFive = (LinearLayout) findViewById(R.id.ll_content_five);
        cbIssmoking = (CheckBox) findViewById(R.id.cb_issmoking);
        etSmokingfrequency2 = (EditText) findViewById(R.id.et_smokingfrequency2);
        etSmokingfrequency1 = (EditText) findViewById(R.id.et_smokingfrequency1);
        etSmokingoften = (EditText) findViewById(R.id.et_smokingoften);
        tvSmokingdate = (TextView) findViewById(R.id.tv_smokingdate);
        tvSmokcessationdate = (TextView) findViewById(R.id.tv_smokcessationdate);
        TextView tvTabsix = (TextView) findViewById(R.id.tv_tabsix);
        LinearLayout llContentSix = (LinearLayout) findViewById(R.id.ll_content_six);
        cbSleeppattern = (CheckBox) findViewById(R.id.cb_sleeppattern);
        mcdSleepquality = (MultiChoiceDialog) findViewById(R.id.mcd_sleepquality);
        tvEarlysleeptime = (TextView) findViewById(R.id.tv_earlysleeptime);
        tvLatesleeptime = (TextView) findViewById(R.id.tv_latesleeptime);
        mcdInsomnia = (MultiChoiceDialog) findViewById(R.id.mcd_insomnia);
        mcdBoilnight = (MultiChoiceDialog) findViewById(R.id.mcd_boilnight);
        TextView tvTabseven = (TextView) findViewById(R.id.tv_tabseven);
        LinearLayout llContentSeven = (LinearLayout) findViewById(R.id.ll_content_seven);
        mcdEnergy = (MultiChoiceDialog) findViewById(R.id.mcd_energy);
        etOtherenergy = (EditText) findViewById(R.id.et_otherenergy);
        TextView tvTabeight = (TextView) findViewById(R.id.tv_tabeight);
        LinearLayout llContentEight = (LinearLayout) findViewById(R.id.ll_content_eight);
        cbFeelingnormal = (CheckBox) findViewById(R.id.cb_feelingnormal);
        cbEasytension = (CheckBox) findViewById(R.id.cb_easytension);
        cbEasyanxiety = (CheckBox) findViewById(R.id.cb_easyanxiety);
        cbEasydepressed = (CheckBox) findViewById(R.id.cb_easydepressed);
        cbEasyangry = (CheckBox) findViewById(R.id.cb_easyangry);
        cbEasyexcited = (CheckBox) findViewById(R.id.cb_easyexcited);
        cbEasyemotionallow = (CheckBox) findViewById(R.id.cb_easyemotionallow);
        cbPressure = (CheckBox) findViewById(R.id.cb_pressure);
        cbOppressive = (CheckBox) findViewById(R.id.cb_oppressive);
        etOtherfeeling = (EditText) findViewById(R.id.et_otherfeeling);
        TextView tvTabnine = (TextView) findViewById(R.id.tv_tabnine);
        LinearLayout llContentNine = (LinearLayout) findViewById(R.id.ll_content_nine);
        cbHighintensity1 = (CheckBox) findViewById(R.id.cb_highintensity1);
        etActivityname1 = (EditText) findViewById(R.id.et_activityname1);
        cbMediumintensity1 = (CheckBox) findViewById(R.id.cb_mediumintensity1);
        etActivityname2 = (EditText) findViewById(R.id.et_activityname2);
        cbLight1 = (CheckBox) findViewById(R.id.cb_light1);
        etActivityname3 = (EditText) findViewById(R.id.et_activityname3);
        mcdActivityfrequency = (MultiChoiceDialog) findViewById(R.id.mcd_activityfrequency);
        TextView tvTabten = (TextView) findViewById(R.id.tv_tabten);
        LinearLayout llContentTen = (LinearLayout) findViewById(R.id.ll_content_ten);
        cbHighintensity2 = (CheckBox) findViewById(R.id.cb_highintensity2);
        etExercisename1 = (EditText) findViewById(R.id.et_exercisename1);
        cbMediumintensity2 = (CheckBox) findViewById(R.id.cb_Mediumintensity2);
        etExercisename2 = (EditText) findViewById(R.id.et_exercisename2);
        cbLight2 = (CheckBox) findViewById(R.id.cb_light2);
        etExercisename3 = (EditText) findViewById(R.id.et_exercisename3);
        mcdExercisefrequency = (MultiChoiceDialog) findViewById(R.id.mcd_exercisefrequency);
        CheckBox[] cbArr = new CheckBox[]{
                cbIshot, cbIsonlyhot, cbIssalty, cbIsonlysalty, cbIssweet, cbIsonlysweet, cbFat};
        cbDietlight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (CheckBox item : cbArr) {
                if (isChecked) item.setChecked(false);
            }
        });
        for (CheckBox item : cbArr) {
            item.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) cbDietlight.setChecked(false);
            });
        }
        tvEarlysleeptime.setOnClickListener(v -> UtilsDate.showTimeChoice(mActivity, tvEarlysleeptime));
        tvLatesleeptime.setOnClickListener(v -> UtilsDate.showTimeChoice(mActivity, tvLatesleeptime));
        TextView[] tvArr = new TextView[]{
                tvTabone, tvTabtwo, tvTabthree, tvTabfour, tvTabfive,
                tvTabsix, tvTabseven, tvTabeight, tvTabnine, tvTabten
        };
        LinearLayout[] llArr = new LinearLayout[]{
                llContentOne, llContentTwo, llContentThree, llContentFour, llContentFive,
                llContentSix, llContentSeven, llContentEight, llContentNine, llContentTen
        };
        for (int index = 0; index < tvArr.length; index++) {
            LinearLayout indexContent = llArr[index];
            tvArr[index].setOnClickListener(v ->
                    UtilsUi.setVisibility(indexContent, indexContent.getVisibility() == View.GONE));
        }
        tvSmokingdate.setOnClickListener(v ->
                UtilsDate.showDateChoice(mActivity, tvSmokingdate, Calendar.getInstance().getTimeInMillis()));
        tvSmokcessationdate.setOnClickListener(v ->
                UtilsDate.showDateChoice(mActivity, tvSmokcessationdate, Calendar.getInstance().getTimeInMillis()));
    }
}

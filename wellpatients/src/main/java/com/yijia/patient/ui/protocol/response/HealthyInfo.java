package com.yijia.patient.ui.protocol.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZeroAries on 2016/6/30.
 * 健康信息
 */
public class HealthyInfo implements Parcelable {
    private String pkHealthyinfo;//主键
    private String pkBasicinfo;//外键
    private String hypotension;//低血压
    private String hypertension;//高血压
    private String bloodsugar;//血糖
    private String bloodfat;//血脂
    private String dietlight;//饮食清淡
    private String ishot;//是否偏辣
    private String isonlyhot;//是否嗜辣
    private String issalty;//是否偏咸
    private String isonlysalty;//是否嗜咸
    private String issweet;//是否偏甜
    private String isonlysweet;//是否嗜甜
    private String fat;//偏油腻
    private String otherflavors;//其他口味
    private String cooking;//蒸煮为主
    private String kuaichao;//煎、快炒为主
    private String shaokao;//油炸、烧烤为主
    private String stew;//煲炖为主
    private String halogen;//卤制、腌制为主
    private String vegetableoil;//植物油
    private String blendoil;//调和油
    private String animaloil;//动物油
    private String mixedoil;//混合油
    private String mixedoilratio;//混合油比例
    private String drinkamount1;//水引用量
    private String drinkamount2;//饮料饮用量
    private String drinkamount3;//茶引用量
    private String drinkamount4;//酒饮用量
    private String frequency1;//饮水频率
    private String frequency2;//饮料频率
    private String frequency3;//饮茶频率
    private String frequency4;//饮酒频率
    private String teatype;//茶类型
    private String winetype;//酒类型
    private String teachroma;//茶浓度
    private String issmoking;//是否吸烟
    private String smokingfrequency1;//吸二手烟烟频率
    private String smokingfrequency2;//吸烟频率
    private String smokingoften;//二手烟吸烟时长
    private String smokingdate;//吸烟时间
    private String smokcessationdate;//戒烟时间
    private String sleeppattern;//睡眠规律
    private String earlysleeptime;//早睡时间
    private String latesleeptime;//晚睡时间
    private String sleepquality;//睡眠质量
    private String insomnia;//失眠情况
    private String boilnight;//熬夜情况
    private String energy;//精力
    private String otherenergy;//精力其他情况
    private String feelingnormal;//情绪正常
    private String easytension;//易紧张
    private String easyanxiety;//易焦虑
    private String easydepressed;//易抑郁
    private String easyangry;//易生气
    private String easyexcited;//易激动
    private String easyemotionallow;//易情绪低落
    private String pressure;//压力大
    private String oppressive;//压抑感强
    private String otherfeeling;//其他情绪
    private String highintensity1;//高强度1
    private String activityname1;//活动名称1
    private String Mediumintensity1;//中等强度1
    private String activityname2;//活动名称2
    private String light1;//轻度1
    private String activityname3;//活动名称3
    private String activityfrequency;//活动频率
    private String highintensity2;//高强度2
    private String exercisename1;//活动名称4
    private String Mediumintensity2;//中等强度2
    private String exercisename2;//活动名称5
    private String light2;//轻度2
    private String exercisename3;//活动名称6
    private String exercisefrequency;//体育运动频率

    public String getActivityfrequency() {
        return activityfrequency;
    }

    public void setActivityfrequency(String activityfrequency) {
        this.activityfrequency = activityfrequency;
    }

    public String getActivityname1() {
        return activityname1;
    }

    public void setActivityname1(String activityname1) {
        this.activityname1 = activityname1;
    }

    public String getActivityname2() {
        return activityname2;
    }

    public void setActivityname2(String activityname2) {
        this.activityname2 = activityname2;
    }

    public String getActivityname3() {
        return activityname3;
    }

    public void setActivityname3(String activityname3) {
        this.activityname3 = activityname3;
    }

    public String getAnimaloil() {
        return animaloil;
    }

    public void setAnimaloil(String animaloil) {
        this.animaloil = animaloil;
    }

    public String getBlendoil() {
        return blendoil;
    }

    public void setBlendoil(String blendoil) {
        this.blendoil = blendoil;
    }

    public String getBloodfat() {
        return bloodfat;
    }

    public void setBloodfat(String bloodfat) {
        this.bloodfat = bloodfat;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getHypotension() {
        return hypotension;
    }

    public void setHypotension(String hypotension) {
        this.hypotension = hypotension;
    }

    public String getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(String bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public String getBoilnight() {
        return boilnight;
    }

    public void setBoilnight(String boilnight) {
        this.boilnight = boilnight;
    }

    public String getCooking() {
        return cooking;
    }

    public void setCooking(String cooking) {
        this.cooking = cooking;
    }

    public String getDietlight() {
        return dietlight;
    }

    public void setDietlight(String dietlight) {
        this.dietlight = dietlight;
    }

    public String getDrinkamount1() {
        return drinkamount1;
    }

    public void setDrinkamount1(String drinkamount1) {
        this.drinkamount1 = drinkamount1;
    }

    public String getDrinkamount2() {
        return drinkamount2;
    }

    public void setDrinkamount2(String drinkamount2) {
        this.drinkamount2 = drinkamount2;
    }

    public String getDrinkamount3() {
        return drinkamount3;
    }

    public void setDrinkamount3(String drinkamount3) {
        this.drinkamount3 = drinkamount3;
    }

    public String getDrinkamount4() {
        return drinkamount4;
    }

    public void setDrinkamount4(String drinkamount4) {
        this.drinkamount4 = drinkamount4;
    }

    public String getEarlysleeptime() {
        return earlysleeptime;
    }

    public void setEarlysleeptime(String earlysleeptime) {
        this.earlysleeptime = earlysleeptime;
    }

    public String getEasyangry() {
        return easyangry;
    }

    public void setEasyangry(String easyangry) {
        this.easyangry = easyangry;
    }

    public String getEasyanxiety() {
        return easyanxiety;
    }

    public void setEasyanxiety(String easyanxiety) {
        this.easyanxiety = easyanxiety;
    }

    public String getEasydepressed() {
        return easydepressed;
    }

    public void setEasydepressed(String easydepressed) {
        this.easydepressed = easydepressed;
    }

    public String getEasyemotionallow() {
        return easyemotionallow;
    }

    public void setEasyemotionallow(String easyemotionallow) {
        this.easyemotionallow = easyemotionallow;
    }

    public String getEasyexcited() {
        return easyexcited;
    }

    public void setEasyexcited(String easyexcited) {
        this.easyexcited = easyexcited;
    }

    public String getEasytension() {
        return easytension;
    }

    public void setEasytension(String easytension) {
        this.easytension = easytension;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getExercisefrequency() {
        return exercisefrequency;
    }

    public void setExercisefrequency(String exercisefrequency) {
        this.exercisefrequency = exercisefrequency;
    }

    public String getExercisename1() {
        return exercisename1;
    }

    public void setExercisename1(String exercisename1) {
        this.exercisename1 = exercisename1;
    }

    public String getExercisename2() {
        return exercisename2;
    }

    public void setExercisename2(String exercisename2) {
        this.exercisename2 = exercisename2;
    }

    public String getExercisename3() {
        return exercisename3;
    }

    public void setExercisename3(String exercisename3) {
        this.exercisename3 = exercisename3;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getFeelingnormal() {
        return feelingnormal;
    }

    public void setFeelingnormal(String feelingnormal) {
        this.feelingnormal = feelingnormal;
    }

    public String getFrequency1() {
        return frequency1;
    }

    public void setFrequency1(String frequency1) {
        this.frequency1 = frequency1;
    }

    public String getFrequency2() {
        return frequency2;
    }

    public void setFrequency2(String frequency2) {
        this.frequency2 = frequency2;
    }

    public String getFrequency3() {
        return frequency3;
    }

    public void setFrequency3(String frequency3) {
        this.frequency3 = frequency3;
    }

    public String getFrequency4() {
        return frequency4;
    }

    public void setFrequency4(String frequency4) {
        this.frequency4 = frequency4;
    }

    public String getHalogen() {
        return halogen;
    }

    public void setHalogen(String halogen) {
        this.halogen = halogen;
    }

    public String getHighintensity1() {
        return highintensity1;
    }

    public void setHighintensity1(String highintensity1) {
        this.highintensity1 = highintensity1;
    }

    public String getHighintensity2() {
        return highintensity2;
    }

    public void setHighintensity2(String highintensity2) {
        this.highintensity2 = highintensity2;
    }

    public String getInsomnia() {
        return insomnia;
    }

    public void setInsomnia(String insomnia) {
        this.insomnia = insomnia;
    }

    public String getIshot() {
        return ishot;
    }

    public void setIshot(String ishot) {
        this.ishot = ishot;
    }

    public String getIsonlyhot() {
        return isonlyhot;
    }

    public void setIsonlyhot(String isonlyhot) {
        this.isonlyhot = isonlyhot;
    }

    public String getIsonlysalty() {
        return isonlysalty;
    }

    public void setIsonlysalty(String isonlysalty) {
        this.isonlysalty = isonlysalty;
    }

    public String getIsonlysweet() {
        return isonlysweet;
    }

    public void setIsonlysweet(String isonlysweet) {
        this.isonlysweet = isonlysweet;
    }

    public String getIssalty() {
        return issalty;
    }

    public void setIssalty(String issalty) {
        this.issalty = issalty;
    }

    public String getIssmoking() {
        return issmoking;
    }

    public void setIssmoking(String issmoking) {
        this.issmoking = issmoking;
    }

    public String getIssweet() {
        return issweet;
    }

    public void setIssweet(String issweet) {
        this.issweet = issweet;
    }

    public String getKuaichao() {
        return kuaichao;
    }

    public void setKuaichao(String kuaichao) {
        this.kuaichao = kuaichao;
    }

    public String getLatesleeptime() {
        return latesleeptime;
    }

    public void setLatesleeptime(String latesleeptime) {
        this.latesleeptime = latesleeptime;
    }

    public String getLight1() {
        return light1;
    }

    public void setLight1(String light1) {
        this.light1 = light1;
    }

    public String getLight2() {
        return light2;
    }

    public void setLight2(String light2) {
        this.light2 = light2;
    }

    public String getMediumintensity1() {
        return Mediumintensity1;
    }

    public void setMediumintensity1(String mediumintensity1) {
        Mediumintensity1 = mediumintensity1;
    }

    public String getMediumintensity2() {
        return Mediumintensity2;
    }

    public void setMediumintensity2(String mediumintensity2) {
        Mediumintensity2 = mediumintensity2;
    }

    public String getMixedoil() {
        return mixedoil;
    }

    public void setMixedoil(String mixedoil) {
        this.mixedoil = mixedoil;
    }

    public String getMixedoilratio() {
        return mixedoilratio;
    }

    public void setMixedoilratio(String mixedoilratio) {
        this.mixedoilratio = mixedoilratio;
    }

    public String getOppressive() {
        return oppressive;
    }

    public void setOppressive(String oppressive) {
        this.oppressive = oppressive;
    }

    public String getOtherenergy() {
        return otherenergy;
    }

    public void setOtherenergy(String otherenergy) {
        this.otherenergy = otherenergy;
    }

    public String getOtherfeeling() {
        return otherfeeling;
    }

    public void setOtherfeeling(String otherfeeling) {
        this.otherfeeling = otherfeeling;
    }

    public String getOtherflavors() {
        return otherflavors;
    }

    public void setOtherflavors(String otherflavors) {
        this.otherflavors = otherflavors;
    }

    public String getPkBasicinfo() {
        return pkBasicinfo;
    }

    public void setPkBasicinfo(String pkBasicinfo) {
        this.pkBasicinfo = pkBasicinfo;
    }

    public String getPkHealthyinfo() {
        return pkHealthyinfo;
    }

    public void setPkHealthyinfo(String pkHealthyinfo) {
        this.pkHealthyinfo = pkHealthyinfo;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getShaokao() {
        return shaokao;
    }

    public void setShaokao(String shaokao) {
        this.shaokao = shaokao;
    }

    public String getSleeppattern() {
        return sleeppattern;
    }

    public void setSleeppattern(String sleeppattern) {
        this.sleeppattern = sleeppattern;
    }

    public String getSleepquality() {
        return sleepquality;
    }

    public void setSleepquality(String sleepquality) {
        this.sleepquality = sleepquality;
    }

    public String getSmokcessationdate() {
        return smokcessationdate;
    }

    public void setSmokcessationdate(String smokcessationdate) {
        this.smokcessationdate = smokcessationdate;
    }

    public String getSmokingdate() {
        return smokingdate;
    }

    public void setSmokingdate(String smokingdate) {
        this.smokingdate = smokingdate;
    }

    public String getSmokingfrequency1() {
        return smokingfrequency1;
    }

    public void setSmokingfrequency1(String smokingfrequency1) {
        this.smokingfrequency1 = smokingfrequency1;
    }

    public String getSmokingfrequency2() {
        return smokingfrequency2;
    }

    public void setSmokingfrequency2(String smokingfrequency2) {
        this.smokingfrequency2 = smokingfrequency2;
    }

    public String getSmokingoften() {
        return smokingoften;
    }

    public void setSmokingoften(String smokingoften) {
        this.smokingoften = smokingoften;
    }

    public String getStew() {
        return stew;
    }

    public void setStew(String stew) {
        this.stew = stew;
    }

    public String getTeachroma() {
        return teachroma;
    }

    public void setTeachroma(String teachroma) {
        this.teachroma = teachroma;
    }

    public String getTeatype() {
        return teatype;
    }

    public void setTeatype(String teatype) {
        this.teatype = teatype;
    }

    public String getVegetableoil() {
        return vegetableoil;
    }

    public void setVegetableoil(String vegetableoil) {
        this.vegetableoil = vegetableoil;
    }

    public String getWinetype() {
        return winetype;
    }

    public void setWinetype(String winetype) {
        this.winetype = winetype;
    }

    public HealthyInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pkHealthyinfo);
        dest.writeString(this.pkBasicinfo);
        dest.writeString(this.hypotension);
        dest.writeString(this.hypertension);
        dest.writeString(this.bloodsugar);
        dest.writeString(this.bloodfat);
        dest.writeString(this.dietlight);
        dest.writeString(this.ishot);
        dest.writeString(this.isonlyhot);
        dest.writeString(this.issalty);
        dest.writeString(this.isonlysalty);
        dest.writeString(this.issweet);
        dest.writeString(this.isonlysweet);
        dest.writeString(this.fat);
        dest.writeString(this.otherflavors);
        dest.writeString(this.cooking);
        dest.writeString(this.kuaichao);
        dest.writeString(this.shaokao);
        dest.writeString(this.stew);
        dest.writeString(this.halogen);
        dest.writeString(this.vegetableoil);
        dest.writeString(this.blendoil);
        dest.writeString(this.animaloil);
        dest.writeString(this.mixedoil);
        dest.writeString(this.mixedoilratio);
        dest.writeString(this.drinkamount1);
        dest.writeString(this.drinkamount2);
        dest.writeString(this.drinkamount3);
        dest.writeString(this.drinkamount4);
        dest.writeString(this.frequency1);
        dest.writeString(this.frequency2);
        dest.writeString(this.frequency3);
        dest.writeString(this.frequency4);
        dest.writeString(this.teatype);
        dest.writeString(this.winetype);
        dest.writeString(this.teachroma);
        dest.writeString(this.issmoking);
        dest.writeString(this.smokingfrequency1);
        dest.writeString(this.smokingfrequency2);
        dest.writeString(this.smokingoften);
        dest.writeString(this.smokingdate);
        dest.writeString(this.smokcessationdate);
        dest.writeString(this.sleeppattern);
        dest.writeString(this.earlysleeptime);
        dest.writeString(this.latesleeptime);
        dest.writeString(this.sleepquality);
        dest.writeString(this.insomnia);
        dest.writeString(this.boilnight);
        dest.writeString(this.energy);
        dest.writeString(this.otherenergy);
        dest.writeString(this.feelingnormal);
        dest.writeString(this.easytension);
        dest.writeString(this.easyanxiety);
        dest.writeString(this.easydepressed);
        dest.writeString(this.easyangry);
        dest.writeString(this.easyexcited);
        dest.writeString(this.easyemotionallow);
        dest.writeString(this.pressure);
        dest.writeString(this.oppressive);
        dest.writeString(this.otherfeeling);
        dest.writeString(this.highintensity1);
        dest.writeString(this.activityname1);
        dest.writeString(this.Mediumintensity1);
        dest.writeString(this.activityname2);
        dest.writeString(this.light1);
        dest.writeString(this.activityname3);
        dest.writeString(this.activityfrequency);
        dest.writeString(this.highintensity2);
        dest.writeString(this.exercisename1);
        dest.writeString(this.Mediumintensity2);
        dest.writeString(this.exercisename2);
        dest.writeString(this.light2);
        dest.writeString(this.exercisename3);
        dest.writeString(this.exercisefrequency);
    }

    protected HealthyInfo(Parcel in) {
        this.pkHealthyinfo = in.readString();
        this.pkBasicinfo = in.readString();
        this.hypotension = in.readString();
        this.hypertension = in.readString();
        this.bloodsugar = in.readString();
        this.bloodfat = in.readString();
        this.dietlight = in.readString();
        this.ishot = in.readString();
        this.isonlyhot = in.readString();
        this.issalty = in.readString();
        this.isonlysalty = in.readString();
        this.issweet = in.readString();
        this.isonlysweet = in.readString();
        this.fat = in.readString();
        this.otherflavors = in.readString();
        this.cooking = in.readString();
        this.kuaichao = in.readString();
        this.shaokao = in.readString();
        this.stew = in.readString();
        this.halogen = in.readString();
        this.vegetableoil = in.readString();
        this.blendoil = in.readString();
        this.animaloil = in.readString();
        this.mixedoil = in.readString();
        this.mixedoilratio = in.readString();
        this.drinkamount1 = in.readString();
        this.drinkamount2 = in.readString();
        this.drinkamount3 = in.readString();
        this.drinkamount4 = in.readString();
        this.frequency1 = in.readString();
        this.frequency2 = in.readString();
        this.frequency3 = in.readString();
        this.frequency4 = in.readString();
        this.teatype = in.readString();
        this.winetype = in.readString();
        this.teachroma = in.readString();
        this.issmoking = in.readString();
        this.smokingfrequency1 = in.readString();
        this.smokingfrequency2 = in.readString();
        this.smokingoften = in.readString();
        this.smokingdate = in.readString();
        this.smokcessationdate = in.readString();
        this.sleeppattern = in.readString();
        this.earlysleeptime = in.readString();
        this.latesleeptime = in.readString();
        this.sleepquality = in.readString();
        this.insomnia = in.readString();
        this.boilnight = in.readString();
        this.energy = in.readString();
        this.otherenergy = in.readString();
        this.feelingnormal = in.readString();
        this.easytension = in.readString();
        this.easyanxiety = in.readString();
        this.easydepressed = in.readString();
        this.easyangry = in.readString();
        this.easyexcited = in.readString();
        this.easyemotionallow = in.readString();
        this.pressure = in.readString();
        this.oppressive = in.readString();
        this.otherfeeling = in.readString();
        this.highintensity1 = in.readString();
        this.activityname1 = in.readString();
        this.Mediumintensity1 = in.readString();
        this.activityname2 = in.readString();
        this.light1 = in.readString();
        this.activityname3 = in.readString();
        this.activityfrequency = in.readString();
        this.highintensity2 = in.readString();
        this.exercisename1 = in.readString();
        this.Mediumintensity2 = in.readString();
        this.exercisename2 = in.readString();
        this.light2 = in.readString();
        this.exercisename3 = in.readString();
        this.exercisefrequency = in.readString();
    }

    public static final Creator<HealthyInfo> CREATOR = new Creator<HealthyInfo>() {
        @Override
        public HealthyInfo createFromParcel(Parcel source) {
            return new HealthyInfo(source);
        }

        @Override
        public HealthyInfo[] newArray(int size) {
            return new HealthyInfo[size];
        }
    };
}

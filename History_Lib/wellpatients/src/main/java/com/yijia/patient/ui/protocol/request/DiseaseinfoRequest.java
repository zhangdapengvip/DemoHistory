package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/22.
 * 疾病信息保存
 */
public class DiseaseinfoRequest extends DefaultRequest {
    private String pkDiseaseinfo;//主键
    private String pkBasicinfo;//外键
    private String diabetes;//糖尿病
    private String hypertension;//高血压
    private String bloodfat;//高血脂
    private String heartdisease;//冠心病
    private String congenitalheartdisease;//先天性心脏病
    private String ischemicheartdisease;//缺血性心脏病
    private String rheumatismheartdisease;//风湿类心脏病
    private String valvulardisease;//瓣膜病
    private String obesity;//肥胖症
    private String myocarditis;//心肌炎
    private String miocardial;//心梗
    private String beats;//早搏
    private String svt;//室上性心动过速
    private String atrialfibrillation;//心房颤动
    private String cerebralapoplexy;//脑卒中
    private String Othercardiovascular;//其他心血管疾病
    private String tuberculosis;//结核
    private String pulmonarynodules;//肺部结节
    private String otherrespiratory;//其他呼吸系统疾病
    private String gastriculcer;//胃溃疡
    private String helicobacter;//幽门螺旋杆菌感染
    private String gastricpolyp;//胃息肉
    private String atrophicgastritis;//萎缩性胃炎
    private String ulcerativecolitis;//溃疡性结肠炎
    private String intestinalpolyp;//肠息肉
    private String hepatitis;//肝炎（ 型）
    private String otherdigestion;//其它消化系统疾病
    private String nephritis;//肾炎
    private String renalcysts;//肾脏囊肿或错构瘤
    private String otherkidney;//其他肾脏疾病
    private String cervical;//宫颈肥大
    private String hyperplasiacervical;//宫颈不典型增生
    private String hysteromyoma;//子宫肌瘤
    private String hpv;//HPV感染
    private String prostatic;//前列腺增生
    private String prostatitis;//前列腺炎
    private String prostaticnodule;//前列腺结节
    private String otherreproductive;//其他生殖系统疾病
    private String mastopathy;//乳腺囊性增生
    private String masthyperplasia;//乳腺单纯增生
    private String adenofibroma;//乳腺纤维瘤
    private String otherbreast;//其他乳腺疾病
    private String drugallergy;//对某些药物过敏
    private String drugname;//过敏药物名称
    private String surgery;//一年内是否做过手术
    private String surgeryname;//手术名称

    public String getAdenofibroma() {
        return adenofibroma;
    }

    public void setAdenofibroma(String adenofibroma) {
        this.adenofibroma = adenofibroma;
    }

    public String getAtrialfibrillation() {
        return atrialfibrillation;
    }

    public void setAtrialfibrillation(String atrialfibrillation) {
        this.atrialfibrillation = atrialfibrillation;
    }

    public String getAtrophicgastritis() {
        return atrophicgastritis;
    }

    public void setAtrophicgastritis(String atrophicgastritis) {
        this.atrophicgastritis = atrophicgastritis;
    }

    public String getBeats() {
        return beats;
    }

    public void setBeats(String beats) {
        this.beats = beats;
    }

    public String getBloodfat() {
        return bloodfat;
    }

    public void setBloodfat(String bloodfat) {
        this.bloodfat = bloodfat;
    }

    public String getCerebralapoplexy() {
        return cerebralapoplexy;
    }

    public void setCerebralapoplexy(String cerebralapoplexy) {
        this.cerebralapoplexy = cerebralapoplexy;
    }

    public String getCervical() {
        return cervical;
    }

    public void setCervical(String cervical) {
        this.cervical = cervical;
    }

    public String getCongenitalheartdisease() {
        return congenitalheartdisease;
    }

    public void setCongenitalheartdisease(String congenitalheartdisease) {
        this.congenitalheartdisease = congenitalheartdisease;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getDrugallergy() {
        return drugallergy;
    }

    public void setDrugallergy(String drugallergy) {
        this.drugallergy = drugallergy;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }

    public String getGastricpolyp() {
        return gastricpolyp;
    }

    public void setGastricpolyp(String gastricpolyp) {
        this.gastricpolyp = gastricpolyp;
    }

    public String getGastriculcer() {
        return gastriculcer;
    }

    public void setGastriculcer(String gastriculcer) {
        this.gastriculcer = gastriculcer;
    }

    public String getHeartdisease() {
        return heartdisease;
    }

    public void setHeartdisease(String heartdisease) {
        this.heartdisease = heartdisease;
    }

    public String getHelicobacter() {
        return helicobacter;
    }

    public void setHelicobacter(String helicobacter) {
        this.helicobacter = helicobacter;
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(String hepatitis) {
        this.hepatitis = hepatitis;
    }

    public String getHpv() {
        return hpv;
    }

    public void setHpv(String hpv) {
        this.hpv = hpv;
    }

    public String getHyperplasiacervical() {
        return hyperplasiacervical;
    }

    public void setHyperplasiacervical(String hyperplasiacervical) {
        this.hyperplasiacervical = hyperplasiacervical;
    }

    public String getHypertension() {
        return hypertension;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getHysteromyoma() {
        return hysteromyoma;
    }

    public void setHysteromyoma(String hysteromyoma) {
        this.hysteromyoma = hysteromyoma;
    }

    public String getIntestinalpolyp() {
        return intestinalpolyp;
    }

    public void setIntestinalpolyp(String intestinalpolyp) {
        this.intestinalpolyp = intestinalpolyp;
    }

    public String getIschemicheartdisease() {
        return ischemicheartdisease;
    }

    public void setIschemicheartdisease(String ischemicheartdisease) {
        this.ischemicheartdisease = ischemicheartdisease;
    }

    public String getMasthyperplasia() {
        return masthyperplasia;
    }

    public void setMasthyperplasia(String masthyperplasia) {
        this.masthyperplasia = masthyperplasia;
    }

    public String getMastopathy() {
        return mastopathy;
    }

    public void setMastopathy(String mastopathy) {
        this.mastopathy = mastopathy;
    }

    public String getMiocardial() {
        return miocardial;
    }

    public void setMiocardial(String miocardial) {
        this.miocardial = miocardial;
    }

    public String getMyocarditis() {
        return myocarditis;
    }

    public void setMyocarditis(String myocarditis) {
        this.myocarditis = myocarditis;
    }

    public String getNephritis() {
        return nephritis;
    }

    public void setNephritis(String nephritis) {
        this.nephritis = nephritis;
    }

    public String getObesity() {
        return obesity;
    }

    public void setObesity(String obesity) {
        this.obesity = obesity;
    }

    public String getOtherbreast() {
        return otherbreast;
    }

    public void setOtherbreast(String otherbreast) {
        this.otherbreast = otherbreast;
    }

    public String getOthercardiovascular() {
        return Othercardiovascular;
    }

    public void setOthercardiovascular(String othercardiovascular) {
        Othercardiovascular = othercardiovascular;
    }

    public String getOtherdigestion() {
        return otherdigestion;
    }

    public void setOtherdigestion(String otherdigestion) {
        this.otherdigestion = otherdigestion;
    }

    public String getOtherkidney() {
        return otherkidney;
    }

    public void setOtherkidney(String otherkidney) {
        this.otherkidney = otherkidney;
    }

    public String getOtherreproductive() {
        return otherreproductive;
    }

    public void setOtherreproductive(String otherreproductive) {
        this.otherreproductive = otherreproductive;
    }

    public String getOtherrespiratory() {
        return otherrespiratory;
    }

    public void setOtherrespiratory(String otherrespiratory) {
        this.otherrespiratory = otherrespiratory;
    }

    public String getPkBasicinfo() {
        return pkBasicinfo;
    }

    public void setPkBasicinfo(String pkBasicinfo) {
        this.pkBasicinfo = pkBasicinfo;
    }

    public String getPkDiseaseinfo() {
        return pkDiseaseinfo;
    }

    public void setPkDiseaseinfo(String pkDiseaseinfo) {
        this.pkDiseaseinfo = pkDiseaseinfo;
    }

    public String getProstatic() {
        return prostatic;
    }

    public void setProstatic(String prostatic) {
        this.prostatic = prostatic;
    }

    public String getProstaticnodule() {
        return prostaticnodule;
    }

    public void setProstaticnodule(String prostaticnodule) {
        this.prostaticnodule = prostaticnodule;
    }

    public String getProstatitis() {
        return prostatitis;
    }

    public void setProstatitis(String prostatitis) {
        this.prostatitis = prostatitis;
    }

    public String getPulmonarynodules() {
        return pulmonarynodules;
    }

    public void setPulmonarynodules(String pulmonarynodules) {
        this.pulmonarynodules = pulmonarynodules;
    }

    public String getRenalcysts() {
        return renalcysts;
    }

    public void setRenalcysts(String renalcysts) {
        this.renalcysts = renalcysts;
    }

    public String getRheumatismheartdisease() {
        return rheumatismheartdisease;
    }

    public void setRheumatismheartdisease(String rheumatismheartdisease) {
        this.rheumatismheartdisease = rheumatismheartdisease;
    }

    public String getSurgery() {
        return surgery;
    }

    public void setSurgery(String surgery) {
        this.surgery = surgery;
    }

    public String getSurgeryname() {
        return surgeryname;
    }

    public void setSurgeryname(String surgeryname) {
        this.surgeryname = surgeryname;
    }

    public String getSvt() {
        return svt;
    }

    public void setSvt(String svt) {
        this.svt = svt;
    }

    public String getTuberculosis() {
        return tuberculosis;
    }

    public void setTuberculosis(String tuberculosis) {
        this.tuberculosis = tuberculosis;
    }

    public String getUlcerativecolitis() {
        return ulcerativecolitis;
    }

    public void setUlcerativecolitis(String ulcerativecolitis) {
        this.ulcerativecolitis = ulcerativecolitis;
    }

    public String getValvulardisease() {
        return valvulardisease;
    }

    public void setValvulardisease(String valvulardisease) {
        this.valvulardisease = valvulardisease;
    }
}

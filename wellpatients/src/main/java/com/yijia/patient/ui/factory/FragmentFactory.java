package com.yijia.patient.ui.factory;

import android.os.Bundle;

import com.yijia.patient.ui.fragment.homechild.LectureFragment;
import com.yijia.patient.ui.fragment.homechild.MineHealthFragment;
import com.yijia.patient.ui.fragment.homechild.NewsFragment;
import com.yijia.patient.ui.fragment.homechild.SpecialNeedFragment;
import com.yijia.patient.ui.fragment.newschild.EducateNewsFragment;
import com.yijia.patient.ui.fragment.newschild.ImportantNewsFragment;
import com.yijia.patient.ui.fragment.newschild.MeetingNewsFragment;
import com.yijia.patient.ui.fragment.specialneed.GeneticDetectionFragment;
import com.yijia.patient.ui.fragment.specialneed.MedicalAssistanceFragment;
import com.yijia.patient.ui.fragment.viewchild.FamousHospitalsFragment;
import com.yijia.patient.ui.fragment.viewchild.ProfessionFragment;
import com.yijia.patient.ui.fragment.viewchild.PublicClassFragment;
import com.yijia.patient.ui.fragment.viewdetail.BriefIntroduceFragment;
import com.yijia.patient.ui.fragment.viewdetail.QuestionFragment;
import com.yijia.patient.ui.protocol.response.ViewPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.uibase.AppBaseFragment;
import com.zero.library.base.uibase.AppPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/3/10.
 * Fragment工厂类
 */
public class FragmentFactory {
    private static ArrayList<AppBaseFragment> mHomeFragments = new ArrayList<>();

    public static List<AppBaseFragment> createHomeFragments() {
        mHomeFragments.clear();
        mHomeFragments.add(new NewsFragment());
        mHomeFragments.add(new LectureFragment());
        mHomeFragments.add(new MineHealthFragment());
        mHomeFragments.add(new SpecialNeedFragment());
        return mHomeFragments;
    }

    private static ArrayList<AppPagerFragment> mNewsFragments = new ArrayList<>();

    public static List<AppPagerFragment> createNewsFragments() {
        if (mNewsFragments.size() <= 0) {
            mNewsFragments.add(new ImportantNewsFragment());
            mNewsFragments.add(new MeetingNewsFragment());
            mNewsFragments.add(new EducateNewsFragment());
        }
        return mNewsFragments;
    }

    private static ArrayList<AppPagerFragment> mViewFragments = new ArrayList<>();

    public static List<AppPagerFragment> createViewFragments() {
        if (mViewFragments.size() <= 0) {
            mViewFragments.add(new ProfessionFragment());
            mViewFragments.add(new FamousHospitalsFragment());
            mViewFragments.add(new PublicClassFragment());
        }
        return mViewFragments;
    }

    private static ArrayList<AppPagerFragment> mSpecialNeedFragments = new ArrayList<>();

    public static List<AppPagerFragment> createSpecialNeedFragments() {
        if (mSpecialNeedFragments.size() <= 0) {
            mSpecialNeedFragments.add(new GeneticDetectionFragment());
            mSpecialNeedFragments.add(new MedicalAssistanceFragment());
//            mSpecialNeedFragments.add(new ScientificInFragment());
        }
        return mSpecialNeedFragments;
    }


    private static ArrayList<AppPagerFragment> mViewDetailFragments = new ArrayList<>();

    public static List<AppPagerFragment> createViewDetailFragments(ViewPageResponse.Page.Rows mViewInfo) {
        if (mViewDetailFragments.size() <= 0) {
            Bundle argument = new Bundle();
            argument.putSerializable(AppConstants.PARCELABLE_KEY, mViewInfo);

            BriefIntroduceFragment briefIntroduce = new BriefIntroduceFragment();
            briefIntroduce.setArguments(argument);
            mViewDetailFragments.add(briefIntroduce);

            QuestionFragment question = new QuestionFragment();
            question.setArguments(argument);
            mViewDetailFragments.add(question);
        }
        return mViewDetailFragments;
    }
}

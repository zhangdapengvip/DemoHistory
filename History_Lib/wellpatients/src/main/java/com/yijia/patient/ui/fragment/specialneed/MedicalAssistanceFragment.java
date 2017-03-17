package com.yijia.patient.ui.fragment.specialneed;

import android.content.Intent;
import android.widget.ListView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.OrganizationIntroducedActivity;
import com.yijia.patient.ui.holder.MedicalAssistanceHolder;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.HospitalPageRequest;
import com.yijia.patient.ui.protocol.response.HospitalPageResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/11.
 * 医疗援助
 */
public class MedicalAssistanceFragment extends SpecialBaseFragment {

    private String SAVE_KEY = "MedicalAssistanceFragment";

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_medical_assistance_name);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<HospitalPageResponse.Page.Rows>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new MedicalAssistanceHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                Intent intent = new Intent(mActivity, OrganizationIntroducedActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData().get(position));
                JumpManager.doJumpForward(mActivity, intent);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }
        };
    }

    @Override
    protected void initData() {
        mRefreshList.perfectPullRefresh();
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }

    private void loadList() {
        if (mPageNum == AppConstants.FIRST_NUM) {
            HospitalPageResponse response = UtilsGson.fromJson(getSaveInfo(SAVE_KEY), HospitalPageResponse.class);
            if (null != response) fillView(response);
        }
        HospitalPageRequest request = new HospitalPageRequest();
        request.setPage(String.valueOf(mPageNum));
        Observable<HospitalPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).hospitalPageRequest
                (request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<HospitalPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(HospitalPageResponse response) {
                        if (mPageNum == AppConstants.FIRST_NUM) saveInfo(SAVE_KEY, UtilsGson.toJson(response));
                        fillView(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.loadError();
                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillView(HospitalPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
    }
}

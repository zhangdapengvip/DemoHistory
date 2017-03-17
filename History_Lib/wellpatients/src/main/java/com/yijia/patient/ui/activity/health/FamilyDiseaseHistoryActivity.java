package com.yijia.patient.ui.activity.health;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.holder.FamilyDiseaseHistoryHolder;
import com.yijia.patient.ui.holder.WorkHistoryHolder;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.InfoGetRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.FamilydiseaseinfoListResponse;
import com.yijia.patient.ui.protocol.response.WorkinfoListResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseActivity;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/6/27.
 * 家庭疾病史列表
 */
public class FamilyDiseaseHistoryActivity extends ListBaseActivity {

    private TextView tvEmpty;

    @Override
    public int getResLayout() {
        return R.layout.activity_title_list;
    }

    @Override
    public void initView() {
        super.initView();
        TitleBarView mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("家族疾病史");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText("新增");
        mTitleBar.setRightListener(v -> JumpManager.doJumpForwardWithResult(mActivity,
                new Intent(mActivity, FamilyDiseaseInfoActivity.class), 2001));
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<FamilydiseaseinfoListResponse.ListBean>(mActivity, listView, new ArrayList<>()) {
            @Override
            protected AppBaseHolder getHolder() {
                return new FamilyDiseaseHistoryHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                FamilydiseaseinfoListResponse.ListBean itemInfo = getData().get(position);
                Intent intent = new Intent(mActivity, FamilyDiseaseInfoActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, itemInfo);
                JumpManager.doJumpForward(mActivity, intent);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        getList();
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRefreshList.perfectPullRefreshSilence();
    }

    private void getList() {
        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        InfoGetRequest request = new InfoGetRequest();
        request.setPkBasicinfo(basicinfo.getDatas().getPkBasicinfo());
        Observable<FamilydiseaseinfoListResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                familydiseaseinfoListRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<FamilydiseaseinfoListResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(FamilydiseaseinfoListResponse response) {
                        fillList(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        pullDownComplete();
                    }
                });
    }

    private void fillList(FamilydiseaseinfoListResponse response) {
        List<FamilydiseaseinfoListResponse.ListBean> list = response.getList();
        UtilsUi.setVisibility(tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }

    @Override
    protected void addHeaderView(ListView listView) {
        listView.setDivider(null);
    }
}
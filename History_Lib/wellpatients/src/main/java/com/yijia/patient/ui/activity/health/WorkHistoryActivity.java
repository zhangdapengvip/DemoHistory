package com.yijia.patient.ui.activity.health;

import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.holder.WorkHistoryHolder;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.InfoGetRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.WorkinfoListResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
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
 * Created by ZeroAries on 2016/6/24.
 * 工作情况列表
 */
public class WorkHistoryActivity extends ListBaseActivity {

    private TextView tvEmpty;

    @Override
    public int getResLayout() {
        return R.layout.activity_title_list;
    }

    @Override
    public void initView() {
        super.initView();
        TitleBarView mTitleBar = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBar.setTitle("工作情况");
        mTitleBar.setLeftText(R.string.back);
        mTitleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
        mTitleBar.setRightText("新增");
        mTitleBar.setRightListener(v -> JumpManager.doJumpForwardWithResult(mActivity,
                new Intent(mActivity, WorkInfoActivity.class), 2001));
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<WorkinfoListResponse.ListBean>(mActivity, listView, new ArrayList<>()) {
            @Override
            protected AppBaseHolder getHolder() {
                return new WorkHistoryHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                WorkinfoListResponse.ListBean itemInfo = getData().get(position);
                Intent intent = new Intent(mActivity, WorkInfoActivity.class);
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
        Observable<WorkinfoListResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                workinfoListRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<WorkinfoListResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(WorkinfoListResponse response) {
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

    private void fillList(WorkinfoListResponse response) {
        List<WorkinfoListResponse.ListBean> list = response.getList();
        UtilsUi.setVisibility(tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }

    @Override
    protected void addHeaderView(ListView listView) {
        listView.setDivider(null);
    }
}

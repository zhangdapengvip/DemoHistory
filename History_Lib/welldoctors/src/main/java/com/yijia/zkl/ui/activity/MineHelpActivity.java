package com.yijia.zkl.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.holder.HelpHistroyHolder;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.HelpGetRequest;
import com.yijia.zkl.ui.protocol.response.HelpGetResponse;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseActivity;
import com.zero.library.base.utils.UtilsListView;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/14.
 * 特需——我的求助
 */
public class MineHelpActivity extends ListBaseActivity {

    private List<HelpGetResponse.Datas> mWaitList;
    private List<HelpGetResponse.Datas> mHistoryList;

    private View mHeadView;
    private ListView mLvWait;
    private DefaultAdapter mHeadAdapter;

    @Override
    public int getResLayout() {
        return R.layout.activity_mine_help;
    }


    @Override
    public void initView() {
        super.initView();
        TitleBarView titleBar = (TitleBarView) findViewById(R.id.title_bar);
        titleBar.setTitle(R.string.title_mine_help);
        titleBar.setLeftText(R.string.back);
        titleBar.setLeftListener(v -> JumpManager.doJumpBack(mActivity));
    }

    @Override
    protected void addHeaderView(ListView listView) {
        mHeadView = UtilsUi.inflate(mActivity, R.layout.head_mine_help);
        mLvWait = (ListView) mHeadView.findViewById(R.id.lv_wait_help);
        mHeadAdapter = getAdapter(mLvWait);
        mLvWait.setAdapter(mHeadAdapter);
        listView.addHeaderView(mHeadView);
    }

    @Override
    public void initData() {
        mRefreshList.perfectPullRefresh();
    }

    private void loadData() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        HelpGetRequest request = new HelpGetRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<HelpGetResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .helpGetRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<HelpGetResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(HelpGetResponse response) {
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

    private void fillList(HelpGetResponse response) {
        mWaitList = new ArrayList<>();
        mHistoryList = new ArrayList<>();
        for (HelpGetResponse.Datas data : response.getList()) {
            String state = data.getState();
            if (HelpGetResponse.LIST_WAIT.equals(state)) {
                mWaitList.add(data);
            } else if (HelpGetResponse.LIST_HISTORY.equals(state)) {
                mHistoryList.add(data);
            }
        }
        mHeadAdapter.refreshData(mWaitList);
        UtilsListView.setListViewHeightBasedOnChildren(mLvWait);
        mAdapter.refreshData(mHistoryList);
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<HelpGetResponse.Datas>(mActivity, listView, new ArrayList<>()) {

            @Override
            protected AppBaseHolder getHolder() {
                return new HelpHistroyHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                HelpGetResponse.Datas item = getData().get(position);
                Intent inent = new Intent(mActivity, PatientInformationActivity.class);
                inent.putExtra(AppConstants.PARCELABLE_KEY, item);
                JumpManager.doJumpForward(mActivity, inent);
            }
        };
    }

    @Override
    protected void onPullRefresh() {
        loadData();
    }
}

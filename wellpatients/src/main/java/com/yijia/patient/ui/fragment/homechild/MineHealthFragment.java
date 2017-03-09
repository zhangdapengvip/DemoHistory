package com.yijia.patient.ui.fragment.homechild;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.MineActiviity;
import com.yijia.patient.ui.activity.health.BaseInfoActivity;
import com.yijia.patient.ui.activity.health.DiseaseInfoActivity;
import com.yijia.patient.ui.activity.health.FamilyDiseaseHistoryActivity;
import com.yijia.patient.ui.activity.health.HealthInfoActivity;
import com.yijia.patient.ui.activity.health.WorkHistoryActivity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.holder.MineHealthHolder;
import com.yijia.patient.ui.manager.HealthManager;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.BasicinfoDetailsRequest;
import com.yijia.patient.ui.protocol.request.InfoGetRequest;
import com.yijia.patient.ui.protocol.response.BasicinfoResponse;
import com.yijia.patient.ui.protocol.response.HealthyInfo;
import com.yijia.patient.ui.protocol.response.HealthyinfoListResponse;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseFragment;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.picasso.UtilsPicasso;
import com.zero.library.base.view.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/10.
 * 我的健康
 */
public class MineHealthFragment extends ListBaseFragment {
    private TitleBarView mTitleBar;
    protected LoginResponse mLoginInfo;
    private TextView tvBaseInfo;
    private TextView tvWorkHistory;
    private TextView tvDiseaseistory;
    private TextView tvHomeDiseasehistory;
    private TextView tvEmpty;
    private TextView tvName;
    private TextView tvSex;
    private TextView tvHeight;
    private TextView tvWeight;
    private ImageView mIvHead;

    @Override
    protected int getResLayout() {
        return R.layout.fragment_mine_health;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLoginInfo = UserManager.getLoginInfo();
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.title_home_tong);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));

        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        tvBaseInfo = (TextView) view.findViewById(R.id.tv_base_info);
        tvWorkHistory = (TextView) view.findViewById(R.id.tv_work_history);
        tvDiseaseistory = (TextView) view.findViewById(R.id.tv_diseasehistory);
        tvHomeDiseasehistory = (TextView) view.findViewById(R.id.tv_home_diseasehistory);
        tvEmpty = (TextView) view.findViewById(R.id.tv_empty);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSex = (TextView) view.findViewById(R.id.tv_sex);
        tvHeight = (TextView) view.findViewById(R.id.tv_height);
        tvWeight = (TextView) view.findViewById(R.id.tv_weight);
        tvBaseInfo.setOnClickListener(v -> JumpManager.doJumpForward(mActivity,
                new Intent(mActivity, BaseInfoActivity.class)));
        tvWorkHistory.setOnClickListener(v -> {
            if (emptyBaseInfo()) {
                jumpToBase();
                return;
            }
            JumpManager.doJumpForward(mActivity, new Intent(mActivity, WorkHistoryActivity.class));
        });
        tvDiseaseistory.setOnClickListener(v -> {
            if (emptyBaseInfo()) {
                jumpToBase();
                return;
            }
            JumpManager.doJumpForward(mActivity, new Intent(mActivity, DiseaseInfoActivity.class));
        });
        tvHomeDiseasehistory.setOnClickListener(v -> {
            if (emptyBaseInfo()) {
                jumpToBase();
                return;
            }
            JumpManager.doJumpForward(mActivity, new Intent(mActivity, FamilyDiseaseHistoryActivity.class));
        });
    }

    private void jumpToBase() {
        AppToast.show(mActivity, "请先完善基本信息");
        JumpManager.doJumpForward(mActivity, new Intent(mActivity, BaseInfoActivity.class));
    }

    private boolean emptyBaseInfo() {
        BasicinfoResponse info = HealthManager.obtainBaseHealthInfo();
        return null == info || TextUtils.isEmpty(info.getDatas().getPkBasicinfo());
    }

    private void requestBaseHealth() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        BasicinfoDetailsRequest request = new BasicinfoDetailsRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        Observable<BasicinfoResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class)
                .basicinfoDetailsRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<BasicinfoResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(BasicinfoResponse response) {
                        HealthManager.storeBaseHealthInfo(response);
                        getList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {

                    }
                });
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<HealthyInfo>(mActivity, listView, new ArrayList<>()) {
            @Override
            protected AppBaseHolder getHolder() {
                return new MineHealthHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                HealthyInfo itemInfo = getData().get(position);
                Intent intent = new Intent(mActivity, HealthInfoActivity.class);
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
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
            UtilsPicasso.loadCircleImage(mActivity, mLoginInfo.getDatas().getImage(),
                    R.drawable.ic_default_head, mIvHead, R.dimen.dimen_60);
        }
        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        if (null == basicinfo || TextUtils.isEmpty(basicinfo.getDatas().getPkBasicinfo())) {
            requestBaseHealth();
        }else{
            tvName.setText(basicinfo.getDatas().getName());
            tvSex.setText(Constants.SEX_MAN.equals(basicinfo.getDatas().getName()) ? "男" : "女");
            tvHeight.setText(basicinfo.getDatas().getHeight());
            tvWeight.setText(basicinfo.getDatas().getWeight());
        }
        getList();
    }

    private void getList() {
        BasicinfoResponse basicinfo = HealthManager.obtainBaseHealthInfo();
        if (null == basicinfo || TextUtils.isEmpty(basicinfo.getDatas().getPkBasicinfo())) {
            List<HealthyInfo> list = new ArrayList<>();
            UtilsUi.setVisibility(tvEmpty, list.size() <= 0);
            mAdapter.refreshData(list);
            pullDownComplete();
            return;
        }
        InfoGetRequest request = new InfoGetRequest();
        String pkBasicinfo = basicinfo.getDatas().getPkBasicinfo();
        tvName.setText(basicinfo.getDatas().getName());
        tvSex.setText(Constants.SEX_MAN.equals(basicinfo.getDatas().getName()) ? "男" : "女");
        tvHeight.setText(basicinfo.getDatas().getHeight());
        tvWeight.setText(basicinfo.getDatas().getWeight());
        request.setPkBasicinfo(pkBasicinfo);
        Observable<HealthyinfoListResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).
                healthyinfoListRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<HealthyinfoListResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(HealthyinfoListResponse response) {
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

    private void fillList(HealthyinfoListResponse response) {
        List<HealthyInfo> list = response.getList();
        UtilsUi.setVisibility(tvEmpty, list.size() <= 0);
        mAdapter.refreshData(list);
    }

    @Override
    protected void addHeaderView(ListView listView) {
        listView.setDivider(null);
        View inflate = UtilsUi.inflate(mActivity, R.layout.head_add_btn);
        View addBtn = inflate.findViewById(R.id.btn_add_healthinfo);
        addBtn.setOnClickListener(v -> {
            if (emptyBaseInfo()) {
                jumpToBase();
                return;
            }
            List<HealthyInfo> data = mAdapter.getData();
            HealthyInfo itemInfo = null;
            if (data.size() <= 0) {
                itemInfo = new HealthyInfo();
            } else {
                itemInfo = data.get(0);
                itemInfo.setPkHealthyinfo("");
            }
            Intent intent = new Intent(mActivity, HealthInfoActivity.class);
            intent.putExtra(AppConstants.PARCELABLE_KEY, itemInfo);
            JumpManager.doJumpForward(mActivity, intent);
        });
        listView.addHeaderView(inflate);
    }

    @Override
    protected void initData() {
    }

    @Override
    public String getPagerTitle() {
        return "";
    }
}

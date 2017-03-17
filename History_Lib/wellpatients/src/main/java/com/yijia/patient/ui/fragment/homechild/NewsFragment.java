package com.yijia.patient.ui.fragment.homechild;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.MineActiviity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.bean.OfficeInfo;
import com.yijia.patient.ui.factory.FragmentFactory;
import com.yijia.patient.ui.holder.PopuWindowHolder;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.ConstantsRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsGson;
import com.zero.library.base.utils.UtilsListView;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.TitleBarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/10.
 * 资讯
 */
public class NewsFragment extends HomeBaseFragment {

    private TitleBarView mTitleBar;
    private OfficeInfo mCheckInfo;
    private AppEmptyDialog mOfficeDialog;
    private PopupWindow mPopupWindow;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.tab_one);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
//        mTitleBar.setRightText(R.string.label_domain);
//        mTitleBar.setRightListener(this::showFieldChoice);
//        checkOfficeInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        }
    }

    private void checkOfficeInfo() {
        List<OfficeInfo> officeList = Constants.getOfficeItemList();
        if (!TextUtils.isEmpty(mLoginInfo.getDatas().getDomain())) {
            for (OfficeInfo info : officeList) {
                if (mLoginInfo.getDatas().getDomain().equals(info.getId()))
                    mTitleBar.setTitle(getString(R.string.title_home_news, info.shortName));
            }
            return;
        }
        mOfficeDialog = new AppEmptyDialog(mActivity, 1);
        View officeView = UtilsUi.inflate(mActivity, R.layout.dialog_choice_office);
        RadioGroup officeGroup = (RadioGroup) officeView.findViewById(R.id.rg_office_group);
        for (int index = 0; index < officeList.size(); index++) {
            RadioButton buttonItem = (RadioButton) UtilsUi.inflate(mActivity, R.layout.dialog_choice_item);
            buttonItem.setText(officeList.get(index).fullName);
            officeGroup.addView(buttonItem, index);
            if (index == 0) buttonItem.setChecked(true);
        }
        mOfficeDialog.setView(officeView);
        mOfficeDialog.setBtnOnClickListener((dialog, view) -> {
            for (int count = 0; count < officeGroup.getChildCount(); count++) {
                RadioButton checkBtn = (RadioButton) officeGroup.getChildAt(count);
                if (checkBtn.isChecked()) {
                    mCheckInfo = officeList.get(count);
                    mTitleBar.setTitle(getString(R.string.title_home_news, mCheckInfo.shortName));
                    checkData();
                    commitDomain(mCheckInfo);
                }
            }
            dialog.dismiss();
        });
        mOfficeDialog.setCancelable(false);
        mOfficeDialog.setCanceledOnTouchOutside(false);
        mOfficeDialog.show();
    }

    private void commitDomain(OfficeInfo info) {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put(ConstantsRequest.PKUSER, mLoginInfo.getDatas().getPkUser());
        infoMap.put(ConstantsRequest.DOMAIN, info.id);
        Map<String, RequestBody> requesetMap = RetrofitUtils.getRequesetMap(infoMap);
        Observable<LoginResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).updateUserRequest(requesetMap);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<LoginResponse>() {
                    @Override
                    public void beforRequest() {
                    }

                    @Override
                    public void onSuccess(LoginResponse response) {
                        UserManager.storeLoginInfo(UtilsGson.toJson(response));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                    }
                });

    }

    private void showFieldChoice(View anchor) {
        List<OfficeInfo> officeList = Constants.getOfficeItemList();
        LinearLayout layout = (LinearLayout) View.inflate(mActivity, R.layout.list_field_choice, null);
        ListView listView = (ListView) layout.findViewById(R.id.lv_field_choice);
        DefaultAdapter<OfficeInfo> defaultAdapter = new DefaultAdapter<OfficeInfo>(mActivity, listView, officeList) {

            @Override
            protected AppBaseHolder getHolder() {
                return new PopuWindowHolder(mActivity);
            }
        };
        listView.setAdapter(defaultAdapter);
        UtilsListView.setListViewHeightBasedOnChildren(listView);
        mPopupWindow = UtilsUi.showDropListPopuwindow(mActivity, anchor, layout, LinearLayout
                .LayoutParams.WRAP_CONTENT, true, 0, -20);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            mCheckInfo = officeList.get(position);
            mTitleBar.setTitle(getString(R.string.title_home_news, mCheckInfo.shortName));
            checkData();
            mPopupWindow.dismiss();
        });
    }

    @Override
    public List<AppPagerFragment> getFragments() {
        return FragmentFactory.createNewsFragments();
    }

    @Override
    protected void initData() {
    }

    private void checkData() {
        if (null != mCheckInfo) {
            mCheckInfo.setViewType(OfficeInfo.TYPE_NEWS);
            RxBus.getInstance().send(mCheckInfo);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mOfficeDialog && mOfficeDialog.isShowing()) mOfficeDialog.dismiss();
        if (null != mPopupWindow && mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }
}

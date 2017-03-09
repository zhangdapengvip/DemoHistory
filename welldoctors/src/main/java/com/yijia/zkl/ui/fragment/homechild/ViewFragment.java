package com.yijia.zkl.ui.fragment.homechild;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.activity.MineActiviity;
import com.yijia.zkl.ui.bean.Constants;
import com.yijia.zkl.ui.bean.OfficeInfo;
import com.yijia.zkl.ui.factory.FragmentFactory;
import com.yijia.zkl.ui.holder.PopuWindowHolder;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.AppPagerFragment;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.utils.UtilsListView;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.util.List;

/**
 * Created by ZeroAries on 2016/3/10.
 * 视野
 */
public class ViewFragment extends HomeBaseFragment {

    private TitleBarView mTitleBar;
    private OfficeInfo mCheckInfo;
    private PopupWindow mPopupWindow;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(getString(R.string.title_home_view, UtilsUi.getString(R.string.label_office_tumour_short)));
        mTitleBar.setRightText(R.string.label_domain);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
        mTitleBar.setRightListener(this::showFieldChoice);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar) {
            LoginResponse loginInfo = UserManager.getLoginInfo();
            mTitleBar.setLeftIcon(mActivity, loginInfo.getDatas().getImage());
        }
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

    private void checkData() {
        if (null != mCheckInfo) {
            mCheckInfo.setViewType(OfficeInfo.TYPE_VIEW);
            RxBus.getInstance().send(mCheckInfo);
        }
    }

    @Override
    public List<AppPagerFragment> getFragments() {
        return FragmentFactory.createViewFragments();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPopupWindow && mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }
}

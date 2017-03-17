package com.yijia.patient.ui.fragment.homechild;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yijia.patient.R;
import com.yijia.patient.ui.activity.MineActiviity;
import com.yijia.patient.ui.bean.Constants;
import com.yijia.patient.ui.bean.OfficeInfo;
import com.yijia.patient.ui.factory.FragmentFactory;
import com.yijia.patient.ui.holder.PopuWindowHolder;
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
 * 科普讲座
 */
public class ViewFragmentOld extends HomeBaseFragment {

    private TitleBarView mTitleBar;
    private OfficeInfo mCheckInfo;
    private PopupWindow mPopupWindow;

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTitleBar = (TitleBarView) view.findViewById(R.id.title_bar);
        mTitleBar.setTitle(R.string.tab_two);
        mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
        mTitleBar.setLeftListener(v ->
                JumpManager.doJumpForward(mActivity, new Intent(mActivity, MineActiviity.class)));
//        mTitleBar.setRightText(R.string.label_domain);
//        mTitleBar.setRightListener(this::showFieldChoice);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mTitleBar && null != mLoginInfo) {
            mTitleBar.setLeftIcon(mActivity, mLoginInfo.getDatas().getImage());
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
            mTitleBar.setTitle(getString(R.string.title_home_view, mCheckInfo.shortName));
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

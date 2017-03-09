package com.yijia.patient.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.yijia.patient.R;
import com.yijia.patient.ui.manager.UserManager;
import com.yijia.patient.ui.protocol.ProtocolImp;
import com.yijia.patient.ui.protocol.request.JoinRequest;
import com.yijia.patient.ui.protocol.response.LoginResponse;
import com.yijia.patient.ui.protocol.response.PageResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.AppEmptyDialog;
import com.zero.library.base.view.AppProgressDialog;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/11.
 * 科研参与
 */
public class ScientificInHolder extends AppBaseHolder<PageResponse.Page.Rows> {


    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mIvJoin;
    private LoginResponse mLoginInfo;
    protected AppProgressDialog mProgressDialog;

    public ScientificInHolder(Activity activity) {
        super(activity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_scientific_in;
    }

    @Override
    public void refreshView(int position) {
        PageResponse.Page.Rows data = getData();
        mTvTitle.setText(data.getTextTarget());
        //mTvContent.setText(data.getContext());
        mIvJoin.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        UtilsUi.getNoticeDialog(mActivity, UtilsUi.getString(R.string.dialog_title_join)
                , UtilsUi.getString(R.string.dialog_message_join)
                , UtilsUi.getString(R.string.dialog_btn_join)
                , UtilsUi.getString(R.string.dialog_btn_notjoin)
                , (dialog, view) -> {
                    if (AppEmptyDialog.BtnView.LEFT == view) {
                        commitInfo();
                    }
                    dialog.dismiss();
                }).show();
    }

    @Override
    public void initView(View view) {
        mProgressDialog = new AppProgressDialog(mActivity);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mIvJoin = (TextView) view.findViewById(R.id.iv_join);
    }

    private void commitInfo() {
        mLoginInfo = UserManager.getLoginInfo();
        JoinRequest request = new JoinRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPkResearchProject(getData().getPkResearchproject());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).joinRequest(request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        if (null != mProgressDialog) mProgressDialog.show();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        AppToast.show(mActivity, response.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        if (null != mProgressDialog) mProgressDialog.dismiss();
                    }
                });
    }
}

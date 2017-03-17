package com.yijia.zkl.ui.fragment.viewdetail;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.holder.QuestionHolder;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.QusetionPageRequest;
import com.yijia.zkl.ui.protocol.request.QusetionSaveRequest;
import com.yijia.zkl.ui.protocol.request.QusetionUpdateRequest;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.yijia.zkl.ui.protocol.response.QusetionPageResponse;
import com.yijia.zkl.ui.protocol.response.ViewPageResponse;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.uibase.ListBaseFragment;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/28.
 * 医友提问
 */
public class QuestionFragment extends ListBaseFragment {
    private int mPageNum = AppConstants.FIRST_NUM;

    private ViewPageResponse.Page.Rows mViewInfo;
    private EditText mComment;
    private TextView mCommit;
    private TextView mTvUpper;
    private ImageView mIvLook;
    private LoginResponse mLoginInfo;

    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_question);
    }

    @Override
    protected int getResLayout() {
        return R.layout.activity_question;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mComment = (EditText) view.findViewById(R.id.et_comment);
        mCommit = (TextView) view.findViewById(R.id.tv_commit);
        mTvUpper = (TextView) view.findViewById(R.id.et_upper);
        mIvLook = (ImageView) view.findViewById(R.id.iv_look_history);
        mCommit.setOnClickListener(v -> commitComment());
        mIvLook.setVisibility(View.GONE);
        mCommit.setVisibility(View.VISIBLE);
        mCommit.setText(R.string.btn_send_message);
        mComment.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                UtilsUi.hideSoftInput(mActivity, mComment);
                mTvUpper.setVisibility(View.VISIBLE);
                mCommit.setVisibility(View.GONE);
                mComment.setVisibility(View.GONE);
            }
        });
        mTvUpper.setOnClickListener(v -> {
            mTvUpper.setVisibility(View.GONE);
            mCommit.setVisibility(View.VISIBLE);
            mComment.setVisibility(View.VISIBLE);
            mComment.setFocusable(true);
            mComment.setFocusableInTouchMode(true);
            mComment.requestFocus();
            UtilsUi.showSoftInput(mActivity);
        });
    }

    @Override
    protected void initData() {
        mViewInfo = (ViewPageResponse.Page.Rows) mArguments.getSerializable(AppConstants.PARCELABLE_KEY);
        mRefreshList.perfectPullRefreshSilence();
        mLoginInfo = UserManager.getLoginInfo();
        RxBus.getInstance().regist(QusetionUpdateRequest.class)
                .subscribe(question -> {
                            List<QusetionPageResponse.Page.Rows> dataList = mAdapter.getData();
                            for (QusetionPageResponse.Page.Rows item : dataList) {
                                if (item.getPkQuestion().equals(question.getPkQuestion())) {
                                    int num = item.getQuestnum();
                                    item.setQuestnum(++num);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                );
    }

    private void commitComment() {
        String comment = mComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            AppToast.show(mActivity, R.string.toast_question_empty);
            return;
        }
        QusetionSaveRequest request = new QusetionSaveRequest();
        request.setPkUser(mLoginInfo.getDatas().getPkUser());
        request.setPkView(mViewInfo.getPkView());
        request.setQuestcontent(comment);
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qusetionSaveRequest(request);
        RetrofitUtils.request(mActivity, ob, mCommit,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        showProgressDialog();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        mComment.clearFocus();
                        mComment.setText("");
                        mRefreshList.perfectPullRefreshSilence();
                        AppToast.show(mActivity, R.string.toast_comment_success);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        hidenProgressDialog();
                    }
                });
    }

    @Override
    protected DefaultAdapter getAdapter(ListView listView) {
        return new DefaultAdapter<QusetionPageResponse.Page.Rows>(mActivity, listView, new ArrayList<>(),
                AppConstants.PAGE_COUNT, true) {

            @Override
            protected AppBaseHolder getHolder() {
                return new QuestionHolder(mActivity);
            }

            @Override
            public void onLoadMore() {
                loadList();
            }
        };
    }

    private void loadList() {
        QusetionPageRequest request = new QusetionPageRequest();
        request.setPage(String.valueOf(mPageNum));
        request.setPkView(mViewInfo.getPkView());
        Observable<QusetionPageResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qusetionPageRequest
                (request);
        RetrofitUtils.request(mActivity, ob,
                new RetrofitUtils.ResponseListener<QusetionPageResponse>() {
                    @Override
                    public void beforRequest() {

                    }

                    @Override
                    public void onSuccess(QusetionPageResponse response) {
                        fillList(response);
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

    private void fillList(QusetionPageResponse response) {
        if (mPageNum == AppConstants.FIRST_NUM) {
            mAdapter.refreshData(response.getPage().getRows());
        } else {
            mAdapter.loadData(response.getPage().getRows());
        }
        mPageNum++;
    }

    @Override
    protected void onPullRefresh() {
        mPageNum = AppConstants.FIRST_NUM;
        loadList();
    }
}

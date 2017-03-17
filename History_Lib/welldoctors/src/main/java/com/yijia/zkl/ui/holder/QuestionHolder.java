package com.yijia.zkl.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijia.zkl.R;
import com.yijia.zkl.ui.manager.UserManager;
import com.yijia.zkl.ui.protocol.ProtocolImp;
import com.yijia.zkl.ui.protocol.request.QusetionUpdateRequest;
import com.yijia.zkl.ui.protocol.response.LoginResponse;
import com.yijia.zkl.ui.protocol.response.QusetionPageResponse;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.retrofit.DefaultResponse;
import com.zero.library.base.retrofit.RetrofitFactory;
import com.zero.library.base.retrofit.RetrofitUtils;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.view.AppProgressDialog;
import com.zero.library.base.utils.picasso.UtilsPicasso;

import rx.Observable;

/**
 * Created by ZeroAries on 2016/3/11.
 * 医友提问
 */
public class QuestionHolder extends AppBaseHolder<QusetionPageResponse.Page.Rows> {

    private TextView mTvDate;
    private ImageView mIvHead;
    private TextView mTvName;
    private TextView mTvContent;
    private TextView mTvQuestionToo;
    protected AppProgressDialog mProgressDialog;

    public QuestionHolder(Activity activity) {
        super(activity);
        mProgressDialog = new AppProgressDialog(mActivity);
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_question;
    }

    @Override
    public void refreshView(int position) {
        QusetionPageResponse.Page.Rows data = getData();
        UtilsPicasso.loadCenterImage(mActivity, data.getImage(),
                R.drawable.ic_default_news, mIvHead,
                R.dimen.dimen_60);
        mTvName.setText(data.getName());
        mTvDate.setText(data.getQuestdate());
        mTvContent.setText(data.getQuestcontent());
        mTvQuestionToo.setText(mActivity.getString(R.string.label_question_up, data.getQuestnum()));
        mTvQuestionToo.setOnClickListener(v -> commitQuestionToo());
    }

    private void commitQuestionToo() {
        LoginResponse loginInfo = UserManager.getLoginInfo();
        QusetionUpdateRequest request = new QusetionUpdateRequest();
        request.setPkUser(loginInfo.getDatas().getPkUser());
        request.setPkQuestion(getData().getPkQuestion());
        Observable<DefaultResponse> ob = RetrofitFactory.getRetorfit(ProtocolImp.class).qusetionUpdateRequest(request);
        RetrofitUtils.request(mActivity, ob, mTvQuestionToo,
                new RetrofitUtils.ResponseListener<DefaultResponse>() {
                    @Override
                    public void beforRequest() {
                        mProgressDialog.show();
                    }

                    @Override
                    public void onSuccess(DefaultResponse response) {
                        RxBus.getInstance().send(request);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onFinish(boolean isSuccess) {
                        mProgressDialog.dismiss();
                    }
                });
    }

    @Override
    public void initView(View view) {
        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvContent = (TextView) view.findViewById(R.id.tv_content);
        mTvQuestionToo = (TextView) view.findViewById(R.id.tv_question_too);
    }
}

package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.ItemFloorReplyBinding;
import com.healthsoulmate.zkl.forum.activity.FloorReplyActivity;
import com.healthsoulmate.zkl.forum.bean.ReplyInfo;
import com.healthsoulmate.zkl.forum.protocol.response.PageDiscussReplyResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsDetailPostsResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.zero.library.base.AppToast;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/5/5.
 * 楼层回复条目
 */
public class FloorReplyHolder extends AppBaseHolder<PageDiscussReplyResponse.PageBean.RowsBean> {
    private boolean isLocalClick = false;
    private ItemFloorReplyBinding mBinding;
    private PostsDetailPostsResponse.PageBean.RowsBean mForumInfo;

    public FloorReplyHolder(Activity activity, PostsDetailPostsResponse.PageBean.RowsBean forumInfo) {
        super(activity);
        this.mForumInfo = forumInfo;
    }

    @Override
    protected int getResLayout() {
        return R.layout.item_floor_reply;
    }

    @Override
    public void refreshView(int position) {
        PageDiscussReplyResponse.PageBean.RowsBean data = getData();
        String sender = data.getUserName();
        String senderPk = data.getPkUser();
        String receiver = data.getUserReplyName();
        String receiverPk = data.getPkUserreply();
        if (TextUtils.isEmpty(senderPk) && TextUtils.isEmpty(receiverPk)) {
            mBinding.llReplyContent.setVisibility(View.GONE);
            return;
        } else {
            mBinding.llReplyContent.setVisibility(View.VISIBLE);
        }
        if (senderPk.equals(receiverPk) || mForumInfo.getPkUser().equals(receiverPk)) receiver = "";

        SpannableStringBuilder spanBuilder = new SpannableStringBuilder();
        // 回复人信息
        SpannableString spanSend = new SpannableString(sender);
        spanSend.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(UtilsUi.getColor(R.color.bg_item_name_color));
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View widget) {
                isLocalClick = true;
                UserManager.jumpToOtherPeople(mActivity, senderPk);
            }
        }, 0, sender.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanBuilder.append(spanSend);


        // 接收人信息
        if (!TextUtils.isEmpty(receiver)) {
            spanBuilder.append("回复");
            SpannableString spanReceive = new SpannableString(receiver);
            spanReceive.setSpan(new ClickableSpan() {

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(UtilsUi.getColor(R.color.bg_item_name_color));
                    ds.setUnderlineText(false);
                }

                @Override
                public void onClick(View widget) {
                    isLocalClick = true;
                    UserManager.jumpToOtherPeople(mActivity, receiverPk);
                }
            }, 0, receiver.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanBuilder.append(spanReceive);
        }
        spanBuilder.append(":");
        spanBuilder.append(data.getContent());
        mBinding.tvReplyContent.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.tvReplyContent.setText(spanBuilder);

        mBinding.tvReplyContent.setOnClickListener(v -> {
            if (isLocalClick) {
                isLocalClick = false;
            } else {
                RxBus.getInstance().send(new ReplyInfo(data.getPkDiscuss(), data.getPkUserreply(), sender,
                        FloorReplyActivity.class.getSimpleName()));
            }
        });
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (ItemFloorReplyBinding) viewDataBinding;
    }
}

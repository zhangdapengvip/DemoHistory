package com.healthsoulmate.zkl.forum.holder;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthsoulmate.zkl.R;
import com.healthsoulmate.zkl.databinding.HolderForumDetailBinding;
import com.healthsoulmate.zkl.databinding.ItemForumImageBinding;
import com.healthsoulmate.zkl.databinding.ItemTextviewBinding;
import com.healthsoulmate.zkl.databinding.PopuFloorHostBinding;
import com.healthsoulmate.zkl.forum.activity.FloorReplyActivity;
import com.healthsoulmate.zkl.forum.activity.ForumDetailActivity;
import com.healthsoulmate.zkl.forum.activity.PhotoPreviewActivity;
import com.healthsoulmate.zkl.forum.activity.ReplyCommentActivity;
import com.healthsoulmate.zkl.forum.activity.ReprotActivity;
import com.healthsoulmate.zkl.forum.bean.ImgInfo;
import com.healthsoulmate.zkl.forum.bean.ReplyInfo;
import com.healthsoulmate.zkl.forum.protocol.response.PostsDetailPostsResponse;
import com.healthsoulmate.zkl.forum.protocol.response.PostsPageResponse;
import com.healthsoulmate.zkl.ui.manager.UserManager;
import com.zero.library.base.bus.RxBus;
import com.zero.library.base.constants.AppConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.utils.binding.ImgBinding;
import com.zero.library.base.view.AppPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZeroAries on 2016/4/29.
 * 帖子楼层条目
 */
public class ForumDetailHolder extends AppBaseHolder<PostsDetailPostsResponse.PageBean.RowsBean> {

    private HolderForumDetailBinding mBinding;
    private PostsPageResponse.PageBean.RowsBean mForumInfo;
    private final int MAX_NUMBER = 3;
    private boolean isLocalClick = false;

    public ForumDetailHolder(Activity activity, PostsPageResponse.PageBean.RowsBean forumInfo) {
        super(activity);
        this.mForumInfo = forumInfo;
    }

    @Override
    protected int getResLayout() {
        return R.layout.holder_forum_detail;
    }

    @Override
    public void refreshView(int position) {
        PostsDetailPostsResponse.PageBean.RowsBean data = getData();
        if (TextUtils.isEmpty(data.getPkDiscuss())) {
            mBinding.llHolderContent.setVisibility(View.GONE);
            return;
        } else {
            mBinding.llHolderContent.setVisibility(View.VISIBLE);
        }
        mBinding.setImgInfo(new ImgBinding(data.getUserImage(), R.drawable.ic_default_head, R.dimen.dimen_30));
        mBinding.setInfo(data);
        mBinding.ivShowAction.setOnClickListener(v -> showAction());
        mBinding.tvContent.setTextIsSelectable(true);
        mBinding.tvFloor.setText(mActivity.getString(R.string.format_floor, position + 1));
        UtilsUi.setVisibility(mBinding.tvIsfloorer, mForumInfo.getPkUser().equals(data.getPkUser()));
        List<PostsDetailPostsResponse.PageBean.RowsBean.DiscussreplysBean> discussreplys = data.getDiscussreplys();
        fillCommnetList(mBinding.llCommentList, discussreplys, position);

        mBinding.tvName.setOnClickListener(v -> UserManager.jumpToOtherPeople(mActivity, data.getPkUser()));

        String images = data.getImages();
        if (!TextUtils.isEmpty(images)) {
            String[] split = images.split(",");
            mBinding.llImgContent.removeAllViews();
            ArrayList<ImgInfo> imgList = new ArrayList<>();
            for (String url : split) {
                ImgInfo info = new ImgInfo();
                info.setPath(url);
                imgList.add(info);
            }
            for (int count = 0; count < imgList.size(); count++) {
                ItemForumImageBinding imgBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                        R.layout.item_forum_image, null, false);
                imgBinding.setImgInfo(new ImgBinding(imgList.get(count).getPath(), R.drawable.ic_default_img));
                final int currentPosition = count;
                imgBinding.ivInfo.setOnClickListener(v -> {
                    Intent photoIntent = new Intent(mActivity, PhotoPreviewActivity.class);
                    photoIntent.putParcelableArrayListExtra(AppConstants.PARCELABLE_KEY, imgList);
                    photoIntent.putExtra(PhotoPreviewActivity.POSITION, currentPosition);
                    photoIntent.putExtra(PhotoPreviewActivity.HIDENDELETE, true);
                    JumpManager.doJumpForward(mActivity, photoIntent);
                });
                mBinding.llImgContent.addView(imgBinding.getRoot());
            }
        }
    }

    /**
     * 评论内容
     */
    private void fillCommnetList(LinearLayout commentList,
                                 List<PostsDetailPostsResponse.PageBean.RowsBean.DiscussreplysBean> itemList,
                                 int position) {
        commentList.removeAllViews();
        UtilsUi.setVisibility(mBinding.llCommentContent, itemList.size() > 0);
        for (int positon = 0; positon < itemList.size() && positon < MAX_NUMBER; positon++) {
            ItemTextviewBinding textInflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                    R.layout.item_textview, null, false);
            TextView itemText = textInflate.tvItem;
            PostsDetailPostsResponse.PageBean.RowsBean.DiscussreplysBean itemInfo = itemList.get(positon);

            String senderName = itemInfo.getUserName();
            String senderPk = itemInfo.getPkUser();
            String receiver = itemInfo.getUserReplyName();
            String receiverPk = itemInfo.getPkUserreply();
            String content = itemInfo.getContent();
            if (senderPk.equals(receiverPk) || getData().getPkUser().equals(receiverPk)) receiver = "";

            // 回复人信息
            SpannableStringBuilder spanBuilder = new SpannableStringBuilder();
            SpannableString spanSend = new SpannableString(senderName);
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
            }, 0, senderName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            spanBuilder.append(content);
            itemText.setMovementMethod(LinkMovementMethod.getInstance());
            itemText.setText(spanBuilder);
            itemText.setOnTouchListener((v, event) -> {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    itemText.setHighlightColor(Color.argb(80, 100, 100, 100));
                } else if (MotionEvent.ACTION_UP == event.getAction()
                        || MotionEvent.ACTION_CANCEL == event.getAction()) {
                    itemText.setHighlightColor(Color.TRANSPARENT);
                }
                return false;
            });
            itemText.setOnClickListener(v -> {
                if (isLocalClick) {
                    isLocalClick = false;
                } else {
                    RxBus.getInstance().send(new ReplyInfo(
                            getData().getPkDiscuss(),
                            itemInfo.getPkUser(), senderName,
                            ForumDetailActivity.class.getSimpleName()));
                }
            });
            commentList.addView(textInflate.getRoot());
        }
        if (itemList.size() >= 3) {
            TextView textView = new TextView(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setPadding(0, 10, 10, 0);
            textView.setOnClickListener(v -> {
                Intent intent = new Intent(mActivity, FloorReplyActivity.class);
                intent.putExtra(AppConstants.PARCELABLE_KEY, getData());
                intent.putExtra(AppConstants.PARCELABLE_KEY_TWO, mForumInfo);
                intent.putExtra(AppConstants.EXTRA_STRING, position + 1);
                JumpManager.doJumpForward(mActivity, intent);
            });
            textView.setText("查看全部评论");
            textView.setTextColor(Color.argb(255, 255, 125, 0));
            commentList.addView(textView);
        }
    }

    /**
     * 显示选择列表
     */
    private void showAction() {
        ((ForumDetailActivity) mActivity).hidenReply();
        PopuFloorHostBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.popu_floor_host, null, false);
        View rootView = inflate.getRoot();
        ImageView parentView = mBinding.ivShowAction;
        AppPopupWindow popupWindow = new AppPopupWindow(mActivity, parentView);
        inflate.btnReply.setOnClickListener(v -> {
            popupWindow.dismiss();
            RxBus.getInstance().send(new ReplyInfo(
                    getData().getPkDiscuss(),
                    getData().getPkUser(),
                    getData().getUserName(),
                    ForumDetailActivity.class.getSimpleName()));
        });
        inflate.btnComment.setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(mActivity, ReplyCommentActivity.class);
            intent.putExtra(AppConstants.EXTRA_STRING, getData().getPkPosts());
            JumpManager.doJumpForwardWithResult(mActivity, intent, 1002);
        });
        inflate.btnReport.setOnClickListener(v -> {
            popupWindow.dismiss();
            Intent intent = new Intent(mActivity, ReprotActivity.class);
            intent.putExtra(ReprotActivity.PK_POSTS, getData().getPkPosts());
            intent.putExtra(ReprotActivity.PK_USER, getData().getPkUser());
            intent.putExtra(ReprotActivity.CONTENT, getData().getContent());
            JumpManager.doJumpForward(mActivity, intent);
        });

        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(rootView);
        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        rootView.measure(0, 0);
        popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY,
                x + parentView.getWidth() - rootView.getMeasuredWidth(),
                y + parentView.getHeight() / 2 - rootView.getMeasuredHeight() / 2);
    }

    @Override
    public void initView(View view, ViewDataBinding viewDataBinding) {
        mBinding = (HolderForumDetailBinding) viewDataBinding;
    }
}
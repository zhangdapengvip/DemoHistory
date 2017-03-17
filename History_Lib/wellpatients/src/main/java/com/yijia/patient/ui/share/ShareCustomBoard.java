package com.yijia.patient.ui.share;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.yijia.patient.R;
import com.zero.library.base.uibase.AppBaseHolder;
import com.zero.library.base.uibase.DefaultAdapter;
import com.zero.library.base.view.AppPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class ShareCustomBoard extends AppPopupWindow {

    private Activity mActivity;
    private List<ShowInfo> mInfoList;
    private ShareInfo mShareInfo;
    private UMShareListener mListener;
    private final UMShareAPI mShareApi;

    public ShareCustomBoard(Activity activity, ShareInfo info, UMShareListener listener) {
        super(activity);
        this.mShareApi = UMShareAPI.get(activity);
        this.mActivity = activity;
        this.mShareInfo = info;
        this.mListener = listener;
        initData();
        initView(activity);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.wgt_custom_board, null);
        GridView shareGrid = (GridView) rootView.findViewById(R.id.share_grid);
        DefaultAdapter shareAdapter = new DefaultAdapter<ShowInfo>(mActivity, shareGrid, mInfoList) {
            @Override
            protected AppBaseHolder<ShowInfo> getHolder() {
                return new ShareHolder(mActivity);
            }

            @Override
            public void onItemClickInner(int position) {
                ShowInfo data = getData().get(position);
                UMImage image = new UMImage(mActivity, mShareInfo.imgUrl);
                new ShareAction(mActivity)
                        .setPlatform(data.platform)
                        .setCallback(mListener)
                        .withTitle(mShareInfo.title)
                        .withText(mShareInfo.content)
                        .withMedia(image)
                        .withTargetUrl(mShareInfo.openUrl)
                        .share();
                dismiss();
            }
        };
        shareGrid.setAdapter(shareAdapter);
        setContentView(rootView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setTouchable(true);
        rootView.findViewById(R.id.cancle).setOnClickListener(v -> dismiss());
    }

    private void initData() {
        mInfoList = new ArrayList<>();
        ShowInfo infoWechat = new ShowInfo();
        infoWechat.icRes = R.drawable.umeng_socialize_wechat;
        infoWechat.title = "微信";
        infoWechat.platform = SHARE_MEDIA.WEIXIN;
        if (mShareApi.isInstall(mActivity, SHARE_MEDIA.WEIXIN))
            mInfoList.add(infoWechat);
        ShowInfo infoWxCircle = new ShowInfo();
        infoWxCircle.icRes = R.drawable.umeng_socialize_wxcircle;
        infoWxCircle.title = "朋友圈";
        infoWxCircle.platform = SHARE_MEDIA.WEIXIN_CIRCLE;
        if (mShareApi.isInstall(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE))
            mInfoList.add(infoWxCircle);
        ShowInfo infoQQ = new ShowInfo();
        infoQQ.icRes = R.drawable.umeng_socialize_qq_on;
        infoQQ.title = "QQ";
        infoQQ.platform = SHARE_MEDIA.QQ;
        mInfoList.add(infoQQ);
        ShowInfo infoQzone = new ShowInfo();
        infoQzone.icRes = R.drawable.umeng_socialize_qzone_on;
        infoQzone.title = "QQ空间";
        infoQzone.platform = SHARE_MEDIA.QZONE;
        mInfoList.add(infoQzone);
        ShowInfo infoSina = new ShowInfo();
        infoSina.icRes = R.drawable.umeng_socialize_sina_on;
        infoSina.title = "微博";
        infoSina.platform = SHARE_MEDIA.SINA;
        mInfoList.add(infoSina);
    }
}

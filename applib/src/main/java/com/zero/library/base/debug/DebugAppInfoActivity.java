package com.zero.library.base.debug;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.zero.library.R;
import com.zero.library.base.AppBase;
import com.zero.library.base.AppToast;
import com.zero.library.base.constants.DirConstants;
import com.zero.library.base.manager.JumpManager;
import com.zero.library.base.manager.PropertyManager;
import com.zero.library.base.uibase.DefaultActivity;
import com.zero.library.base.utils.UtilsFile;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.base.view.TitleBarView;

import java.io.File;
import java.text.DecimalFormat;

public class DebugAppInfoActivity extends DefaultActivity implements OnClickListener {

    private TextView mTextOne;
    private TextView mTextVersion;
    private TextView mTextImei;
    private TextView mTextHost;
    private TextView mTextPhoneInfo;
    private EditText mLookKey;
    private View mViewContent;

    @Override
    public int getResLayout() {
        return R.layout.activity_debug;
    }

    @Override
    public void initView() {
        initTitle();
        mViewContent = findViewById(R.id.view_content);
        mLookKey = (EditText) findViewById(R.id.look_key);
        mTextOne = (TextView) findViewById(R.id.text_one);
        mTextVersion = (TextView) findViewById(R.id.text_version);
        mTextImei = (TextView) findViewById(R.id.text_imei);
        mTextHost = (TextView) findViewById(R.id.text_host);
        mTextPhoneInfo = (TextView) findViewById(R.id.text_phoneinfo);
        findViewById(R.id.btn_clean).setOnClickListener(this);
        lookAdaptation();
        lookAppInformation();
        lookPhoneInfo();
    }

    @Override
    public void initData() {

    }

    private void lookPhoneInfo() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int contentWidth = rect.width();
        int contentHeight = rect.height();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        float density = metric.density;
        int densityDpi = metric.densityDpi;
        StringBuilder sb = new StringBuilder();
        sb.append("Build:" + AppBase.getMetaDate("BUILD_TYPE") + "  " + AppBase.getMetaDate("UMENG_CHANNEL") + "\n");
        sb.append("Time:" + getString(R.string.build_time) + "\n");
        sb.append("Host:" + getString(R.string.build_host) + "\n");
        sb.append("基本信息:" + UtilsUi.getContext().getPackageName() + "\n");
        sb.append("屏幕宽度=" + width + "\n");
        sb.append("屏幕高度=" + height + "\n");
        sb.append("内容区域宽度=" + contentWidth + "\n");
        sb.append("内容区域高度=" + contentHeight + "\n");
        sb.append("屏幕密度=" + density + "\n");
        sb.append("屏幕DPI=" + densityDpi + "\n");
        sb.append("长度DPI=" + contentWidth + "/" + density);
        sb.append("=" + format(contentWidth / density, "#.0") + "\n");
        sb.append("高度DPI=" + contentHeight + "/" + density);
        sb.append("=" + format(contentHeight / density, "#.0"));
        mTextPhoneInfo.setText(sb.toString());
    }

    public String format(Object value, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(value);
    }

    private void lookAppInformation() {
        mTextVersion.setText("当前版本：V" + UtilsUi.getVersionName() + "——" + UtilsUi.getVersionCode());
        mTextImei.setText("IMEI:" + UtilsUi.getIMEI());
        mTextHost.setText("HOST:" + PropertyManager.getRequestHost());
    }

    private void lookAdaptation() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        mTextOne.setWidth(width / 2);
    }

    private void initTitle() {
        TitleBarView mTitle = (TitleBarView) findViewById(R.id.title_bar);
        mTitle.setTitle("调试信息");
        mTitle.setLeftText(R.string.back);
        mTitle.setRightText("查看");
        mTitle.setLeftListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                JumpManager.doJumpBack(mActivity);
            }
        });
        mTitle.setRightListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1593570".equals(mLookKey.getText().toString().trim())) {
                    mViewContent.setVisibility(View.VISIBLE);
                    mLookKey.setVisibility(View.GONE);
                } else {
                    AppToast.show(mActivity, "密钥错误");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_clean) {
            UtilsFile.deleteAllByDirectory(new File(DirConstants.DEFAULT_LOG_DIR));
            AppToast.show(mContext, "Clean App Log");
        }
    }
}

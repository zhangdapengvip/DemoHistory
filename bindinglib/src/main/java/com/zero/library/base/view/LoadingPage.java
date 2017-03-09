package com.zero.library.base.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.FrameLayout;

import com.zero.library.base.utils.UtilsThread;
import com.zero.library.base.utils.UtilsUi;
import com.zero.library.R;

public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_UNLOADED = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 3;
    private static final int STATE_EMPTY = 4;
    private static final int STATE_SUCCEED = 5;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSucceedView;

    private Context mContext;
    private int mState;

    public LoadingPage(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_on_loading)));
        mState = STATE_UNLOADED;
        mLoadingView = createLoadingView();
        if (null != mLoadingView) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }

        mErrorView = createErrorView();
        if (null != mErrorView) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }

        mEmptyView = createEmptyView();
        if (null != mEmptyView) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        showPageSafe();
        show();
    }

    public void showPageSafe() {
        UtilsUi.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED
                    || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }

        if (mState == STATE_SUCCEED && mSucceedView == null) {
            mSucceedView = createLoadedView();
            addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }

        if (null != mSucceedView) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE
                    : View.INVISIBLE);
        }
    }

    public void reset() {
        mState = STATE_UNLOADED;
        showPageSafe();
    }

    protected boolean needReset() {
        return mState == STATE_ERROR || mState == STATE_EMPTY;
    }

    public synchronized void show() {
        if (needReset()) {
            mState = STATE_UNLOADED;
        }
        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING;
            LoadingTask task = new LoadingTask();
            UtilsThread.getPool().execute(task);
        }
        showPageSafe();
    }

    protected View createLoadingView() {
        return UtilsUi.inflate(mContext, R.layout.wgt_pager_loading_onload);
    }

    protected View createEmptyView() {
        return UtilsUi.inflate(mContext, R.layout.wgt_pager_loading_empty);
    }

    protected View createErrorView() {
        View view = UtilsUi.inflate(mContext, R.layout.wgt_pager_loading_error);
        view.findViewById(R.id.page_bt).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show();
                    }
                });
        return view;
    }

    public abstract View createLoadedView();

    public abstract void load();


    public void setState(int state) {
        this.mState = state;
    }

    class LoadingTask implements Runnable {
        @Override
        public void run() {
            load();
        }
    }

    public enum LoadResult {
        ERROR(3), EMPTY(4), SUCCEED(5);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}

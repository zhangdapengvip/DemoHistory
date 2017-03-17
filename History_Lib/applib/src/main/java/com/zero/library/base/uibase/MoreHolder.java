package com.zero.library.base.uibase;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.zero.library.R;

public class MoreHolder extends AppBaseHolder<Integer> implements OnClickListener {
    private RelativeLayout mLoading, mError, mNoMore;
    private DefaultAdapter mAdapter;

    public MoreHolder(DefaultAdapter adapter, Activity activity, boolean hasMore) {
        super(activity);
        setData(null,
                hasMore ? HaseMore.HAS_MORE.getValue() : HaseMore.NO_MORE
                        .getValue(), adapter.getCount());
        mAdapter = adapter;
    }

    public enum HaseMore {
        HAS_MORE(0), NO_MORE(1), ERROR(2);
        private int value;
        HaseMore(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    @Override
    protected int getResLayout() {
        return R.layout.wgt_more_loading;
    }


    @Override
    public void initView(View view) {

        mLoading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
        mNoMore = (RelativeLayout) view.findViewById(R.id.rl_no_more);
        mError = (RelativeLayout) view.findViewById(R.id.rl_more_error);
        mError.setOnClickListener(this);
    }

    @Override
    public void refreshView(int positon) {
        Integer data = getData();
        mLoading.setVisibility(data == HaseMore.HAS_MORE.getValue() ? View.VISIBLE
                : View.GONE);
        mError.setVisibility(data == HaseMore.ERROR.getValue() ? View.VISIBLE
                : View.GONE);
        mNoMore.setVisibility(data == HaseMore.NO_MORE.getValue() ? View.VISIBLE
                : View.GONE);
    }

    @Override
    public View getRootView() {
        if (getData() == HaseMore.HAS_MORE.getValue()) {
            loadMore();
        }
        return super.getRootView();
    }

    public MoreHolder setAdapter(DefaultAdapter adapter) {
        mAdapter = adapter;
        return this;
    }

    @Override
    public void onClick(View v) {
        mError.setVisibility(View.GONE);
        mNoMore.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
        loadMore();
    }

    public void loadMore() {
        mAdapter.loadMore();
    }
}

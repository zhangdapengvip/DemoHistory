package com.zero.library.base.uibase;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zero.library.R;
import com.zero.library.base.AppToast;
import com.zero.library.base.uibase.MoreHolder.HaseMore;
import com.zero.library.base.utils.UtilsUi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class DefaultAdapter<Data> extends BaseAdapter implements RecyclerListener, OnItemClickListener {
    public static final int MORE_VIEW_TYPE = 0;
    public static final int ITEM_VIEW_TYPE = 1;

    private boolean mHasMore = false;
    private List<AppBaseHolder> mDisplayedHolders;
    protected AbsListView mListView;
    protected List<? extends Data> mDatas;
    private volatile boolean mIsLoading;
    private MoreHolder mMoreHolder;
    private Activity mActiviy;
    private int pageSize = 1;

    /**
     * @param activiy  当前Activiy
     * @param listView 调用listview
     * @param datas    空数据List
     */
    public DefaultAdapter(Activity activiy, AbsListView listView, List<? extends Data> datas) {
        this.mActiviy = activiy;
        initAdapter(listView, datas);
    }

    /**
     * @param activiy  当前Activiy
     * @param listView 调用listview
     * @param datas    空数据List
     * @param pageSize 分页页码数
     * @param hasMore  是否加载更多，true是需实现{@link #onLoadMore()}
     */
    public DefaultAdapter(Activity activiy, AbsListView listView, List<? extends Data> datas, int pageSize, boolean
            hasMore) {
        this.pageSize = pageSize;
        this.mHasMore = hasMore;
        this.mActiviy = activiy;
        initAdapter(listView, datas);
    }


    private void initAdapter(AbsListView listView, List<? extends Data> datas) {
        mDisplayedHolders = new ArrayList<AppBaseHolder>();
        mListView = listView;
        if (null != listView) {
            listView.setRecyclerListener(this);
            listView.setOnItemClickListener(this);
        }
        setData(datas);
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        if (null != view) {
            Object tag = view.getTag();
            if (tag instanceof AppBaseHolder) {
                AppBaseHolder holder = (AppBaseHolder) tag;
                synchronized (mDisplayedHolders) {
                    mDisplayedHolders.remove(holder);
                }
                holder.recycle();
            }
        }
    }

    public List<AppBaseHolder> getDisplayedHolders() {
        synchronized (mDisplayedHolders) {
            return new ArrayList<AppBaseHolder>(mDisplayedHolders);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setData(List<? extends Data> datas) {
        mDatas = datas;
    }

    public void refreshData(List<? extends Data> datas) {
        mDatas.clear();
        mDatas = datas;
        getMoreHolder().setData(null, hasMore() ? HaseMore.HAS_MORE.getValue() : HaseMore.NO_MORE.getValue(),
                getCount());
        notifyDataSetChanged();
    }

    public List<Data> getData() {
        return (List<Data>) mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size() >= pageSize && hasMore() ? mDatas.size() + 1 : mDatas.size();
        }
        return 0;
    }

    @Override
    public Data getItem(int position) {
        int checkPosition = position - getHeaderViewCount();
        if (mDatas != null && checkPosition >= 0 && checkPosition < mDatas.size()) {
            return mDatas.get(checkPosition);
        }
        return null;
    }

    public int getHeaderViewCount() {
        int count = 0;
        if (mListView != null && mListView instanceof ListView) {
            ListView listView = (ListView) mListView;
            count = listView.getHeaderViewsCount();
        }
        return count;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1 && position >= pageSize && hasMore()) {
            return MORE_VIEW_TYPE;
        } else {
            return getItemViewTypeInner(position);
        }
    }

    public int getItemViewTypeInner(int position) {
        return ITEM_VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppBaseHolder<Data> holder;
        if (convertView != null && convertView.getTag() instanceof AppBaseHolder) {
            holder = (AppBaseHolder<Data>) convertView.getTag();
        } else {
            if (getItemViewType(position) == MORE_VIEW_TYPE) {
                holder = getMoreHolder();
            } else {
                holder = getHolder();
            }
        }
        if (getItemViewType(position) == ITEM_VIEW_TYPE && mDatas.size() > position) {
            holder.setData(mDatas, mDatas.get(position), position);
        }
        mDisplayedHolders.add(holder);
        return holder.getRootView();
    }

    private AppBaseHolder getMoreHolder() {
        if (mMoreHolder == null) {
            mMoreHolder = new MoreHolder(this, mActiviy, hasMore());
        }
        return mMoreHolder;
    }

    public void loadMore() {
        if (!UtilsUi.isNetworkConnected(mActiviy)) {
            getMoreHolder().setData(null, HaseMore.ERROR.getValue(), getCount());
            AppToast.show(mActiviy, R.string.error_service_error);
            return;
        }
        if (!mIsLoading) {
            mIsLoading = true;
            Observable.just(1).observeOn(Schedulers.newThread()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    onLoadMore();
                }
            });
        }
    }

    public void loadError() {
        UtilsUi.post(new Runnable() {

            @Override
            public void run() {
                getMoreHolder().setData(null, HaseMore.ERROR.getValue(), getCount());
                notifyDataSetChanged();
                mIsLoading = false;
            }
        });
    }

    public void loadData(final List<? extends Data> datas) {
        UtilsUi.post(new Runnable() {

            @Override
            public void run() {
                if (datas != null) {
                    if (getData() != null && getData().size() > 0) {
                        getData().addAll(datas);
                    } else {
                        setData(datas);
                    }
                }
                getMoreHolder().setData(null, datas.size() < pageSize ? HaseMore.NO_MORE.getValue() : HaseMore
                                .HAS_MORE.getValue(),
                        getCount());
                notifyDataSetChanged();
                mIsLoading = false;
            }
        });
    }

    /**
     * 创建对应的BaseHolder,{@link AppBaseHolder}
     *
     * @return 返回BaseHolder实体
     */
    protected abstract AppBaseHolder getHolder();


    /**
     * 分页时加载更多数据时调用
     */
    public void onLoadMore() {
    }

    private boolean hasMore() {
        return mHasMore;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int clickPosition = position - getHeaderViewCount();
        int size = getData().size();
        if (clickPosition >= 0 && clickPosition < size) {
            onItemClickInner(clickPosition);
        }
    }

    public void onItemClickInner(int position) {

    }
}

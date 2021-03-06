package com.yijia.zkl.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yijia.zkl.ui.protocol.response.NewsPage;
import com.zero.library.R;
import com.zero.library.base.uibase.DefaultPagerAdapter;
import com.zero.library.base.utils.picasso.UtilsPicasso;
import com.zero.library.base.view.Indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("HandlerLeak")
public class ScrollingViewPager extends RelativeLayout {

    private static final int DEFAULT_TIME = 3;
    private static final int TAG_SCORLL = 1001;
    private static final boolean DEFAULT_ALLOW = false;
    private RelativeLayout mContextView;
    private int mContinuedTime;
    private boolean mAllowScroll;
    private CirclePageIndicator mIndicator;
    private ViewPager mViewPager;
    private Context mContext;
    private int mCurrentPosition = 0;
    private MyAdapter myAdapter;
    private List<NewsPage> mDataLists = new ArrayList<>();
    private PagerClickListener onPagerClickListener;
    private boolean isStop = false;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == TAG_SCORLL) {
                mCurrentPosition++;
                mViewPager.setCurrentItem(mCurrentPosition);
                handler.sendEmptyMessageDelayed(TAG_SCORLL,
                        mContinuedTime * 1000);
            }
        }
    };

    public ScrollingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mContextView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.wgt_horizontal_carousel_viewpager, this, true);
        TypedArray mTypeArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontlScrollViewPager);
        mContinuedTime = mTypeArray.getInteger(R.styleable.HorizontlScrollViewPager_scrollDurationMinute, DEFAULT_TIME);
        mAllowScroll = mTypeArray.getBoolean(R.styleable.HorizontlScrollViewPager_allowScroll, DEFAULT_ALLOW);
        mViewPager = (ViewPager) mContextView.findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator) mContextView
                .findViewById(R.id.circle_indicator);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                mCurrentPosition = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        mViewPager.setOnTouchListener(new OnTouchListener() {

            private long downTime;
            private float downX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downTime = System.currentTimeMillis();
                        downX = v.getScrollX();
                        stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        long upTime = System.currentTimeMillis();
                        float upX = v.getScrollX();
                        if ((upTime - downTime) < 200 && Math.abs(upX - downX) < 3 && null != onPagerClickListener) {
                            onPagerClickListener.onPagerClick(mCurrentPosition % mDataLists.size());
                        }
                        reStart();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        reStart();
                        break;
                }
                return false;
            }
        });
        mTypeArray.recycle();
    }

    public void start() {
        if (null == mDataLists || mDataLists.size() <= 0) {
            return;
        }
        mViewPager.setBackgroundColor(Color.TRANSPARENT);
        if (mAllowScroll) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(TAG_SCORLL, mContinuedTime * 1000);
        }
    }

    public void stop() {
        isStop = true;
        handler.removeCallbacksAndMessages(null);
    }

    public void reStart() {
        if (isStop) {
            start();
        }
    }

    public void setDatas(List<NewsPage> dataLists) {
        if (null == dataLists || dataLists.size() <= 0) {
            return;
        }
        mDataLists = dataLists;
        myAdapter = new MyAdapter(dataLists, Integer.MAX_VALUE);
        mViewPager.setAdapter(myAdapter);
        mIndicator.setPageCount(mDataLists.size());
        mIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(mDataLists.size() * 100);
    }

    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    public List<NewsPage> getDatas() {
        return mDataLists;
    }

    public void setOnPagerClickListener(PagerClickListener onPagerClickListener) {
        this.onPagerClickListener = onPagerClickListener;
    }

    public interface PagerClickListener {
        void onPagerClick(int position);
    }

    private class MyAdapter extends DefaultPagerAdapter<NewsPage> {

        public MyAdapter(List<NewsPage> dataList, int count) {
            super(dataList, count);
        }

        @Override
        protected View initItemView(NewsPage page, int position) {
            LinearLayout mPagerItem = (LinearLayout) View.inflate(mContext, R.layout.wgt_horizontalscroll_viewpager,
                    null);
            ImageView mImageContext = (ImageView) mPagerItem.findViewById(R.id.img_content);
            Picasso.with(mContext)
                    .load(page.getPictureurl())
                    .config(Bitmap.Config.RGB_565)
                    .resizeDimen(R.dimen.dimen_320, R.dimen.dimen_130)
                    .placeholder(R.drawable.bg_horizontal_default)
                    .error(R.drawable.bg_horizontal_default)
                    .into(mImageContext);
            return mPagerItem;
        }
    }
}

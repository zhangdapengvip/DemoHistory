package com.zero.library.base.view.wheel.uitls;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zero.library.base.utils.UtilsDate;
import com.zero.library.base.view.wheel.OnWheelChangedListener;
import com.zero.library.base.view.wheel.WheelView;
import com.zero.library.base.view.wheel.adapters.ArrayWheelAdapter;
import com.zero.library.R;

public class WheelDateSelector implements OnWheelChangedListener,
        OnClickListener {
    private static final float MIN_TEXTSIZE = 18;
    private static final float MAX_TEXTSIZE = 20;
    private Activity mActivity;
    private View mParentView;
    private View popupWindowLayout;
    private WheelView mViewDate;
    private WheelView mViewTime;
    private Button mBtnConfirm;
    private PopupWindow mDateSelector = null;
    protected String[] mDates;
    protected String[] mTimes = {"上午", "下午"};
    protected DateSelectListener onDateSelect;
    private ArrayWheelAdapter<String> mDateAdapter;
    private ArrayWheelAdapter<String> mTimeAdapter;

    public WheelDateSelector(Activity context, View parentView,
                             DateSelectListener listener) {
        mActivity = context;
        mParentView = parentView;
        onDateSelect = listener;
        popupWindowLayout = LayoutInflater.from(mActivity).inflate(
                R.layout.wgt_wheel_datechoice, null);
        initView();
        initDateAndTime();
        mDateAdapter = new ArrayWheelAdapter<String>(mActivity, mDates);
        mViewDate.setViewAdapter(mDateAdapter);
        mTimeAdapter = new ArrayWheelAdapter<String>(mActivity, mTimes);
        mViewTime.setViewAdapter(mTimeAdapter);
    }

    private void initView() {
        mViewDate = (WheelView) popupWindowLayout.findViewById(R.id.id_date);
        mViewTime = (WheelView) popupWindowLayout.findViewById(R.id.id_time);
        mBtnConfirm = (Button) popupWindowLayout.findViewById(R.id.btn_confirm);
        mViewDate.setVisibleItems(7);
        mViewTime.setVisibleItems(7);
        mViewDate.addChangingListener(this);
        mViewTime.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);
    }

    public void show() {
        hideInputMethod(mActivity);
        getDateSelector().showAtLocation(mParentView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismis() {
        getDateSelector().dismiss();
    }

    private PopupWindow getDateSelector() {
        if (mDateSelector == null) {
            mDateSelector = new PopupWindow(popupWindowLayout,
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mDateSelector.setFocusable(true);
            mDateSelector.setOutsideTouchable(true);
            mDateSelector.setTouchable(true);
            mDateSelector.setAnimationStyle(R.style.push_bottom_anim);
            mDateSelector.setBackgroundDrawable(new BitmapDrawable(mActivity
                    .getResources(), (Bitmap) null));
        }
        return mDateSelector;
    }

    public static void hideInputMethod(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = context.getCurrentFocus();
        if (view != null) {
            IBinder token = view.getWindowToken();
            if (token != null) {
                inputMethodManager.hideSoftInputFromWindow(context
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            int currentDate = mViewDate.getCurrentItem();
            Date selectDate = UtilsDate.nextDay(new Date(), currentDate);
            int currentTime = mViewTime.getCurrentItem();
            onDateSelect.onDateSelect(selectDate, mTimes[currentTime]);
        }
    }

    public interface DateSelectListener {
        void onDateSelect(Date date, String time);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewDate) {
            updateTime();
// refreshTextSize(mDateAdapter, wheel);
        } else if (wheel == mViewTime) {
// refreshTextSize(mTimeAdapter, wheel);
        }
    }

    @SuppressWarnings("unused")
    private void refreshTextSize(ArrayWheelAdapter<String> adapter,
                                 WheelView wheel) {
        String currentText = (String) adapter.getItemText(wheel
                .getCurrentItem());
        setTextviewSize(currentText, adapter);
    }

    public void setTextviewSize(String curriteItemText,
                                ArrayWheelAdapter<String> adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAX_TEXTSIZE);
            } else {
                textvew.setTextSize(MIN_TEXTSIZE);
            }
        }
    }

    private void updateTime() {
        mViewTime.setViewAdapter(new ArrayWheelAdapter<String>(mActivity,
                mTimes));
        mViewTime.setCurrentItem(0);
    }

    protected void initDateAndTime() {
        mDates = new String[30];
        for (int i = 0; i < 30; i++) {
            if (i == 0) {
                mDates[i] = "今天      ";
            } else {
                mDates[i] = UtilsDate.formatDate(
                        UtilsDate.nextDay(new Date(), i),
                        UtilsDate.FORMAT_DATE_MONTHDAY)
                        + "  "
                        + UtilsDate.getWeak(UtilsDate.nextDay(new Date(), i));
            }
        }
    }
}

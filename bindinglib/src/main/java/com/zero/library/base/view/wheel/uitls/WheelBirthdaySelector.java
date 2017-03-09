package com.zero.library.base.view.wheel.uitls;

import java.util.ArrayList;
import java.util.Calendar;

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

import com.zero.library.R;
import com.zero.library.base.view.wheel.OnWheelChangedListener;
import com.zero.library.base.view.wheel.WheelView;
import com.zero.library.base.view.wheel.adapters.ArrayWheelAdapter;

public class WheelBirthdaySelector implements OnWheelChangedListener,
        OnClickListener {
    private static final float MIN_TEXTSIZE = 18;
    private static final float MAX_TEXTSIZE = 20;
    private Activity mActivity;
    private View mParentView;
    private View popupWindowLayout;
    private WheelView mViewYear;
    private WheelView mViewMonth;
    private WheelView mViewDay;
    private Button mBtnConfirm;
    private PopupWindow mDateSelector = null;
    protected String[] mYears;
    protected String[] mMonth = {"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12"};
    protected String[] mDays;
    protected BirthdaySelectListener onDateSelect;
    private ArrayWheelAdapter<String> mDateAdapter;
    private ArrayWheelAdapter<String> mTimeAdapter;

    public WheelBirthdaySelector(Activity context, View parentView,
                                 BirthdaySelectListener listener) {
        mActivity = context;
        mParentView = parentView;
        onDateSelect = listener;
        popupWindowLayout = LayoutInflater.from(mActivity).inflate(
                R.layout.wgt_wheel_birthdaychoice, null);
        initView();
        initYearAndMonth();
        mDateAdapter = new ArrayWheelAdapter<String>(mActivity, mYears);
        mViewYear.setViewAdapter(mDateAdapter);
        mTimeAdapter = new ArrayWheelAdapter<String>(mActivity, mMonth);
        mViewMonth.setViewAdapter(mTimeAdapter);
        mViewYear.setCurrentItem(70);
        mViewMonth.setCurrentItem(6);
        updateDay();
    }

    private void initView() {
        mViewYear = (WheelView) popupWindowLayout.findViewById(R.id.id_year);
        mViewMonth = (WheelView) popupWindowLayout.findViewById(R.id.id_month);
        mViewDay = (WheelView) popupWindowLayout.findViewById(R.id.id_day);
        mBtnConfirm = (Button) popupWindowLayout.findViewById(R.id.btn_confirm);
        mViewYear.setVisibleItems(7);
        mViewMonth.setVisibleItems(7);
        mViewDay.setVisibleItems(7);
        mViewYear.addChangingListener(this);
        mViewMonth.addChangingListener(this);
        mViewDay.addChangingListener(this);
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
            int currentYear = mViewYear.getCurrentItem();
            int currentMonth = mViewMonth.getCurrentItem();
            int currentDay = mViewDay.getCurrentItem();
            onDateSelect.onBirthdaySelect(mYears[currentYear],
                    mMonth[currentMonth], mDays[currentDay]);
        }
    }

    public interface BirthdaySelectListener {
        void onBirthdaySelect(String year, String month, String day);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewYear) {
            updateDay();
        } else if (wheel == mViewMonth) {
            updateDay();
        } else if (wheel == mViewDay) {

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

    private void updateDay() {
        int currentYear = mViewYear.getCurrentItem();
        int currentTime = mViewMonth.getCurrentItem();
        int currentDay = mViewDay.getCurrentItem();
        String year = mYears[currentYear];
        String month = mMonth[currentTime];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month), 0);
        int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
        mDays = new String[maxDay];
        for (int i = 0; i < maxDay; i++) {
            if (i < 9) {
                mDays[i] = "0" + (i + 1) + "";
            } else {
                mDays[i] = (i + 1) + "";
            }
        }
        mViewDay.setViewAdapter(new ArrayWheelAdapter<String>(mActivity, mDays));
        mViewDay.setCurrentItem(0);
    }

    protected void initYearAndMonth() {
        mYears = new String[100];
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        for (int i = 99; i >= 0; i--) {
            mYears[i] = (year - 99 + i) + "";
        }
    }
}

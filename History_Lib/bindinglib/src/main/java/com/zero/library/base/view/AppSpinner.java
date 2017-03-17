package com.zero.library.base.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zero.library.R;
import com.zero.library.base.bean.SpinnerItem;
import com.zero.library.base.utils.UtilsListView;

import java.util.List;

public class AppSpinner extends LinearLayout {
    private Context mContext;
    private LinearLayout mContextView;
    private RelativeLayout mBtnCheck;
    private MySpinnerAdapter mAdapter;
    private List<SpinnerItem> mDataList;
    private PopupWindow popupWindow;
    private TextView mTvContent;
    private int maxItemCount = 5;
    private ImageView mImgCheck;
    private SpinnerItem mCheckItem = new SpinnerItem("", "");
    private final View mContent;

    public AppSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mContextView = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.wgt_spinner_layout, this, true);
        mContent = mContextView.findViewById(R.id.view_content);
        mBtnCheck = (RelativeLayout) mContextView.findViewById(R.id.btn_check);
        mImgCheck = (ImageView) findViewById(R.id.img_check);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mContextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showWindow();
            }
        });
    }

    public void setBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContextView.setBackground(drawable);
        } else {
            mContextView.setBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundResource(int resId) {
        mContextView.setBackgroundResource(resId);
    }

    public View getCheckBtn() {
        return mBtnCheck;
    }

    public void setViewBackground(int resId) {
        mContent.setBackgroundResource(resId);
    }

    public void setViewBackgroundColor(int color) {
        mContent.setBackgroundColor(color);
    }

    public String getText() {
        return mTvContent.getText().toString().trim();
    }

    public void setKey(String key) {
        for (SpinnerItem item : mDataList) {
            if (null != key && key.equals(item.getKey())) {
                mTvContent.setText(item.getValue());
                mCheckItem = item;
            }
        }
    }

    public void setIcon(int resid) {
        mImgCheck.setImageResource(resid);
    }

    public void setText(CharSequence text) {
        mTvContent.setText(text);
    }

    public void setText(int resid) {
        mTvContent.setText(resid);
    }

    public void setHintText(CharSequence hint) {
        mTvContent.setHint(hint);
    }

    public void setHintText(int resid) {
        mTvContent.setHint(resid);
    }

    public String getHintText() {
        return mTvContent.getHint().toString().trim();
    }

    public void setHintTextColor(int color) {
        mTvContent.setHintTextColor(color);
    }

    public void setHintTextColor(ColorStateList colors) {
        mTvContent.setHintTextColor(colors);
    }

    private void showWindow() {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.wgt_spinner_content, null);
        ListView listView = (ListView) layout.findViewById(R.id.spinner_list);
        listView.setAdapter(mAdapter);
        UtilsListView.setListViewMaxHeight(listView, maxItemCount);
        popupWindow = new PopupWindow(mContextView);
        popupWindow.setWidth(mContextView.getWidth());
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        popupWindow.showAsDropDown(mContextView, 0, 0);
        Animation animation = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_btn_rotate_to180);
        mImgCheck.startAnimation(animation);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Animation animation = AnimationUtils.loadAnimation(mContext,
                        R.anim.anim_btn_rotate_to360);
                mImgCheck.startAnimation(animation);
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mCheckItem = mDataList.get(position);
                mTvContent.setText(mCheckItem.getValue());
                popupWindow.dismiss();
                popupWindow = null;
            }
        });
    }

    public SpinnerItem getCheckItem() {
        return mCheckItem;
    }

    public void setMaxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
    }

    public void setList(List<SpinnerItem> dataList) {
        mDataList = dataList;
        mAdapter = new MySpinnerAdapter(mContext, mDataList);
    }

    public class MySpinnerAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        List<SpinnerItem> list;

        public MySpinnerAdapter(Context context, List<SpinnerItem> list) {
            super();
            this.context = context;
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.wgt_spinner_item, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView
                        .findViewById(R.id.spinner_dropdown_txt);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(list.get(position).getValue());
            return convertView;
        }

        public class ViewHolder {
            TextView textView;
        }

        public void refresh(List<SpinnerItem> refreshList) {
            this.list.clear();
            list.addAll(refreshList);
            notifyDataSetChanged();
        }

        public void add(List<SpinnerItem> addList) {
            list.addAll(addList);
            notifyDataSetChanged();
        }
    }
}

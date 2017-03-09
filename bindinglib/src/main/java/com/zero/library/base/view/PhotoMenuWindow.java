package com.zero.library.base.view;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zero.library.R;

public class PhotoMenuWindow extends AppBottomMenuWindow {

    public static final int BUTTON_ONE = 0;
    public static final int BUTTON_TWO = 2;
    public static final int BUTTON_CANCLE = 3;
    private Button view_btn1;
    private Button view_btn2;
    private Button view_btn3;
    private Activity context;

    public PhotoMenuWindow(Activity context) {
        super(context, R.layout.wgt_bottommenuwindow_layout);
        this.context = context;
        addPhotoChild();
    }

    private void addPhotoChild() {
        setLayoutPadding(0, 0, 0, 20);
        LinearLayout.LayoutParams bottonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        view_btn1 = new Button(context);
        view_btn1.setText(context.getString(R.string.take_photo));
        view_btn1.setTextSize(20);
        view_btn1.setTextColor(context.getResources().getColor(
                R.color.photo_menu_textcolor));
        view_btn1.setBackgroundResource(R.drawable.bg_edittext_top);
        view_btn1.setLayoutParams(bottonParams);

        view_btn2 = new Button(context);
        view_btn2.setText(context
                .getString(R.string.photot_select_from_gellary));
        view_btn2.setTextSize(20);
        view_btn2.setTextColor(context.getResources().getColor(
                R.color.photo_menu_textcolor));
        view_btn2.setBackgroundResource(R.drawable.bg_edittext_bottom);
        view_btn2.setLayoutParams(bottonParams);

        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView view_img1 = new ImageView(context);
        view_img1.setBackgroundResource(R.drawable.bg_line_simple);
        view_img1.setLayoutParams(imgParams);

        LinearLayout.LayoutParams cancleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        cancleParams.setMargins(0, 20, 0, 0);

        view_btn3 = new Button(context);
        view_btn3.setText(context.getString(R.string.cancel));
        view_btn3.setTextSize(20);
        view_btn3.setTextColor(context.getResources().getColor(
                R.color.photo_menu_textcolor));
// TextPaint pt = view_btn3.getPaint();
// pt.setFakeBoldText(true);
        view_btn3.setBackgroundResource(R.drawable.bg_edittext_center);
        view_btn3.setGravity(Gravity.CENTER);
        view_btn3.setLayoutParams(cancleParams);

        addChild(view_btn1, view_img1, view_btn2, view_btn3);
    }

    public Button getView_btn1() {
        return view_btn1;
    }

    public Button getView_btn2() {
        return view_btn2;
    }

    public Button getView_btn3() {
        return view_btn3;
    }
}

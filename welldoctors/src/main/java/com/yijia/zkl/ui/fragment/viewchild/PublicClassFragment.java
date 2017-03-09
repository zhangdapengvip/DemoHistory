package com.yijia.zkl.ui.fragment.viewchild;

import com.yijia.zkl.R;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/11.
 * 医学公开课
 */
public class PublicClassFragment extends ViewBaseFragment {
    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_public_class_name);
    }

    @Override
    protected String getType() {
        return "2";
    }
}
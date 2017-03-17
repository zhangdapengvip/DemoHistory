package com.yijia.zkl.ui.fragment.newschild;

import com.yijia.zkl.R;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/11.
 * 教育
 */
public class EducateNewsFragment extends NewsBaseFragment {
    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_educate_name);
    }

    @Override
    public String getType() {
        return "2";
    }
}

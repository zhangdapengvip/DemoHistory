package com.yijia.zkl.ui.fragment.viewchild;

import com.yijia.zkl.R;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/11.
 * 名医医院
 */
public class FamousHospitalsFragment extends ViewBaseFragment {
    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_famous_hospital_name);
    }

    @Override
    protected String getType() {
        return "1";
    }
}
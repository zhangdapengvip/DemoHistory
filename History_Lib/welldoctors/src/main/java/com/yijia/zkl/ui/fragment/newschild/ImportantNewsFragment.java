package com.yijia.zkl.ui.fragment.newschild;

import com.yijia.zkl.R;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/11.
 * 要闻
 */
public class ImportantNewsFragment extends NewsBaseFragment {
    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_important_news_name);
    }

    @Override
    public String getType() {
        return "0";
    }
}

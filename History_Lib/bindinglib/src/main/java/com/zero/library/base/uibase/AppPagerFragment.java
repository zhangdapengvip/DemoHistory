package com.zero.library.base.uibase;

import com.zero.library.base.utils.UtilsSharedPreference;
import com.zero.library.base.view.LoadingPage;
import com.zero.library.base.view.LoadingPage.LoadResult;

public abstract class AppPagerFragment extends AppBaseFragment {

    /**
     * 被选中时调用
     */
    public void onSelected() {
    }

    /**
     * 返回对应Tab的标题信息
     *
     * @return 标题
     */
    public abstract String getPagerTitle();

    /**
     * 保存数据
     *
     * @param key   key
     * @param value value
     */
    protected void saveInfo(String key, String value) {
        UtilsSharedPreference.setStringValue(key, value);
    }

    /**
     * 获取数据
     *
     * @param key key
     * @return 保存内容
     */
    protected String getSaveInfo(String key) {
        return UtilsSharedPreference.getStringValue(key);
    }
}

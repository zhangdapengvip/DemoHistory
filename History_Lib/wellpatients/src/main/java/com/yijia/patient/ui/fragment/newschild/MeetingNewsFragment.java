package com.yijia.patient.ui.fragment.newschild;

import com.yijia.patient.R;
import com.zero.library.base.utils.UtilsUi;

/**
 * Created by ZeroAries on 2016/3/11.
 * 会议
 */
public class MeetingNewsFragment extends NewsBaseFragment {
    @Override
    public String getPagerTitle() {
        return UtilsUi.getString(R.string.label_meeting_name);
    }


    @Override
    public String getType() {
        return "1";
    }
}

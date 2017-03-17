package com.yijia.zkl.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 参加科研招募
 */
public class JoinRequest extends DefaultRequest {
    private String pkUser;//用户主键
    private String pkResearchProject;//科研项目主键

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getPkResearchProject() {
        return pkResearchProject;
    }

    public void setPkResearchProject(String pkResearchProject) {
        this.pkResearchProject = pkResearchProject;
    }
}

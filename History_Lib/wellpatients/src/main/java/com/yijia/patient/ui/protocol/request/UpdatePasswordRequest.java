package com.yijia.patient.ui.protocol.request;

import com.zero.library.base.retrofit.DefaultRequest;

/**
 * Created by ZeroAries on 2016/3/21.
 * 修改密码
 */
public class UpdatePasswordRequest extends DefaultRequest {
    private String pkUser;
    private String oldpasswword;
    private String newpassword;

    public String getPkUser() {
        return pkUser;
    }

    public void setPkUser(String pkUser) {
        this.pkUser = pkUser;
    }

    public String getOldpasswword() {
        return oldpasswword;
    }

    public void setOldpasswword(String oldpasswword) {
        this.oldpasswword = oldpasswword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}

package com.elegps.javabean;

import java.io.Serializable;

public class AppUserInfo implements Serializable{


    /**
     * Sucess : true
     * UserID : 8888
     * UserName : 工人测试02
     * RoleName : 工人
     */

    private boolean Sucess;
    private String UserID;
    private String UserName;
    private String RoleName;

    public boolean isSucess() {
        return Sucess;
    }

    public void setSucess(boolean Sucess) {
        this.Sucess = Sucess;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }
}

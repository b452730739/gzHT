package com.lcd.project.module.login;

import com.lcd.project.module.BasePresenter;
import com.lcd.project.module.BaseView;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{

        void loginFail(String message);
        void loginSucceeded();
        void userID(String userID);
        void userInfo(String userInfo);
        void deptList(String deptList);
        void huiQianInfo(String duiQianInfo);
        void deptInfoByUnitID(String deptInfoByUnitID);
        void yueShiInfo(String yueShiInfo);
        void dictTypeInfo(String dictTypeInfo);
        void roleList(String roleList);

    }
    interface Presenter extends BasePresenter{
        void onLogin(String strUserID,String strPWD, String strMobileCode,  String strMobileType);
        void getUserID(String strUserName);
        void getUserInfo(String strUserID);
        void getDeptList(String strUserID);
        void getHuiQianInfo(String strUnitID,String strExceptDeptList);
        void getDeptInfoByUnitID(String strUnitID);
        void getYueShiInfo(String strUnitID);
        void getDictTypeInfo(String strZDType);
        void getRoleList(String strUserID);
    }
}

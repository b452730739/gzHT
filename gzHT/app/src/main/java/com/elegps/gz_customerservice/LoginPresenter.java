package com.lcd.project.module.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.lcd.androidbaseframe.data.source.remote.ExceptionMsgManager;
import com.lcd.project.repository.RemoteDataByCommonService;
import com.lcd.project.repository.RemoteDataByGongWenService;
import com.lcd.project.repository.RemoteDataByUserService;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private String TAG = this.getClass().getName();
    private LoginContract.View mLoginView = null;
    public LoginPresenter(@NonNull LoginContract.View mLoginView ) {
        this.mLoginView = mLoginView;
        this.mLoginView.setPresenter(this);
    }

    @Override
    public void onLogin(String strUserID, String strPWD, String strMobileCode, String strMobileType) {

        RemoteDataByUserService.UserLogon(strUserID, strPWD, strMobileCode, strMobileType, new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {
                if (TextUtils.isEmpty(s)){ //登陆失败
                    mLoginView.loginFail("登录失败");
                }else if (!s.equals("1")){
                    mLoginView.loginFail(s);
                }else{
                    mLoginView.loginSucceeded();
                }
            }
        });
    }

    @Override
    public void getUserID(String strUserName) {
    RemoteDataByUserService.GetUserIDByName(strUserName, new Subscriber<String>() {
        @Override
        public void onCompleted() {}
        @Override
        public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
         @Override
        public void onNext(String s) {mLoginView.userID(s);}
    });
    }

    @Override
    public void getUserInfo(String strUserID) {
    RemoteDataByUserService.GetUserInfo(strUserID, new Subscriber<String>() {
        @Override
        public void onCompleted() {}
        @Override
        public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
        @Override
        public void onNext(String s) {mLoginView.userInfo(s);}
    });
    }

    @Override
    public void getDeptList(String strUserID) {
        RemoteDataByUserService.GetDeptList(strUserID, new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {mLoginView.deptList(s);}
        });
    }

    @Override
    public void getHuiQianInfo(String strUnitID, String strExceptDeptList) {
        RemoteDataByGongWenService.GetHuiQianInfo(strUnitID,strExceptDeptList, new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {mLoginView.huiQianInfo(s);}
        });
    }

    @Override
    public void getDeptInfoByUnitID(String strUnitID) {
        RemoteDataByUserService.GetDeptInfoByUnitID(strUnitID, new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {mLoginView.deptInfoByUnitID(s);}
        });
    }

    @Override
    public void getYueShiInfo(String strUnitID) {
        RemoteDataByGongWenService.GetYueShiInfo(strUnitID,new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {mLoginView.yueShiInfo(s);}
        });
    }

    @Override
    public void getDictTypeInfo(String strZDType) {
        RemoteDataByCommonService.GetDictTypeInfo(strZDType,new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {mLoginView.dictTypeInfo(s);}
        });
    }

    @Override
    public void getRoleList(String strUserID) {
    RemoteDataByUserService.GetRoleList(strUserID,new Subscriber<String>() {
        @Override
        public void onCompleted() {}
        @Override
        public void onError(Throwable e) {
        mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
        @Override
        public void onNext(String s) {mLoginView.roleList(s);}
    });
    }

    @Override
    public void onStart() {

    }
}

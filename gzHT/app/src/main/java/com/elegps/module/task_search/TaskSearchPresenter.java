package com.elegps.module.task_search;

import android.text.TextUtils;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.javabean.AppUserInfo;
import com.google.gson.Gson;
import com.soap.RemoteDataByAppMemberService;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoginPresenter implements TaskSearchContract.Presenter{
    private String TAG = this.getClass().getName();
    private TaskSearchContract.View mLoginView = null;
    public LoginPresenter( TaskSearchContract.View mLoginView ) {
        this.mLoginView = mLoginView;
        this.mLoginView.setPresenter(this);
    }

    @Override
    public void onLogin(String strUserID, String strPWD) {

        RemoteDataByAppMemberService.AppUserLogon(strUserID, strPWD,  new Subscriber<String>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));}
            @Override
            public void onNext(String s) {
                if (TextUtils.isEmpty(s)){ //登陆失败
                    mLoginView.loginFail("登录失败");
                }

                AppUserInfo appUserInfo = new Gson().fromJson(s,AppUserInfo.class);
                if(appUserInfo.isSucess()){
                    mLoginView.loginSucceeded(appUserInfo);
                }else{
                    mLoginView.loginFail("账号或者密码错误!");

                }
            }
        });
    }


    @Override
    public void onStart() {

    }


}

package com.elegps.gz_customerservice;

import android.text.TextUtils;
import android.util.Log;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.javabean.AppUserInfo;

import com.google.gson.Gson;
import com.soap.RemoteDataByAppMemberService;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/11.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private String TAG = this.getClass().getName();
    private LoginContract.View mLoginView = null;
    public LoginPresenter( LoginContract.View mLoginView ) {
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
                mLoginView.loginFail(ExceptionMsgManager.onExceptionToMsg(e));
            e.printStackTrace();}
            @Override
            public void onNext(String s) {
                if (TextUtils.isEmpty(s)){ //登陆失败
                    mLoginView.loginFail("登录失败");
                }

                Log.e(TAG,s);
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

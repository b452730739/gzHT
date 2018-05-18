package com.elegps.gz_customerservice;


import com.elegps.javabean.AppUserInfo;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loginFail(String message);
        void loginSucceeded(AppUserInfo appUserInfo);


    }
    interface Presenter extends BasePresenter {
        void onLogin(String strUserID, String strPWD);

}}

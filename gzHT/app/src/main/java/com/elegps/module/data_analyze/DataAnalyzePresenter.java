package com.elegps.module.data_analyze;

import android.text.TextUtils;
import android.util.Log;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.help.ParseJsonTools;
import com.elegps.javabean.DataAnalyzeWraper;
import com.soap.RemoteDataByAppMemberService;

import java.util.ArrayList;

import rx.Subscriber;

public class DataAnalyzePresenter implements DataAnalyzeContract.Presenter {

    private DataAnalyzeContract.View view;
    private String TAG = this.getClass().getName();

    public DataAnalyzePresenter(DataAnalyzeContract.View view) {
        this.view = view;
    }

    @Override
    public void AppDataAnalyze() {

        RemoteDataByAppMemberService.AppDataAnalyze(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.Fail(ExceptionMsgManager.onExceptionToMsg(e));
            }

            @Override
            public void onNext(String s) {

                if(TextUtils.isEmpty(s)){
                    view.Fail("未获取到数据!");
                    return;
                }
                Log.e(TAG,s);
                ArrayList<DataAnalyzeWraper> analyzeArrayList = (ArrayList<DataAnalyzeWraper>) ParseJsonTools.fromJsonArray(s,DataAnalyzeWraper.class);
                view.Succeeded(analyzeArrayList);

            }
        });

    }

    @Override
    public void onStart() {

    }
}

package com.elegps.module.task_daiban_search;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.help.ParseJsonTools;
import com.elegps.javabean.TaskDaiBanInfo;
import com.soap.RemoteDataByAppMemberService;

import java.util.ArrayList;

import rx.Subscriber;

public class TaskDaiBanSearchPresenter implements TaskDaibanSearchContract.Presenter {

private String TAG = this.getClass().getName();
    private TaskDaibanSearchContract.View view;

    public TaskDaiBanSearchPresenter(TaskDaibanSearchContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void AppTaskDaiBanList(String strUserID, String strMachineNO, String strStartDate, String strEndDate) {

        RemoteDataByAppMemberService.AppTaskDaiBanList(strUserID, strMachineNO, strStartDate, strEndDate, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.Fail(ExceptionMsgManager.onExceptionToMsg(e));
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {

                Log.e(TAG,s);
                if(TextUtils.isEmpty(s)){
                    view.Fail("");
                    return;
                }

                ArrayList<TaskDaiBanInfo> arrayList = (ArrayList<TaskDaiBanInfo>) ParseJsonTools.fromJsonArray(s,TaskDaiBanInfo.class);
                view.Succeeded(arrayList);

            }
        });
    }

    @Override
    public void onStart() {

    }
}

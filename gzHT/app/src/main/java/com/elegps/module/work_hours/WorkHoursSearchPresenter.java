package com.elegps.module.work_hours;

import android.text.TextUtils;
import android.util.Log;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.help.ParseJsonTools;
import com.elegps.javabean.TaskDaiBanInfo;
import com.elegps.javabean.WorkingHours;
import com.soap.RemoteDataByAppMemberService;

import java.util.ArrayList;

import rx.Subscriber;

public class WorkHoursSearchPresenter implements WorkHoursSearchContract.Presenter {

private String TAG = this.getClass().getName();
    private WorkHoursSearchContract.View view;

    public WorkHoursSearchPresenter(WorkHoursSearchContract.View view) {
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void AppWorkHour(String strUserID,  String strStartDate, String strEndDate) {

        RemoteDataByAppMemberService.AppWorkHour(strUserID,  strStartDate, strEndDate, new Subscriber<String>() {
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

                ArrayList<WorkingHours> arrayList = (ArrayList<WorkingHours>) ParseJsonTools.fromJsonArray(s,WorkingHours.class);
                view.Succeeded(arrayList);

            }
        });
    }

    @Override
    public void onStart() {

    }
}

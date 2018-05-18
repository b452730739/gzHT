package com.elegps.module.task_search;

import android.text.TextUtils;
import android.util.Log;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.help.ParseJsonTools;
import com.elegps.javabean.AppUserInfo;
import com.elegps.javabean.TaskInfoList;
import com.google.gson.Gson;
import com.soap.RemoteDataByAppMemberService;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/11.
 */

public class TaskSearchPresenter implements TaskSearchContract.Presenter{
    private String TAG = this.getClass().getName();
    private TaskSearchContract.View view = null;
    public TaskSearchPresenter(TaskSearchContract.View view ) {
        this.view = view;
        this.view.setPresenter(this);
    }



    @Override
    public void onStart() {

    }


    @Override
    public void searchTask(String strMachineNO, String strMachineModel, String strStartDate, String strEndDate, String strStatus) {

        RemoteDataByAppMemberService.AppTaskSearch(strMachineNO, strMachineModel, strStartDate, strEndDate, strStatus, new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            public void onError(Throwable e) {
                view.Fail(ExceptionMsgManager.onExceptionToMsg(e));
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,s);

                if (TextUtils.isEmpty(s)){
                    view.Fail("获取数据失败!");
                }else{
                   // TaskInfoList     taskInfoList = new Gson().fromJson(s.toString(),TaskInfoList.class);


                    ArrayList<TaskInfoList.TaskInfo> arrayList = (ArrayList<TaskInfoList.TaskInfo>) ParseJsonTools.fromJsonArray(s,TaskInfoList.TaskInfo.class);


                    TaskInfoList     taskInfoList = new TaskInfoList();
                    taskInfoList.setTaskInfoArrayList(arrayList);

                    view.Succeeded(taskInfoList);
                }



            }
        });
    }
}

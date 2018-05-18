package com.elegps.module.task_daiban_search;


import com.elegps.javabean.TaskDaiBanInfo;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

import java.util.ArrayList;

public interface TaskDaibanSearchContract {


    interface View extends BaseView<Presenter>{

        void Fail(String message);
        void Succeeded(ArrayList<TaskDaiBanInfo> arrayList);

    }
    interface Presenter extends BasePresenter{
      void  AppTaskDaiBanList(String strUserID,String strMachineNO,String strStartDate,String strEndDate);
    }
}

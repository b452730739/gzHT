package com.elegps.module.work_hours;


import com.elegps.javabean.TaskDaiBanInfo;
import com.elegps.javabean.WorkingHours;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

import java.util.ArrayList;

public interface WorkHoursSearchContract {


    interface View extends BaseView<Presenter>{

        void Fail(String message);
        void Succeeded(ArrayList<WorkingHours> arrayList);

    }
    interface Presenter extends BasePresenter{
      void  AppWorkHour(String strUserID,  String strStartDate, String strEndDate);
    }
}

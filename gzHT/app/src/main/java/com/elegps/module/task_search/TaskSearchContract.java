package com.elegps.module.task_search;


import com.elegps.javabean.TaskInfoList;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface TaskSearchContract {

    interface View extends BaseView<Presenter> {

        void Fail(String message);
        void Succeeded(TaskInfoList taskInfoList);


    }
    interface Presenter extends BasePresenter {
        void searchTask(String strMachineNO,String strMachineModel,String strStartDate,String strEndDate,String strStatus);

}}

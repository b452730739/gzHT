package com.elegps.module.data_analyze;


import com.elegps.javabean.DataAnalyzeWraper;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

import java.util.ArrayList;

public interface DataAnalyzeContract {


    interface View extends BaseView<Presenter>{

        void Fail(String message);
        void Succeeded(ArrayList<DataAnalyzeWraper> arrayList);

    }
    interface Presenter extends BasePresenter{
      void  AppDataAnalyze();
    }
}

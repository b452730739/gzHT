package com.elegps.module.machine_stock_search;


import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskInfoList;
import com.mvp.BasePresenter;
import com.mvp.BaseView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public interface MachineStockSearchContract {

    interface View extends BaseView<Presenter> {

        void Fail(String message);
        void Succeeded(ArrayList<MachineStockInfo> arrayList);


    }
    interface Presenter extends BasePresenter {
        void searchMachineStock(String strMachineNO, String strMachineModel, String strStartDate, String strEndDate);

}}

package com.elegps.module.machine_stock_search;

import android.text.TextUtils;
import android.util.Log;

import com.elegps.help.ExceptionMsgManager;
import com.elegps.help.ParseJsonTools;
import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskInfoList;
import com.soap.RemoteDataByAppMemberService;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/11.
 */

public class MachineStockSearchPresenter implements MachineStockSearchContract.Presenter{
    private String TAG = this.getClass().getName();
    private MachineStockSearchContract.View view = null;
    public MachineStockSearchPresenter(MachineStockSearchContract.View view ) {
        this.view = view;
        this.view.setPresenter(this);
    }



    @Override
    public void onStart() {

    }



    @Override
    public void searchMachineStock(String strMachineNO, String strMachineModel, String strStartDate, String strEndDate) {
        RemoteDataByAppMemberService.AppMachineStock(strMachineNO, strMachineModel, strStartDate, strEndDate, new Subscriber<String>() {
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


                    ArrayList<MachineStockInfo> arrayList = (ArrayList<MachineStockInfo>) ParseJsonTools.fromJsonArray(s,MachineStockInfo.class);


                    view.Succeeded(arrayList);
                }



            }
        });
    }
}

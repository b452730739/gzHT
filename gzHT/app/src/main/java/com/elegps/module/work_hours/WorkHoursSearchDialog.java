package com.elegps.module.work_hours;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.App;
import com.constant.Constant;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.AppUserInfo;
import com.elegps.javabean.TaskDaiBanInfo;
import com.elegps.javabean.WorkingHours;
import com.view.DateEditTextForData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkHoursSearchDialog extends Dialog implements WorkHoursSearchContract.View{



    @Bind(R.id.startData)
    DateEditTextForData startData;
    @Bind(R.id.endData)
    DateEditTextForData endData;

    @Bind(R.id.search)
    Button search;

    private Context mContext;
    private GetWorkingHoursList infoList;
    private WorkHoursSearchPresenter presenter;
    private Dialog_UI loading ;
    private AppUserInfo userInfo;

    public   boolean IS_MY_HOURS = false;//我的工时


    public WorkHoursSearchDialog(Context context, GetWorkingHoursList infoList, boolean IS_MY_HOURS) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.infoList = infoList;
        this.IS_MY_HOURS = IS_MY_HOURS;
        setContentView(R.layout.dialog_work_hours_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        userInfo = (AppUserInfo) App.ACACHE.getObject(Constant.APPUSERINFO);
        presenter = new WorkHoursSearchPresenter(this);
        loading = new Dialog_UI(mContext,"正在查询...");

    }

    public void searchTask(){
        this.dismiss();
        loading.show();
        if(IS_MY_HOURS){
            presenter.AppWorkHour(userInfo.getUserID(),startData.getText().toString(),endData.getText().toString());

        }else{
            presenter.AppWorkHour("",startData.getText().toString(),endData.getText().toString());

        }
    }

    @OnClick(R.id.search)
    public void onViewClicked() {
        searchTask();
    }

    @Override
    public void Fail(String message) {
        loading.dismiss();
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Succeeded(ArrayList<WorkingHours> arrayList) {
        loading.dismiss();
        infoList.WorkingHoursList(arrayList);
    }


    @Override
    public void setPresenter(WorkHoursSearchContract.Presenter presenter) {

    }

    interface GetWorkingHoursList{
        public void WorkingHoursList(ArrayList<WorkingHours> arrayList);
    }
}

package com.elegps.module.task_daiban_search;


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
import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskDaiBanInfo;
import com.elegps.module.machine_stock_search.MachineStockSearchContract;
import com.elegps.module.machine_stock_search.MachineStockSearchPresenter;
import com.view.DateEditTextForData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDaiBanSearchDialog extends Dialog implements TaskDaibanSearchContract.View{


    @Bind(R.id.et_strMachineNO)
    EditText etStrMachineNO;

    @Bind(R.id.startData)
    DateEditTextForData startData;
    @Bind(R.id.endData)
    DateEditTextForData endData;

    @Bind(R.id.search)
    Button search;

    private Context mContext;
    private GetTaskDaiBanInfoList infoList;
    private TaskDaiBanSearchPresenter presenter;
    private Dialog_UI loading ;
    private AppUserInfo userInfo;

    public TaskDaiBanSearchDialog(Context context, GetTaskDaiBanInfoList infoList) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.infoList = infoList;
        setContentView(R.layout.dialog_task_daiban_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        userInfo = (AppUserInfo) App.ACACHE.getObject(Constant.APPUSERINFO);
        presenter = new TaskDaiBanSearchPresenter(this);
        loading = new Dialog_UI(mContext,"正在查询...");

    }

    public void searchTask(){
        this.dismiss();
        loading.show();
        presenter.AppTaskDaiBanList(userInfo.getUserID(),etStrMachineNO.getText().toString(),startData.getText().toString(),endData.getText().toString());
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
    public void Succeeded(ArrayList<TaskDaiBanInfo> arrayList) {
        loading.dismiss();
        infoList.taskDaiBanList(arrayList);
    }


    @Override
    public void setPresenter(TaskDaibanSearchContract.Presenter presenter) {

    }

    interface GetTaskDaiBanInfoList{
        public void taskDaiBanList(ArrayList<TaskDaiBanInfo> arrayList);
    }
}

package com.elegps.module.task_search;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.elegps.UIManager.Dialog_UI;
import com.elegps.adapter.SpinnerApadterByArray;
import com.elegps.adapter.SpinnerApadterByList;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskInfoList;
import com.view.DateEditTextForData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskSearchDialog extends Dialog implements TaskSearchContract.View,AdapterView.OnItemSelectedListener{


    @Bind(R.id.et_strMachineNO)
    EditText etStrMachineNO;
    @Bind(R.id.et_strMachineModel)
    EditText etStrMachineModel;
    @Bind(R.id.startData)
    DateEditTextForData startData;
    @Bind(R.id.endData)
    DateEditTextForData endData;
    @Bind(R.id.sp_status)
    Spinner spStatus;
    @Bind(R.id.search)
    Button search;

    private Context mContext;
    private GetTaskInfoList getTaskInfoList;
    private TaskSearchPresenter presenter = null;
    private Dialog_UI loading ;
    private final String[][] TASK_STATUS = {{"待生产","生产中","已入库"},{"0","1","2"}};
    private String taskStatus = "";

    public TaskSearchDialog(Context context,GetTaskInfoList getTaskInfoList) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.getTaskInfoList = getTaskInfoList;
        setContentView(R.layout.dialog_task_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        loading = new Dialog_UI(mContext,"正在查询...");
        presenter = new TaskSearchPresenter(this);

        spStatus.setAdapter(new SpinnerApadterByArray(TASK_STATUS[0],mContext));
        spStatus.setOnItemSelectedListener(this);
    }

    public void searchTask(){
        this.dismiss();
        loading.show();
        presenter.searchTask(etStrMachineNO.getText().toString(),etStrMachineModel.getText().toString(),startData.getText().toString(),endData.getText().toString(),taskStatus);

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
    public void Succeeded(TaskInfoList taskInfoList) {
        loading.dismiss();
        getTaskInfoList.taskInfoList(taskInfoList);
    }

    @Override
    public void setPresenter(TaskSearchContract.Presenter presenter) {

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        taskStatus = TASK_STATUS[1][i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    interface GetTaskInfoList{
        public void taskInfoList(TaskInfoList taskInfoList);
    }
}

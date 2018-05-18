package com.elegps.module.task_search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.Constant;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskInfoList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskInfoActivity extends Activity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_MachineNO)
    TextView tvMachineNO;
    @Bind(R.id.tv_TaskID)
    TextView tvTaskID;
    @Bind(R.id.tv_FlowNO)
    TextView tvFlowNO;
    @Bind(R.id.tv_CreateTime)
    TextView tvCreateTime;
    @Bind(R.id.tv_StatusText)
    TextView tvStatusText;
    @Bind(R.id.tv_MachineModel)
    TextView tvMachineModel;
    @Bind(R.id.tv_Remark)
    TextView tvRemark;

    private Bundle bundle;
    private TaskInfoList.TaskInfo taskInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_info);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {

            taskInfo = (TaskInfoList.TaskInfo) bundle.get(Constant.TASKINFO);
            init();

        }
    }

    private void init() {

        tvCreateTime.setText(taskInfo.getCreateTime());
        tvFlowNO.setText(taskInfo.getFlowNO());
        tvMachineNO.setText(taskInfo.getMachineNO());
        tvStatusText.setText(taskInfo.getStatusText());
        tvTaskID.setText(taskInfo.getTaskID());
        tvMachineModel.setText(taskInfo.getMachineModel());
        tvRemark.setText(taskInfo.getRemark());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

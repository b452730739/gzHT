package com.elegps.module.task_daiban_search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.Constant;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskDaiBanInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDaiBanInfoActivity extends Activity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_MachineNO)
    TextView tvMachineNO;
    @Bind(R.id.tv_CreateTime)
    TextView tvCreateTime;
    @Bind(R.id.tv_FlowNO)
    TextView tvFlowNO;
    @Bind(R.id.tv_StatusText)
    TextView tvStatusText;
    @Bind(R.id.tv_CurNodeName)
    TextView tvCurNodeName;


    private Bundle bundle;
    private TaskDaiBanInfo taskInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_daiban_task_info);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {

            taskInfo = (TaskDaiBanInfo) bundle.get(Constant.TASK_DAIBAN_INFO);
            init();

        }
    }

    private void init() {
        tvMachineNO.setText(taskInfo.getMachineNO());
        tvCreateTime.setText(taskInfo.getCreateTime());
        tvFlowNO.setText(taskInfo.getFlowNO());
        tvStatusText.setText(taskInfo.getStatusText());
        tvCurNodeName.setText(taskInfo.getCurNodeName());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

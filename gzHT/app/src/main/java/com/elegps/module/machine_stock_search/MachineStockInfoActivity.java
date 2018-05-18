package com.elegps.module.machine_stock_search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.Constant;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskInfoList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MachineStockInfoActivity extends Activity {


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
    private MachineStockInfo machineStockInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_machine_stock_info);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {

            machineStockInfo = (MachineStockInfo) bundle.get(Constant.MACHINESTOCK);
            init();

        }
    }

    private void init() {

        tvCreateTime.setText(machineStockInfo.getCreateTime());
        tvFlowNO.setText(machineStockInfo.getFlowNO());
        tvMachineNO.setText(machineStockInfo.getMachineNO());
        tvStatusText.setText(machineStockInfo.getStatusText());
        tvTaskID.setText(machineStockInfo.getTaskID());
        tvMachineModel.setText(machineStockInfo.getMachineModel());
        tvRemark.setText(machineStockInfo.getRemark());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}

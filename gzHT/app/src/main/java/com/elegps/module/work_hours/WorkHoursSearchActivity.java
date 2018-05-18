package com.elegps.module.work_hours;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.Constant;
import com.elegps.adapter.TaskDaiBanInfoAdapter;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskDaiBanInfo;
import com.elegps.javabean.WorkingHours;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkHoursSearchActivity extends Activity implements WorkHoursSearchDialog.GetWorkingHoursList {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvHoursStatis)
    TextView tvHoursStatis;

    private ArrayList<TaskDaiBanInfo> arrayList;
    private TaskDaiBanInfoAdapter taskDaiBanInfoAdapter;
    private WorkHoursSearchDialog searchDialog;

    public boolean IS_MY_HOURS = false;//我的工时

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_work_hours_search);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();

        if (bundle != null) {

            IS_MY_HOURS = bundle.getBoolean(Constant.IS_MY_HOURS);
        }

        if (IS_MY_HOURS) {
            tvTitle.setText("我的工时");
        } else {
            tvTitle.setText("工时统计");

        }

        searchDialog = new WorkHoursSearchDialog(this, this, IS_MY_HOURS);
        searchDialog.searchTask();
    }


    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                searchDialog.show();
                break;
        }
    }


    @Override
    public void WorkingHoursList(ArrayList<WorkingHours> arrayList) {

        if(arrayList == null){
            tvHoursStatis.setText("未查询到工时");
            return;
        }
        if(arrayList.size()<=0){
            tvHoursStatis.setText("未查询到工时");
            return;
        }
         String hourStatis = "";
        for (int i = 0; i < arrayList.size(); i++) {

            hourStatis+= (arrayList.get(i).getUserName()+"("+arrayList.get(i).getHour()+")\n");

        }
        tvHoursStatis.setText(hourStatis);

    }
}

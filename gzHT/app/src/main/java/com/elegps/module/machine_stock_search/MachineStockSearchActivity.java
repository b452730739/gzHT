package com.elegps.module.machine_stock_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.constant.Constant;
import com.elegps.adapter.MachineStockAdapter;
import com.elegps.adapter.TaskInfoAdapter;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.MachineStockInfo;
import com.elegps.javabean.TaskInfoList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MachineStockSearchActivity extends Activity implements MachineStockSearchDialog.GetMachineStockInfoList,AdapterView.OnItemClickListener{


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.taskListView)
    ListView taskListView;


    private ArrayList<MachineStockInfo> arrayList;
    private MachineStockSearchDialog taskSearchDialog;

    private MachineStockAdapter machineStockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_machine_stock_search);
        ButterKnife.bind(this);

        taskSearchDialog = new MachineStockSearchDialog(this,this);
        taskSearchDialog.searchTask();
    }


    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                taskSearchDialog.show();
                break;
        }
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this,MachineStockInfoActivity.class);
        intent.putExtra(Constant.MACHINESTOCK,arrayList.get(i));
        startActivity(intent);
    }

    @Override
    public void machineStockList(ArrayList<MachineStockInfo> arrayList) {

        this.arrayList = arrayList;

        machineStockAdapter = new MachineStockAdapter(this,arrayList);
        taskListView.setAdapter(machineStockAdapter);
        taskListView.setOnItemClickListener(this);
    }
}

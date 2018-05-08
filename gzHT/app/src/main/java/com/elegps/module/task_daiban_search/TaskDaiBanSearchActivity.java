package com.elegps.module.task_daiban_search;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.elegps.adapter.TaskDaiBanInfoAdapter;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskDaiBanInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskDaiBanSearchActivity extends Activity implements TaskDaiBanSearchDialog.GetTaskDaiBanInfoList{

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.listview)
    ListView listview;
    private ArrayList<TaskDaiBanInfo> arrayList;
    private TaskDaiBanInfoAdapter taskDaiBanInfoAdapter;
    private TaskDaiBanSearchDialog searchDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_daiban_search);
        ButterKnife.bind(this);

        searchDialog = new TaskDaiBanSearchDialog(this,this);
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
    public void taskDaiBanList(ArrayList<TaskDaiBanInfo> arrayList) {
        this.arrayList = arrayList;
        taskDaiBanInfoAdapter = new TaskDaiBanInfoAdapter(this,arrayList);
        listview.setAdapter(taskDaiBanInfoAdapter);
    }
}

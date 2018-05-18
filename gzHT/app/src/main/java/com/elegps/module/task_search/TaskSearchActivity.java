package com.elegps.module.task_search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.constant.Constant;
import com.elegps.adapter.TaskInfoAdapter;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskInfoList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskSearchActivity extends Activity implements TaskSearchDialog.GetTaskInfoList,AdapterView.OnItemClickListener{


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.taskListView)
    ListView taskListView;


    private TaskSearchDialog taskSearchDialog;
    private TaskInfoAdapter taskInfoAdapter;
    private TaskInfoList taskInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_search);
        ButterKnife.bind(this);

        taskSearchDialog = new TaskSearchDialog(this,this);
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
    public void taskInfoList(TaskInfoList taskInfoList) {

        this.taskInfoList = taskInfoList;

        taskInfoAdapter = new TaskInfoAdapter(this,taskInfoList);
        taskListView.setAdapter(taskInfoAdapter);
        taskListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(this,TaskInfoActivity.class);
        intent.putExtra(Constant.TASKINFO,taskInfoList.getTaskInfoArrayList().get(i));
        startActivity(intent);
    }
}

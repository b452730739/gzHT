package com.elegps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.elegps.gz_customerservice.R;
import com.elegps.javabean.TaskInfoList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskInfoAdapter extends BaseAdapter {

    private TaskInfoList taskInfoList;
    private Context mContext;

    public TaskInfoAdapter(Context mContext,TaskInfoList taskInfoList) {
        this.taskInfoList = taskInfoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return taskInfoList == null ? 0 : taskInfoList.taskInfoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View view1 = LayoutInflater.from(mContext).inflate(R.layout.task_list_item, null);

        ViewHolder holder = new ViewHolder(view1);

        holder.textView1.setText(taskInfoList.taskInfoArrayList.get(i).getMachineNO()+"("+taskInfoList.taskInfoArrayList.get(i).getMachineModel()+")");
        holder.textView2.setText(taskInfoList.taskInfoArrayList.get(i).getStatusText());
        holder.textView4.setText(taskInfoList.taskInfoArrayList.get(i).getCreateTime());

        return view1;
    }

    static class ViewHolder {
        @Bind(R.id.textView1)
        TextView textView1;
        @Bind(R.id.textView2)
        TextView textView2;
        @Bind(R.id.textView3)
        TextView textView3;
        @Bind(R.id.textView4)
        TextView textView4;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.elegps.UIManager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elegps.gz_customerservice.R;
import com.elegps.javabean.Get_Error;

public class Error_ListAdapter extends BaseAdapter {

	private List<Get_Error> get_Errors = null;
	private Context mContext = null;

	public Error_ListAdapter(List<Get_Error> get_Errors, Context mContext) {

		this.get_Errors = get_Errors;
		this.mContext = mContext;
	};
	
	@Override
	public int getCount() {
		try {
			return get_Errors.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LinearLayout ly = (LinearLayout) ((Activity) mContext)
				.getLayoutInflater().inflate(R.layout.error_listitem, null);

		TextView tv1 = (TextView) ly.findViewById(R.id.textView1);
	//	TextView tv2 = (TextView) ly.findViewById(R.id.textView2);
		TextView tv3 = (TextView) ly.findViewById(R.id.textView3);
		TextView tv4 = (TextView) ly.findViewById(R.id.textView4);
	//	tv2.setVisibility(View.GONE);
		tv1.setText(get_Errors.get(position).getContent()+"("+get_Errors.get(position).getDate()+")");
		//tv2.setText("(日期)"+get_Errors.get(position).getDate());
		tv3.setText(get_Errors.get(position).getFlag());
		tv4.setText(/*"(工单号)"+*/get_Errors.get(position).getGDNO());
		
		return ly;
	}

}

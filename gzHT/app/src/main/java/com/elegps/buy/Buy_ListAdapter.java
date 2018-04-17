package com.elegps.buy;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elegps.gz_customerservice.R;
import com.elegps.javabean.Buy_fitingOne;

public class Buy_ListAdapter extends BaseAdapter {

	private List<Buy_fitingOne> buy_fitingOnes = null;
	private Context mContext = null;

	Buy_ListAdapter(List<Buy_fitingOne> buy_fitingOnes, Context mContext) {

		this.buy_fitingOnes = buy_fitingOnes;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		try {
			return buy_fitingOnes.size();
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
				.getLayoutInflater().inflate(R.layout.buy_listitem, null);
		
		TextView tv = (TextView) ly.findViewById(R.id.textView1);
		tv.setText(buy_fitingOnes.get(position).getTypeName());
		return ly;
	}

}

package com.elegps.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CLYJ_Spinner_Adapter extends BaseAdapter {
	private ArrayList<String> str = null;
	private Context context = null;
	public CLYJ_Spinner_Adapter(ArrayList<String> str, Context context ){
		this.str = str;
		this.context = context;
	}
	@Override
	public int getCount() {
		try {
			return str.size();
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
		TextView textView = new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(18);
		textView.setPadding(20, 20, 0, 20);
		textView.setText(str.get(position));

		return textView;
	}

}

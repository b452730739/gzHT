package com.elegps.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerApadterByArray extends BaseAdapter {
	private String[] array = null;
	private Context context = null;
	public SpinnerApadterByArray(String[] array , Context context ){
		this.array = array;
		this.context = context;
	}
	@Override
	public int getCount() {

		return array == null ?0:array.length;
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
		textView.setText(array[position]);

		return textView;
	}

}

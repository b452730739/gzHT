package com.elegps.UIManager;

import com.elegps.gz_customerservice.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Toast_Creat extends Toast {

	private Context context = null;
	private String str = null;
	private int time = 0;

	public Toast_Creat(Context context,String str ,int time) {
		super(context);
		this.context = context;
		this.str = str;
		this.time = time;
	}
	public void show_toast(){
		this.setDuration(time);
		this.setGravity(Gravity.CENTER, 0,0 ); // 设置出现的位置
		
		TextView textView = new TextView(context);
		textView.setTextColor(Color.BLACK);
		textView.setText(str);
		textView.setTextSize(30);
		textView.setBackgroundResource(R.drawable.lingdao_bg);
		
		this.setView(textView);
		this.show();
	}
}

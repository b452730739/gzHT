package com.elegps.UIManager;

import com.elegps.gz_customerservice.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class Dialog_UI extends Dialog {
	private TextView tv = null;
	public Dialog_UI(Context context, String Txt) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.setTitle(null);
		//this.setCancelable(false);// 设置点击屏幕Dialog不消失
		setContentView(R.layout.dialog);
		tv = (TextView)findViewById(R.id.textView1);
		tv.setText(Txt);
	}
	
}

package com.elegps.gz_customerservice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;

public class LianXi_Activity extends Activity {

	private String[] str = {
			"公司名称：海天塑料机械（广州）有限公司",
			"服务电话：4008832668",
			"投诉电话：020-84662499",
			"传真电话：020-84662268",
			"邮箱地址：HT4008832668@163.COM",
			"公司地址：广东省广州市番禺区东环街东升工业区12号",
			"邮政编码：511400",
			//"开户名称：海天塑料机械（广州）有限公司",
			//"开户银行及账号：平安银行广州番禺支行11000866204001",
		/*	"国税纳税人识别号：440181708214418",
			"地税纳税人识别号：440113708214418",
			"营业执照注册号：440126400014294",
			"组织机构代码证号：70821441-8",*/
			}; ListView listView = null;
	private ImageView back = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lianxi);
		PublicWay.activityList.add(this);
		
		init();
	}
	private void init(){
		back = (ImageView)findViewById(R.id.error_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LianXi_Activity.this,
						MainActivity.class);
				startActivity(intent);
				LianXi_Activity.this.finish();
			}
		});
		listView = (ListView)findViewById(R.id.error_list);
		listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = new TextView(LianXi_Activity.this);
				textView.setText(str[position]);
				textView.setTextColor(Color.BLACK);
				textView.setPadding(25, 17, 25, 17);
				textView.setTextSize(15);
				return textView;
			}
			
			@Override
			public long getItemId(int position) {
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				return position;
			}
			
			@Override
			public int getCount() {
				return str.length;
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ������д���ؼ�
			Intent intent = new Intent(LianXi_Activity.this,
					MainActivity.class);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
}

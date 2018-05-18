package com.elegps.notebook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.downfiles.Down_Files_Dialog;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Note_PingTaiBean;
import com.elegps.warranty.BaoXiu_Activity;

public class Note_PingTaiActivity extends Activity {
	
	private ListView listView = null;
	private ImageView back = null;
	private Dialog_UI dialog_UI = null;
	private ArrayList<Note_PingTaiBean> pingTaiBeans = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.note_pingtai);
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					pingTaiBeans = (ArrayList<Note_PingTaiBean>) new PullPersonService().Note_PingTai(
							ContentWeb.getinstance().contetweb(
							IP_Address.LOGSERVICE,
							"GetMessage", 
							new String[]{"strAccount","strAcctType"},
							new String[]{Constant.UserName,Constant.ISINSIDE},dialog_UI));
							
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				dialog_UI.dismiss();
				
				init();

			}
			
		}.execute((Void)null);
	}
	private void init(){
		
		listView = (ListView)findViewById(R.id.listView1);
		listView.setDivider(getResources().getDrawable(R.drawable.note_horizontal));

		back = (ImageView)findViewById(R.id.note_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Note_PingTaiActivity.this, MainActivity.class);
				startActivity(intent);
				Note_PingTaiActivity.this.finish();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					new Down_Files_Dialog(Note_PingTaiActivity.this, pingTaiBeans.get(arg2).getFileInfo()).show();
				} catch (Exception e) {
					Toast.makeText(Note_PingTaiActivity.this, "该公告没有附件", 0).show();
				}
			}
		});
		listView.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
/*
				TextView textView = new TextView(Note_PingTaiActivity.this);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(18);
				textView.setPadding(30, 20, 30, 20);

				textView.setText(
						Html.fromHtml(pingTaiBeans.get(position).getTitle()+" \t\t \t\t \t\t    "+
								"<font color=#E61A6B>"+pingTaiBeans.get(position).getTime()+"</font> "));*/

				
				RelativeLayout relativeLayout = new RelativeLayout(Note_PingTaiActivity.this);
				relativeLayout.setPadding(30, 20, 30, 20);
				TextView time = new TextView(Note_PingTaiActivity.this);
				time.setTextSize(20);
				time.setId(100);
				time.setText(
						Html.fromHtml(
								"<font color=787c85>"+pingTaiBeans.get(position).getTime()+"</font> "));
				RelativeLayout.LayoutParams time_params = new RelativeLayout.LayoutParams(-2, -2);
				time_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				time.setLayoutParams(time_params);
				
				TextView tv = new TextView(Note_PingTaiActivity.this);
				tv.setTextSize(20);
				tv.setTextColor(Color.BLACK);
				tv.setText(
						Html.fromHtml(pingTaiBeans.get(position).getTitle()+" \t\t \t\t"));
				
				RelativeLayout.LayoutParams tv_params = new RelativeLayout.LayoutParams(-2, -2);
				tv_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				tv_params.addRule(RelativeLayout.LEFT_OF,time.getId());
				tv.setLayoutParams(tv_params);
				
				relativeLayout.addView(time);
				relativeLayout.addView(tv);
				
				return relativeLayout;
			
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
				try {
					return pingTaiBeans.size();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			Intent intent = new Intent(Note_PingTaiActivity.this, MainActivity.class);
			startActivity(intent);
			Note_PingTaiActivity.this.finish();
	
			return true;
		}
		
		return false;

	}
	
}

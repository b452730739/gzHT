package com.elegps.Complaints_proposals;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;

public class Complaints_ProposalsAdd_Activity extends Activity implements OnClickListener{
	private Button add_note = null;
	private ImageView back = null;

	private EditText content = null;
	private String temp = "1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.tousu_jianyi_add);
		init();
		}
		private void init(){
			add_note = (Button) findViewById(R.id.addnote);
			back = (ImageView) findViewById(R.id.note_back);
			content = (EditText)findViewById(R.id.note_suggestion);
	
			add_note.setOnClickListener(this);
			back.setOnClickListener(this);
			
		}
		@Override
		public void onClick(View v) {

			final Intent intent = new Intent();

			switch (v.getId()) {

			case R.id.addnote:
				final Dialog_UI dialog_UI = new Dialog_UI(Complaints_ProposalsAdd_Activity.this, "正在添加...");
				dialog_UI.show();
				dialog_UI.setCancelable(false);
				new AsyncTask<Void, Void, String>() {
					@Override
					protected String doInBackground(Void... params) {
					String temp1 = 	ContentWeb.getinstance().contetweb(
								IP_Address.LOGSERVICE,
								"AddPersonInfo", 
								new String[]{
								"strAccount","strContent",
								},
								new String[]{
										Constant.UserName, 
										content.getText().toString(),
										},null);
						return temp1;
					}

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						dialog_UI.dismiss();
						Toast.makeText(Complaints_ProposalsAdd_Activity.this, result, 0).show();
						Intent intent1 = new Intent();
						intent1.setClass(Complaints_ProposalsAdd_Activity.this, Complaints_ProposalsActivity.class);
						startActivity(intent1);
						Complaints_ProposalsAdd_Activity.this.finish();
					}
					
				}.execute((Void)null);
				
				break;
			case R.id.note_back:

				intent.setClass(Complaints_ProposalsAdd_Activity.this, Complaints_ProposalsActivity.class);
				break;
				
			default:
				break;
			}
			try {
				startActivity(intent);
				this.finish();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				// 这里重写返回键
				Intent intent = new Intent(Complaints_ProposalsAdd_Activity.this,
						Complaints_ProposalsActivity.class);
				startActivity(intent);
				this.finish();
				
				return true;
			}
			
			return false;

		}

	
}

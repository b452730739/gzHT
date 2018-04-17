package com.elegps.warranty;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;

public class BaiXiu_AddActivity extends Activity implements OnClickListener{
	private ImageView add_note = null;
	private ImageView back = null;
	private EditText[] editTexts = null;
	private RadioButton radioButton = null;
	private String temp = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.baoxiu_add);
		init();
		}
		private void init(){
			editTexts = new EditText[12];
			editTexts[0] = (EditText)findViewById(R.id.fax);
			editTexts[1] = (EditText)findViewById(R.id.address2);
			editTexts[2] = (EditText)findViewById(R.id.address);
			editTexts[3] = (EditText)findViewById(R.id.gz);
			editTexts[4] = (EditText)findViewById(R.id.et_contactname);
			editTexts[5] = (EditText)findViewById(R.id.et_phone);
			editTexts[6] = (EditText)findViewById(R.id.et_swollen);
			editTexts[7] = (EditText)findViewById(R.id.et_password);
			editTexts[8] = (EditText)findViewById(R.id.et_confirm_password);
			editTexts[9] = (EditText)findViewById(R.id.tel);//电话
			editTexts[10] = (EditText)findViewById(R.id.et_name);//客户名称
			editTexts[11] = (EditText)findViewById(R.id.beizhu);//图片备注
			
			try {
				editTexts[10].setText(Constant.users.get(0).getCustName());
			} catch (Exception e) {
				e.printStackTrace();
			}

			radioButton = (RadioButton)findViewById(R.id.radio0);
			radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						temp = "0";
					}else{
						temp = "1";
					}
				}
			});
			add_note = (ImageView) findViewById(R.id.addnote);
			back = (ImageView) findViewById(R.id.note_back);
			add_note.setOnClickListener(this);
			back.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.addnote:
				final Dialog_UI dialog_UI = new Dialog_UI(BaiXiu_AddActivity.this, "正在添加...");
				dialog_UI.show();
				dialog_UI.setCancelable(false);
				new AsyncTask<Void, Void, String>() {
					@Override
					protected String doInBackground(Void... params) {
					String temp1 = 	ContentWeb.getinstance().contetweb(
								IP_Address.MAINTAINADVISESERVICE,
								"AddRepairsMaintainInfo", 
								new String[]{
								"strAccount","strContent","strMachineNO","strMachineModel",
								"strProductDate","strContact","strMobile","strEmail",
								"strFax","strAddress","strRepairType","strCustName","strRemark",
								"strTel"
								},
								new String[]{
										Constant.UserName, 
										editTexts[0].getText().toString(),
										editTexts[1].getText().toString(),
										editTexts[2].getText().toString(),
										editTexts[3].getText().toString(),
										editTexts[4].getText().toString(),
										editTexts[5].getText().toString(),
										editTexts[6].getText().toString(),
										editTexts[7].getText().toString(),
										editTexts[8].getText().toString(),
										temp,
										editTexts[10].getText().toString(),
										editTexts[11].getText().toString(),
										editTexts[9].getText().toString(),
										},null);
						return temp1;
					}

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						dialog_UI.dismiss();
						Toast.makeText(BaiXiu_AddActivity.this, result, 0).show();
						Intent intent1 = new Intent();
						intent1.setClass(BaiXiu_AddActivity.this, BaoXiu_Activity.class);
						startActivity(intent1);
						BaiXiu_AddActivity.this.finish();
					}
					
				}.execute((Void)null);
				
				break;
			case R.id.note_back:

				intent.setClass(BaiXiu_AddActivity.this, BaoXiu_Activity.class);
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
				Intent intent = new Intent(BaiXiu_AddActivity.this,
						BaoXiu_Activity.class);
				startActivity(intent);
				this.finish();
				return true;
			}
			return false;

		}
		
}

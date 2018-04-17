package com.elegps.gz_customerservice;

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

import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.gz_customerservice.R;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;



public class Change_passwordActivity extends Activity implements OnClickListener{

	private EditText[] enrolment = null;//从EditText获取的值
	private Button enrolmentBtn = null; //确认按钮
	private Button b_back = null; 		//下面的返回按钮
	private ImageView image_back = null;//返回按钮
	private String temp = null;
	private Bundle bundle= null;
	private Dialog_UI dialog_UI = null;
	private String strAccount = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.change_password);
		PublicWay.activityList.add(this);
		bundle = getIntent().getExtras();
		
		if(bundle != null){
			strAccount = bundle.getString("strAccount");
		}
		
		init();
	}
	
	
	private void init(){
		
		enrolment = new EditText[2];

		enrolment[0] = (EditText)findViewById(R.id.password);			//密码
		enrolment[1] = (EditText)findViewById(R.id.password2);			//确认密码

		
		enrolmentBtn = (Button)findViewById(R.id.b_enrolment);			//确认
		b_back = (Button)findViewById(R.id.b_back);  					//返回
		image_back = (ImageView)findViewById(R.id.image_back);
		
		enrolmentBtn.setOnClickListener(this);
		b_back.setOnClickListener(this);
		image_back.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.b_enrolment:
			if(new Hellper().getNetworkIsAvailable(Change_passwordActivity.this)){
			
		if(
			(!enrolment[0].getText().toString().equals("")) &&
			(!enrolment[1].getText().toString().equals("")) &&
			(enrolment[0].getText().toString().equals(enrolment[1].getText().toString()))){
			
		
			dialog_UI = new Dialog_UI(Change_passwordActivity.this, "正在修改...");
			dialog_UI.show();
			
			new AsyncTask<Void, Void, String>() {
				
				@Override
				protected String doInBackground(Void... params) {
					
					try {
						temp = ContentWeb.getinstance().contetweb(
								IP_Address.MEMBERSERVICE,
								"ChangePassword", 
								new String[]{"strAccount","strNewPassword"},
								new String[]{strAccount,enrolment[0].getText().toString()},dialog_UI);
					}  catch (Exception e) {
						e.printStackTrace();
					}
					return temp;
				}
				
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					
					dialog_UI.dismiss();
					if("true".equals(result)){
					Toast.makeText(Change_passwordActivity.this, "修改成功", 0).show();
					Intent intent1 = new Intent(Change_passwordActivity.this,LogingActivity.class);
					startActivity(intent1);
					Change_passwordActivity.this.finish();
					}else{
						Toast.makeText(Change_passwordActivity.this, "修改失败", 0).show();
						
					}
				
				}
				
				
			}.execute((Void)null);

		
		}else{
			Toast.makeText(Change_passwordActivity.this, "您的输入有误,请重新输入...", 0).show();
			
		}}else{
			Toast.makeText(Change_passwordActivity.this, R.string.networkeerror, 0).show();
		}
			
			break;
		case R.id.b_back:
			
			Intent intent = new Intent(Change_passwordActivity.this,Find_PasswordActivity.class);
			startActivity(intent);
			this.finish();
			
			break;
		case R.id.image_back:
			
			Intent intent1 = new Intent(Change_passwordActivity.this,Find_PasswordActivity.class);
			startActivity(intent1);
			this.finish();
			
			break;

		default:
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			
			Intent intent = new Intent(Change_passwordActivity.this, Find_PasswordActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;
		
	}
	
}

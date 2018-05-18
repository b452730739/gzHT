package com.elegps.gz_customerservice;

import java.util.ArrayList;
import java.util.List;

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
import com.elegps.antkingXML.PullBuildXMLService;
import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Find_password;

public class Find_PasswordActivity extends Activity implements OnClickListener {

	private EditText[] find_password = null;// 从EditText获取的值
	private Button find_btn = null; // 注册按钮
	private Button b_back = null; // 下面的返回按钮
	private ImageView image_back = null;
	private List<Find_password> find_passwords = null;
	private Find_password password = null;
	private Dialog_UI dialog_UI = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);
		
		setContentView(R.layout.find_password);
		init();
	}

	private void init() {
		
		find_password = new EditText[5];
		find_password[0] = (EditText) findViewById(R.id.find_name); // 客户名称
		find_password[1] = (EditText) findViewById(R.id.find_contactname);// 联系人
		find_password[2] = (EditText) findViewById(R.id.find_phone);// 手机号码
		find_password[3] = (EditText) findViewById(R.id.find_swollen);// 账户
		
		find_btn = (Button) findViewById(R.id.b_enrolment);// 确认找回
		b_back = (Button) findViewById(R.id.b_back); // 返回
		image_back = (ImageView) findViewById(R.id.image_back);
		
		find_passwords= new ArrayList<Find_password>();
		password = new Find_password();

		find_btn.setOnClickListener(this);
		b_back.setOnClickListener(this);
		image_back.setOnClickListener(this);
		
		

	}
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.b_enrolment:
			dialog_UI = new Dialog_UI(Find_PasswordActivity.this, "正在匹配...");
			if ((!find_password[0].getText().toString().equals(""))
					&& (!find_password[1].getText().toString().equals(""))
					&& (!find_password[2].getText().toString().equals(""))
					&& (!find_password[3].getText().toString().equals(""))) {
				password.setFind_contactname(find_password[1].getText().toString());
				password.setFind_name(find_password[0].getText().toString());
				password.setFind_phone(find_password[2].getText().toString());
				password.setFind_swollen(find_password[3].getText().toString());
				find_passwords.add(password);
				
				dialog_UI.show();

				new AsyncTask<Void, Void, Void>() {
					String temp = null;

					@Override
					protected Void doInBackground(Void... params) {
						try {
							temp = ContentWeb.getinstance().contetweb(
									IP_Address.MEMBERSERVICE,
									"CheckUserInfo", 
									new String[]{"strUserInfo"},
									new String[]{new PullBuildXMLService().getFind_passwordXML(find_passwords).toString()}
									,dialog_UI);
						}catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						dialog_UI.dismiss();

						//Toast.makeText(Find_PasswordActivity.this, temp, 0).show();
						if("true".equals(temp)){
							Intent intent = new Intent(Find_PasswordActivity.this,
									Change_passwordActivity.class);
							intent.putExtra("strAccount", find_password[3].getText().toString());
							startActivity(intent);
							Find_PasswordActivity.this.finish();
						}else{
							Toast.makeText(Find_PasswordActivity.this, temp, 0).show();
						}
					}
					
				}.execute((Void)null);

		/*		Intent intent = new Intent(Find_PasswordActivity.this,
						Change_passwordActivity.class);
				startActivity(intent);
				this.finish();*/
				
			} else {
				Toast.makeText(Find_PasswordActivity.this, "您输入的有误，请重新输入...", 0)
						.show();
				
			}
			
			break;
		case R.id.b_back:
			
			Intent intent = new Intent(Find_PasswordActivity.this,
					LogingActivity.class);
			startActivity(intent);
			this.finish();
			
			break;
		case R.id.image_back:
			
			Intent intent1 = new Intent(Find_PasswordActivity.this,
					LogingActivity.class);
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
			
			Intent intent = new Intent(Find_PasswordActivity.this,
					LogingActivity.class);
			
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;
		
	}
}

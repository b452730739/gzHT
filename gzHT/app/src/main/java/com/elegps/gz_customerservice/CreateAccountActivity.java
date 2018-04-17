package com.elegps.gz_customerservice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Creat_Person;

public class CreateAccountActivity extends Activity implements OnClickListener{

	private EditText[] enrolment = null;//从EditText获取的�?
	private Button enrolmentBtn = null; //注册按钮
	private Button b_back = null; 		//下面的返回按�?
	private ImageView image_back = null;//返回按钮
	
	private Dialog_UI dialog_UI = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.creata_account);
		PublicWay.activityList.add(this);
		
		init();
	}
	
	private void init(){
		
		/**
		 * 判断是否�?��显示协议
		 */
		//if(!if_ShowDialog("xieyi.obj")){
			show_dialog();
		//}
		enrolment = new EditText[18];
		enrolment[0] = (EditText)findViewById(R.id.et_name);      		
		enrolment[1] = (EditText)findViewById(R.id.et_contactname);		
		enrolment[2] = (EditText)findViewById(R.id.et_phone);		
		enrolment[3] = (EditText)findViewById(R.id.et_swollen);		
		enrolment[4] = (EditText)findViewById(R.id.et_password);		
		enrolment[5] = (EditText)findViewById(R.id.et_confirm_password);
		enrolment[6] = (EditText)findViewById(R.id.postbox);		
		enrolment[7] = (EditText)findViewById(R.id.fax);				
		enrolment[8] = (EditText)findViewById(R.id.address);		

		enrolment[9] = (EditText)findViewById(R.id.gz);			
		enrolment[10] = (EditText)findViewById(R.id.gz2);				
		enrolment[11] = (EditText)findViewById(R.id.gz4);			
		
		enrolment[12] = (EditText)findViewById(R.id.gz6);			
		enrolment[13] = (EditText)findViewById(R.id.gz8);				
		enrolment[14] = (EditText)findViewById(R.id.gz10);		
		enrolment[15] = (EditText)findViewById(R.id.gz12);			
		enrolment[16] = (EditText)findViewById(R.id.gz14);			
		enrolment[17] = (EditText)findViewById(R.id.lianxiren);			

		enrolment[3].setKeyListener(new NumberKeyListener() {
			
			@Override
			public int getInputType() {
				 return android.text.InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT;  
			}
			@Override
			protected char[] getAcceptedChars() {
				 char[] numberChars ={
				'1','2','3','4','5','6','7','8','9','0','a',
				'b','c','d','e','f','g','h','i','j','k','l',
				'm','n','o','p','q','r','s','t','u','v','w',
				'x','y','z'};
				return numberChars;
			}
		});
		
		enrolmentBtn = (Button)findViewById(R.id.b_enrolment);			//注册
		b_back = (Button)findViewById(R.id.b_back);  					//返回
		image_back = (ImageView)findViewById(R.id.image_back);
		
		enrolmentBtn.setOnClickListener(this);
		b_back.setOnClickListener(this);
		image_back.setOnClickListener(this);

	}
	private void show_dialog(){
		final Dialog dialog = new Dialog(CreateAccountActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_xieyi);
		dialog.setCancelable(false);
		TextView no = (TextView)dialog.findViewById(R.id.textView2);
		TextView yes = (TextView)dialog.findViewById(R.id.textView3);
		no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CreateAccountActivity.this.finish();
			}
		});
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	public String writeFile(EditText[] et){
		List<Creat_Person> personList = null;
			try {
			
				personList = new ArrayList<Creat_Person>();
				Creat_Person creat_Person = new Creat_Person();
				creat_Person.setEt_name(et[0].getText().toString());
				creat_Person.setEt_address(et[1].getText().toString());
				creat_Person.setEt_zhizhao(et[2].getText().toString());
				creat_Person.setEt_swollen(et[3].getText().toString());
				creat_Person.setEt_password(et[4].getText().toString());
				creat_Person.setEt_confirm_password(et[5].getText().toString());
				creat_Person.setPostbox(et[6].getText().toString());
				creat_Person.setFax(et[7].getText().toString());
				creat_Person.setZijin(et[8].getText().toString());
				creat_Person.setGuoshui(et[9].getText().toString());
				creat_Person.setDishui(et[10].getText().toString());
				creat_Person.setDaima(et[11].getText().toString());
				creat_Person.setSex(et[12].getText().toString());
				creat_Person.setBumen(et[13].getText().toString());
				creat_Person.setBumen_zhiwu(et[14].getText().toString());
				creat_Person.setEt_phone(et[15].getText().toString());
				creat_Person.setEt_tel(et[16].getText().toString());
				creat_Person.setEt_contactname(et[17].getText().toString());

				personList.add(creat_Person);

			}  catch (Exception e) {
				e.printStackTrace();
			}
			try {
				return PullPersonService.writeToXml(personList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

			
	   }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.b_enrolment:
			
			System.out.println(writeFile(enrolment));
			
			if(new Hellper().getNetworkIsAvailable(CreateAccountActivity.this)){
			
		if(	(!enrolment[0].getText().toString().equals("")) &&
			(!enrolment[1].getText().toString().equals("")) &&
			(!enrolment[16].getText().toString().equals("")) &&
			(!enrolment[3].getText().toString().equals("")) &&
			(!enrolment[4].getText().toString().equals("")	&&
			(!enrolment[5].getText().toString().equals("")) &&
			(enrolment[4].getText().toString().equals(enrolment[5].getText().toString()))) &&
			(!enrolment[12].getText().toString().equals(""))){
			
			if(ifAbc(enrolment[3].getText().toString())){
			dialog_UI = new Dialog_UI(CreateAccountActivity.this, "正在注册...");
			dialog_UI.show();
			
			new AsyncTask<Void, Void, String>() {
				
				@Override
				protected String doInBackground(Void... params) {
					
					String temp = null;
					try {
						temp = ContentWeb.getinstance().contetweb(
								IP_Address.MEMBERSERVICE,
								"RegUserInfo", 
								new String[]{"strUserInfo"},
								new String[]{writeFile(enrolment)},dialog_UI);
					}  catch (Exception e) {
						e.printStackTrace();
					}
					return temp;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
				/*	if((enrolment[0].getText().toString()+"账号注册成功,您的账号正在审核�?").
							equals(result)){*/
					dialog_UI.dismiss();
					
					Toast.makeText(CreateAccountActivity.this, result+"", 0).show();
					if((result+"").equals(enrolment[3].getText().toString()+
							"帐号注册成功,您的帐号正在审核")){
						
						
						Intent intent = new Intent(CreateAccountActivity.this,LogingActivity.class);
						startActivity(intent);
						CreateAccountActivity.this.finish();
					}
				
				}
				
			}.execute((Void)null);

		
			}else{
				
		Toast.makeText(CreateAccountActivity.this, "您的帐号输入有误，请重新输入...", 0).show();
			}
		
		}else{
			Toast.makeText(CreateAccountActivity.this, "您的输入有误,请重新输入..", 0).show();

		}}else{
			Toast.makeText(CreateAccountActivity.this, R.string.networkeerror, 0).show();
		}
			
			break;
		case R.id.b_back:
			
			Intent intent = new Intent(CreateAccountActivity.this,LogingActivity.class);
			startActivity(intent);
			this.finish();
			
			break;
		case R.id.image_back:
			
			Intent intent1 = new Intent(CreateAccountActivity.this,LogingActivity.class);
			startActivity(intent1);
			this.finish();
			
			break;

		default:
			break;
		}
	}

	public void saveAccountOrPassword(String str/** 保存的内�?*/
	, String name/** 保存的文件名 */
	) {

		ObjectOutputStream out = null;
		try {
			FileOutputStream os = openFileOutput(name, MODE_PRIVATE);
			out = new ObjectOutputStream(os);
			out.writeObject(str);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private boolean ifAbc(String str){
		String[] txt = {
				"a","b","c","d","e","f","g",
				"h","i","j","k","l","m","n",
				"o","p","q","r","s","t","u",
				"v","w","x","y","z",};
		for(int i = 0;i <txt.length;i++){
			
			if(str.startsWith(txt[i]))
			return true;
		}
		
			return false;	
	}
	private boolean if_ShowDialog(String filename){


		ObjectInputStream in = null;
		String mAccount = null;
		try {
			InputStream is = openFileInput(filename);
			in = new ObjectInputStream(is);
			mAccount = (String) in.readObject();

			if (mAccount != null) {
				if(mAccount.equals("1")){
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;

	
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回�?
			
			Intent intent = new Intent(CreateAccountActivity.this, LogingActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}
	
}

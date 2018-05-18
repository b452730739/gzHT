package com.elegps.gz_customerservice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import cn.elegps.service.StartService;
import rx.Subscriber;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.help.ACache;
import com.elegps.help.BitmapCache;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;
import com.elegps.javabean.AppUserInfo;
import com.elegps.javabean.Update_bean;
import com.elegps.javabean.Video_users;
import com.elegps.update.UpdateAPP;
import com.google.gson.Gson;
import com.soap.RemoteDataByAppMemberService;


public class LogingActivity extends Activity implements OnClickListener,LoginContract.View {
	

	private String TAG = this.getClass().getName();
	private ImageView imageView = null;
	private Button login = null; 			// 登录
	private EditText account = null; 		// 账号
	private EditText password = null; 		// 密码
	private TextView learnPassword = null; 	// 记住密码
	private TextView getPassword = null;	// 找回密码
	private TextView getAccount = null; 	// 账号注册
	private ImageButton jizhupassword = null; // 记住前面的密�?
	private boolean ifLoginSuccess = false; // 是否成功登录
	private boolean ifLearnPassword = true; // 是否保存密码
	public static File PATH = null;

	private Dialog_UI dialog_UI = null;
	private List<Video_users> users = null;
	
	
	public static final String COM_HYCH_MOBILE_USER_CLICK = "com.hych.mobile.userClick";
	private AQuery aq;
	private String Client_version;
	private static final int NOTIFICATION_ID = 0x12;
	private static final int DIALOG_SIMPLE = 2;
	private static final int DOWNLOAD_FAIL = 3;
	private String message;
	private int _progress = 0;
	private NotificationManager manager = null;
	private Notification notif;
	private int fileSize;
	private int downLoadFileSize;
	private String filename;

	private LoginPresenter presenter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题�?

		PublicWay.activityList.add(this);

		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		Constant.screenWidth = dm.widthPixels;
		Constant.screenHeight = dm.heightPixels;


		presenter = new LoginPresenter(this);

			init();
			new UpdateAPP(this).execute();



		
	}

	private void init() {
		
		RelativeLayout ly = null;
		//if(Constant.screenWidth<550){
			
			ly = (RelativeLayout) LogingActivity.this
					.getLayoutInflater().inflate(R.layout.activity_main, null);
		/*	}else{
		
		ly = (RelativeLayout) MainActivity.this
				.getLayoutInflater().inflate(R.layout.activity_main1, null);
		}*/
		login = (Button) ly.findViewById(R.id.login);
		account = (EditText) ly.findViewById(R.id.account);
		password = (EditText) ly.findViewById(R.id.password);
		learnPassword = (TextView) ly.findViewById(R.id.learnPassword);
		jizhupassword = (ImageButton) ly.findViewById(R.id.learnPasswordpicture);
		getPassword = (TextView) ly.findViewById(R.id.getPassword);
		getAccount = (TextView) ly.findViewById(R.id.getAccount);
		imageView = (ImageView)ly.findViewById(R.id.imageView1);
		imageView.setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.logo, this));
		
		dialog_UI = new Dialog_UI(this, "正在登录...");
        dialog_UI.setCancelable(false);
		
		login.setOnClickListener(this);
		learnPassword.setOnClickListener(this);
		getPassword.setOnClickListener(this);
		getAccount.setOnClickListener(this);
		
		setAccountOrPassword(account, "account.obj");// 设置账号
		setAccountOrPassword(password, "password.obj");// 设置密码
		setContentView(ly);
	}
	
	/**
	 * 当�?出activity是调用此方法保存账号密码
	 */
	@Override
	protected void onDestroy() {
		
		if (ifLoginSuccess) {
			
			saveAccountOrPassword(account.getText().toString(), "account.obj");
			
			if (ifLearnPassword) {
				
				saveAccountOrPassword(password.getText().toString(),
						"password.obj");
			}
		}
		super.onDestroy();

	}
	

	private void setAccountOrPassword(EditText et/** 账号或�?密码 */
	, String name/** 文件�?*/
	) {

		ObjectInputStream in = null;
		String mAccount = null;
		try {
			InputStream is = openFileInput(name);
			in = new ObjectInputStream(is);
			mAccount = (String) in.readObject();

			if (mAccount != null) {
				et.setText(mAccount);
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

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login:
			if(new Hellper().getNetworkIsAvailable(LogingActivity.this)){
			dialog_UI.show();
			new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					 String result = "";

					Constant.UserName = account.getText().toString();
					Constant.TableName = account.getText().toString();
					try {
						result = ContentWeb.getinstance().contetweb(
								IP_Address.MEMBERSERVICE,
								"UserLogin",
								new String[] { "strAccount", "strPassword", },
								new String[] { account.getText().toString(),
								password.getText().toString() },dialog_UI);
					}  catch (Exception e1) {
						e1.printStackTrace();
					}

					String temp1 = null;
					try {
						temp1 = ContentWeb
								.getinstance()
								.contetweb(
										IP_Address.MEMBERSERVICE,
										"GetUserInfo",
										new String[] { "strAccount" },
										new String[] { Constant.UserName },dialog_UI);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						users = new PullPersonService().pullReadXml(temp1);
						Constant.users = users;
						Constant.ISINSIDE = users.get(0).getAcctType();
						Constant.DENGLUNAME = users.get(0).getContact();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return result;
				}
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);

					result = "true";
					loginSucess(result);

				}
			}.execute((Void) null);
			}else{
				Toast.makeText(LogingActivity.this, R.string.networkeerror, 0).show();
			}
		/*	Intent intent8 = new Intent(MainActivity.this,
					ZhuYeActivity.class);
			startActivity(intent8);
			MainActivity.this.finish();*/
			break;
		case R.id.learnPassword:

			if (!ifLearnPassword) {
				// Toast.makeText(MainActivity.this, "记住密码", 0).show();
				ifLearnPassword = true;
				jizhupassword.setBackgroundResource(R.drawable.learnpassword);
			} else {
				// Toast.makeText(MainActivity.this, "忘记密码", 0).show();
				ifLearnPassword = false;
				jizhupassword.setBackgroundResource(R.drawable.nolearnpassword);
				if (new File("/data/data/com.elegps.GZ_CustomerService/files/password.obj")
						.isFile()) {
						// 判断是否是文�?
					new File("/data/data/com.elegps.GZ_CustomerService/files/password.obj")
						.delete();
						// delete()方法 你应该知�?是删除的意�?;
				}else{
						
					}
			}
			break;
		case R.id.getPassword:

			Intent intent1 = new Intent(LogingActivity.this,
					Find_PasswordActivity.class);
			startActivity(intent1);
			this.finish();
			
			break;
		case R.id.getAccount:

			Intent intent = new Intent(LogingActivity.this,
					CreateAccountActivity.class);
			startActivity(intent);
			this.finish();

			break;

		default:
			break;
		}
	}

	private void loginSucess(String result){
		try {
			if (result.equals("true")) {

				presenter.onLogin(account.getText().toString(),password.getText().toString());

			} else {
				//loading.setVisibility(View.INVISIBLE);
				dialog_UI.dismiss();
				Toast.makeText(LogingActivity.this, result, 0).show();
			}
		} catch (Exception e) {
			dialog_UI.dismiss();
			Toast.makeText(LogingActivity.this, "登录失败", 0).show();

			e.printStackTrace();
		}
	}
	public void saveAccountInfo(String str/** 保存的内�?*/
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
	@Override
	protected void onResume() {
		/*
		 * configEntity = ConfigService.LoadConfig(this);
		 * anychat.SetBaseEvent(this);
		 */
		super.onResume();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回�?
			// 遍历Activity集合，关闭所有集合内的Activity
			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			return true;
		}

		return false;

	}


	@Override
	public void loginFail(String message) {
		dialog_UI.dismiss();

		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void loginSucceeded(AppUserInfo appUserInfo) {


		ACache.putObject(Constant.APPUSERINFO,appUserInfo);

		PATH = new File(Environment.getExternalStorageDirectory()
				.getPath()+"/haitian/"+Constant.UserName+"/");
		if(!(PATH.isFile())){
			PATH.mkdirs();
		}
		Constant.User_Path = PATH;
		Constant.jietuPath = PATH+"/map.png";

		Constant.photoPath = PATH+"";
		saveAccountInfo(Constant.UserName+"$"+Constant.DENGLUNAME, "config");

		ifLoginSuccess = true;
		Intent intent = new Intent(LogingActivity.this,
				MainActivity.class);
		startActivity(intent);
		LogingActivity.this.finish();
	}

	@Override
	public void setPresenter(LoginContract.Presenter presenter) {

	}
}

package com.elegps.photo;

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
import com.elegps.UIManager.Toast_Creat;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;


public class Photo_NativeActivity extends Activity implements OnClickListener{
	
	private ImageView setphoto = null;	//图片显示
	private EditText[] setValues = null;//从EditText获取到的数值
	//private TextView setTime = null;  //显示时间
	private Button upload = null;    	//上传
	private Bundle bundle = null;
	private String path = null;			//图片的绝对地址
	
	private String ImageTemp = null; 	//图片转为String的临时变量
	private Dialog_UI dialog_UI = null;
	
	private byte[] b  = null;
	private Hellper hellper = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.photo_upload);
		PublicWay.activityList.add(this);
		
		bundle = getIntent().getExtras();
		hellper = new Hellper();
		if(bundle != null){
			
			path = bundle.getString("setPATH");
		}
		b = hellper.readInputStream1(path);
		init();
		
	}
	
	private void init(){

		setValues = new EditText[3];
		
		setValues[0] = (EditText)findViewById(R.id.photo_number); 
		setValues[1] = (EditText)findViewById(R.id.photo_describe);
		setValues[2] = (EditText)findViewById(R.id.photo_people);
		
		setphoto = (ImageView)findViewById(R.id.photo_picture);
		upload = (Button)findViewById(R.id.photo_upload);
		try {	
		setphoto.setImageBitmap(hellper.readInputStream(path));
	} catch (Exception e) {
		e.printStackTrace();
	} catch (OutOfMemoryError e) {
		e.printStackTrace();
		Toast.makeText(Photo_NativeActivity.this, "图片过大", 0).show();
	}
	
		
		setphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Photo_NativeActivity.this, Photo_LookActivity.class);
				intent.putExtra("PhotoPath", path);
				startActivity(intent);
			}
		});
		
		try {
			ImageTemp = null;
			new AsyncTask<Void, Void, Void>(){

				@Override
				protected Void doInBackground(Void... params) {
					try {
						ImageTemp =  hellper.bitmaptoString(hellper.getImage(b),
								Photo_NativeActivity.this);
					} catch (Exception e) {
						e.printStackTrace();
					} catch (OutOfMemoryError e) {
						e.printStackTrace();

					}
					return null;
				}
				
			}.execute((Void)null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dialog_UI = new Dialog_UI(Photo_NativeActivity.this, "正在上传...");
		dialog_UI.setCancelable(false);
		upload.setOnClickListener(this);
	}
	private void setPicture(ImageView imageView){    		
		
		if(path.endsWith("jpg")||path.endsWith("png")){
		try {
			
			/*Bitmap bitmap = hellper.readInputStream(path);

			imageView.setImageBitmap(bitmap);*/
			//imageView.setImageBitmap(hellper.getImage(b));
			imageView.setImageBitmap(hellper.readInputStream(path));

		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText(Photo_NativeActivity.this, "图片过大", 0).show();
		}
	}}

	@Override
	public void onClick(View v) {
		if(new Hellper().getNetworkIsAvailable(Photo_NativeActivity.this)){
		/*if(
		(!setValues[1].getText().toString().equals("")) &&
		(!setValues[2].getText().toString().equals("")) &&
		(ImageTemp != null)){*/
			dialog_UI.show();
			new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					String temp = null;

					try {
						
						
						temp = ContentWeb.getinstance().contetweb(
								IP_Address.PICTURESERVICE,
								"UploadPicture", 
								new String[]{
										"strGDNO","strAccount","strRemark","strFileName",
										"strfileExtension","strPicture"},
								new String[]{
										setValues[0].getText().toString(),
										Constant.UserName,
										setValues[1].getText().toString(),
										setValues[2].getText().toString(),
										".png",
										ImageTemp},dialog_UI);
					} catch (Exception e) {
						e.printStackTrace();
					} catch (OutOfMemoryError e) {
						e.printStackTrace();

					}
					return temp;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					dialog_UI.dismiss();
					new Toast_Creat(Photo_NativeActivity.this, result+"", 4000).show_toast();

					//Toast.makeText(Photo_NativeActivity.this, result, 0).show();
					Intent intent = new Intent(Photo_NativeActivity.this, MainActivity.class);
					startActivity(intent);
					Photo_NativeActivity.this.finish();
				}
				
			}.execute((Void)null);
			
	/*	}else{

			Toast.makeText(this, "图片或带*号为必填项目,请重新输入...", 0).show();

	}*/}else{
		Toast.makeText(Photo_NativeActivity.this, R.string.networkeerror, 0).show();
	}
		}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			
			Intent intent = new Intent(Photo_NativeActivity.this, MainActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}
}

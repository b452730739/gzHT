package com.elegps.buy;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;

public class Buy_PeiJianActivity extends Activity implements OnClickListener{
	
	private ImageView back = null;
	private Button buy = null;
	private ImageView imageView = null;
	private Button bendi = null;
	private Button zhaoxiang = null;
	
	private byte[] bb  = null;
	private boolean b = false;// 如果是true就是本地上传，否则就是拍照上传
	private Hellper hellper = null;
	private File file = null;
	private Bitmap photo = null;
	private Bundle bundle = null;
	private String path = null;			//图片的绝对地址
	private String ImageTemp = null;
	private Dialog_UI dialog_UI = null;
	private EditText[] editTexts = null;
	private String [] strs = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);
		
		setContentView(R.layout.buy_peijian);
		
		bundle = getIntent().getExtras();
		if(bundle != null){
			path = bundle.getString("setPATH");
		}
		init();
	}
	
	private void init(){
		
		editTexts = new EditText[13];
		hellper = new Hellper();
		
		back = (ImageView)findViewById(R.id.note_back);
		buy = (Button)findViewById(R.id.imageView2);
		bendi = (Button)findViewById(R.id.button1);
		zhaoxiang = (Button)findViewById(R.id.button2);
		imageView = (ImageView)findViewById(R.id.imageView1);
			
		editTexts[0] = (EditText)findViewById(R.id.et_name);
		editTexts[1] = (EditText)findViewById(R.id.et_contactname);

		editTexts[2] = (EditText)findViewById(R.id.postbox2);
		editTexts[3] = (EditText)findViewById(R.id.et_swollen);
		editTexts[4] = (EditText)findViewById(R.id.et_password);
		editTexts[5] = (EditText)findViewById(R.id.et_confirm_password);
		editTexts[6] = (EditText)findViewById(R.id.et_phone);
		editTexts[7] = (EditText)findViewById(R.id.fax);
		editTexts[8] = (EditText)findViewById(R.id.address);
		editTexts[9] = (EditText)findViewById(R.id.gz);
		editTexts[10] = (EditText)findViewById(R.id.gz2);
		editTexts[11] = (EditText)findViewById(R.id.gz4);
		editTexts[12] = (EditText)findViewById(R.id.postbox);




		
		editTexts[10].setText(Constant.users.get(0).getCustName());
		editTexts[1].setText(Constant.users.get(0).getContact());
		
		editTexts[6].setText(Constant.users.get(0).getMobile());
		editTexts[3].setText(Constant.users.get(0).getEmail());
		editTexts[4].setText(Constant.users.get(0).getFax());
		editTexts[5].setText(Constant.users.get(0).getCustAddr());
		
		try {
			strs = Constant.Buy_PeiJian.split("\\$");
			editTexts[0].setText(strs[0]);     //配件名称
			editTexts[1].setText(strs[1]);		//机台编号
			editTexts[2].setText(strs[2]);		//联系人
			editTexts[3].setText(strs[3]);		//手机
			editTexts[4].setText(strs[4]);		//邮箱
			editTexts[5].setText(strs[5]);		//传真
			editTexts[6].setText(strs[6]);		//客户地址
			editTexts[7].setText(strs[7]);		//收货人
			editTexts[8].setText(strs[8]);		//收货人联系方式
			editTexts[9].setText(strs[9]);		//收货地址
			editTexts[10].setText(strs[10]);	//客户名称	
			editTexts[11].setText(strs[11]);	//备注 
			editTexts[12].setText(strs[12]);	//配件描述
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			imageView.setImageBitmap(hellper.readInputStream(path));
			imageView.setVisibility(View.VISIBLE);

		} catch (Exception e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			Toast.makeText(Buy_PeiJianActivity.this, "图片过大", 0).show();
		}
		try {
			ImageTemp = null;
			new AsyncTask<Void, Void, Void>(){

				@Override
				protected Void doInBackground(Void... params) {
					try {
						bb = hellper.readInputStream1(path);
						ImageTemp =  hellper.bitmaptoString(hellper.getImage(bb),
								Buy_PeiJianActivity.this);
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
		
		back.setOnClickListener(this);
		buy.setOnClickListener(this);
		bendi.setOnClickListener(this);
		zhaoxiang.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.note_back:
			Constant.Buy_PeiJian = null;
			
			Intent intent = new Intent(Buy_PeiJianActivity.this,
					Buy_LishiActivity.class);

			startActivity(intent);
			this.finish();
			break;
		case R.id.imageView2:
			dialog_UI = new Dialog_UI(this, "正在提交...");
			dialog_UI.show();
			dialog_UI.setCancelable(false);// 设置点击屏幕Dialog不消失
			
			if(editTexts[12].getText().toString() == null|editTexts[12].getText().toString().equals("")){
				Toast.makeText(Buy_PeiJianActivity.this, "配件描述不能为空", 0).show();
				dialog_UI.dismiss();
				break;
			}
			
			new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					
					String result = null;
					String file_name = null;
					String end = "";
					try {
						file_name = path.substring(path.lastIndexOf("/")+1, path.length());
						 end=file_name.substring(file_name.lastIndexOf(".") + 1, file_name.length()).toLowerCase();
					} catch (Exception e1) {
					}
					try {
						result = ContentWeb.getinstance().contetweb(
						IP_Address.PRODUCTSERVICE,
						"AddProductBuyInfo", 
						new String[]{
								"strAccount","strCustName","strMachineNO",
								"strEmail","strFax","strCustAddr",
								"strMobile","strBuyInfo","strFileName",
								"strfileExtension","strPicture","strRemark",
								"strReceiver","strReceiverContact","strReceiverAddr",
								"strContact","strMateName","strMateDesc"},
						new String[]{
	Constant.UserName,editTexts[10].getText().toString(),editTexts[2].getText().toString(),
	editTexts[3].getText().toString(),editTexts[4].getText().toString(),editTexts[5].getText().toString(),
	editTexts[6].getText().toString(),editTexts[12].getText().toString(),/*文件名*/file_name,
	/*文件扩展名*/"."+end,/*Base64文件*/ImageTemp,/*备注*/editTexts[11].getText().toString(),
	editTexts[7].getText().toString(),editTexts[8].getText().toString(),editTexts[9].getText().toString(),
	editTexts[1].getText().toString(),editTexts[0].getText().toString(),editTexts[12].getText().toString()
			},dialog_UI);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return result;
				}
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					dialog_UI.dismiss();
					Toast.makeText(Buy_PeiJianActivity.this, result, 0).show();
					Constant.Buy_PeiJian = null;
					
					Intent intent = new Intent(Buy_PeiJianActivity.this, Buy_LishiActivity.class);
					startActivity(intent);
					Buy_PeiJianActivity.this.finish();
				}
				
			}.execute((Void)null);	
			break;		
		case R.id.button1:
			Constant.Buy_PeiJian = 
			editTexts[0].getText().toString()+"$"+
			editTexts[1].getText().toString()+"$"+
			editTexts[2].getText().toString()+"$"+
			editTexts[3].getText().toString()+"$"+
			editTexts[4].getText().toString()+"$"+
			editTexts[5].getText().toString()+"$"+
			editTexts[6].getText().toString()+"$"+
			editTexts[7].getText().toString()+"$"+
			editTexts[8].getText().toString()+"$"+
			editTexts[9].getText().toString()+"$"+
			editTexts[10].getText().toString()+"$"+
			editTexts[11].getText().toString()
			+"$"+editTexts[12].getText().toString()
			;
			
			b = true;
			// 打开图库选择图片
			Intent intent1 = new Intent();
			intent1.setType("image/*");
			intent1.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent1, 1);
				break;
				
		case R.id.button2:
			Constant.Buy_PeiJian = 
					editTexts[0].getText().toString()+"$"+
					editTexts[1].getText().toString()+"$"+
					editTexts[2].getText().toString()+"$"+
					editTexts[3].getText().toString()+"$"+
					editTexts[4].getText().toString()+"$"+
					editTexts[5].getText().toString()+"$"+
					editTexts[6].getText().toString()+"$"+
					editTexts[7].getText().toString()+"$"+
					editTexts[8].getText().toString()+"$"+
					editTexts[9].getText().toString()+"$"+
					editTexts[10].getText().toString()+"$"+
					editTexts[11].getText().toString()+"$"+
					editTexts[12].getText().toString();
			photograph();
			b = false;
			break;
		default:
			break;
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	
		String path = null;

		try {
			if (b) {
				if (resultCode == RESULT_OK) {
					Uri uri = data.getData();
					String[] pojo = { MediaStore.Images.Media.DATA };
					@SuppressWarnings("deprecation")
					Cursor cursor = managedQuery(uri, pojo, null, null, null);
					if (cursor != null) {
						int coulunm_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						path = cursor.getString(coulunm_index);
					}

				}
			} else {
				if (requestCode == 1 && resultCode == RESULT_OK) {
					if (file != null && file.exists()) {

						path = file.getPath();
					}
				}
			}
			if (path != null) {

				Intent intent = new Intent(Buy_PeiJianActivity.this,
						Buy_PeiJianActivity.class);
				
				intent.putExtra("setPATH", path);
					imageView.setVisibility(View.VISIBLE);
				startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拍照上传
	 */
	private void photograph() {

		destoryImage();
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			file = new File(Constant.photoPath, Constant.photoName);
			file.delete();
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(Buy_PeiJianActivity.this, "文件不存在",
							Toast.LENGTH_LONG).show();
					return;
				}
			}
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, 1);
		} else {
			Toast.makeText(Buy_PeiJianActivity.this, "sdcard不存在", Toast.LENGTH_SHORT)
					.show();
		}

	}
	private void destoryImage() {
		if (photo != null) {
			photo.recycle();
			photo = null;
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			Constant.Buy_PeiJian = null;
			Intent intent = new Intent(Buy_PeiJianActivity.this,
					Buy_LishiActivity.class);

			startActivity(intent);
			this.finish();

			return true;
		}

		return false;

	}
}

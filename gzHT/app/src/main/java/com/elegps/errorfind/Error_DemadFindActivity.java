package com.elegps.errorfind;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Error_ListAdapter;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Get_Error;

public class Error_DemadFindActivity extends Activity implements
		OnClickListener {

	private ImageView Image_back = null;;
	private ImageView error_find = null;;
	private ListView listView = null;
	private List<Get_Error> get_Errors = null;
	private PullPersonService personService = null;
	private Dialog_UI dialog_UI = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.error_demand);
		PublicWay.activityList.add(this);
		
		if(new Hellper().getNetworkIsAvailable(Error_DemadFindActivity.this)){
		personService = new PullPersonService();
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				if(Constant.errors == null){
				try {
					get_Errors = personService.Get_Errors(ContentWeb.getinstance().contetweb(
							IP_Address.WORKORDERSERVICE,
							"GetNewWorkOrderInfo", 
							new String[]{"strAccount","strAcctType",},
							new String[]{Constant.UserName,Constant.ISINSIDE},dialog_UI));
					
					Constant.errors = get_Errors;
				} catch (Exception e) {
					e.printStackTrace();
				}
				}else{

					get_Errors = Constant.errors;
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				dialog_UI.dismiss();
				try {
					init();
				} catch (Exception e) {
				}

			}
			
		}.execute((Void)null);

		}else{
			Toast.makeText(Error_DemadFindActivity.this, R.string.networkeerror, 0).show();
		}
	}
	
	private void init() {
		
		Image_back = (ImageView) findViewById(R.id.error_back);
		error_find = (ImageView) findViewById(R.id.error_find);
		listView = (ListView) findViewById(R.id.error_list);

		Image_back.setOnClickListener(this);
		error_find.setOnClickListener(this);
		try {
			listView.setAdapter(new Error_ListAdapter(get_Errors, Error_DemadFindActivity.this));
		} catch (Exception e) {
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Constant.b = true;
				Intent intent = new Intent(Error_DemadFindActivity.this, Error_information.class);
				intent.putExtra("GDNO", get_Errors.get(arg2).getGDNO());
				startActivity(intent);
				Error_DemadFindActivity.this.finish();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.error_back:
			Intent intent = new Intent();

			intent.setClass(Error_DemadFindActivity.this, MainActivity.class);
			startActivity(intent);
			this.finish();

			break;
		case R.id.error_find:
			
			Intent intent2 = new Intent();
			intent2.setClass(Error_DemadFindActivity.this,
					Error_findActivity.class);
			startActivity(intent2);
			this.finish();
			break;

		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			Constant.errors = null;
			Intent intent = new Intent(Error_DemadFindActivity.this,
					MainActivity.class);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
}

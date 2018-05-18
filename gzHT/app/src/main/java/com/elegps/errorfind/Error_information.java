package com.elegps.errorfind;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.Error_info;
import com.elegps.javabean.Get_ErrorXiangXI;

public class Error_information extends Activity implements OnClickListener{
	private EditText[] editTexts = null;
	private ImageView imageView = null;
	private Button manyi = null;
	private Button bumanyi = null;
	
	private ArrayList<Get_ErrorXiangXI> get_Error = null;
	private ArrayList<Error_info> error_infos = null;
	private Dialog_UI dialog_UI = null;
	private Bundle bundle = null;
	private String GDNO = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.error_information);
		
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		
		bundle = getIntent().getExtras();
		
		if(bundle != null){
			GDNO = bundle.getString("GDNO");
		}
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				
		/*		try {
					error_infos = (ArrayList<Error_info>) new PullPersonService().pullReadXml11(
							ContentWeb.getinstance().contetweb(
							IP_Address.WORKORDERSERVICE,
							"GetSendGoodsInfo", 
							new String[]{"strGDNO"},
							new String[]{GDNO},dialog_UI));
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
				try {
					get_Error = (ArrayList<Get_ErrorXiangXI>) new PullPersonService().pullReadXml1(
							ContentWeb.getinstance().contetweb(
							IP_Address.WORKORDERSERVICE,
							"GetWorkOrderDetailInfo", 
							new String[]{"strAcctType","strGDNO"},
							new String[]{Constant.ISINSIDE,GDNO},dialog_UI));
				/*	System.out.println(
							ContentWeb.getinstance().contetweb(
							IP_Address.WORKORDERSERVICE,
							"GetWorkOrderDetailInfo", 
							new String[]{"strAcctType","strGDNO"},
							new String[]{Constant.ISINSIDE,GDNO},dialog_UI));*/
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
		
		
		editTexts = new EditText[13];
		//工单号
		editTexts[0] = (EditText)findViewById(R.id.et_name);
		//日期
		editTexts[1] = (EditText)findViewById(R.id.et_phone);
		//故障描述
		editTexts[2] = (EditText)findViewById(R.id.et_swollen);
		//处理措施
		editTexts[3] = (EditText)findViewById(R.id.et_password);
		//派工人
		editTexts[4] = (EditText)findViewById(R.id.et_confirm_password);
		//备注
		editTexts[5] = (EditText)findViewById(R.id.postbox);
		//发货单
		editTexts[6] = (EditText)findViewById(R.id.fax);
		//发货人
		editTexts[7] = (EditText)findViewById(R.id.address);
		//发货日期
		editTexts[8] = (EditText)findViewById(R.id.fahuoriqi);
		//联系方式
		editTexts[9] = (EditText)findViewById(R.id.lianxifangshi);

		//派工人联系方式
		editTexts[10] = (EditText)findViewById(R.id.lianxi);
		editTexts[11] = (EditText)findViewById(R.id.et1);
		editTexts[12] = (EditText)findViewById(R.id.et2);

		try {
			editTexts[0].setText(get_Error.get(0).getGDNO());
			editTexts[1].setText(get_Error.get(0).getDate());
			editTexts[2].setText(get_Error.get(0).getContent());
			editTexts[3].setText(get_Error.get(0).getOperate());
			editTexts[4].setText(get_Error.get(0).getPGUserName());
			editTexts[10].setText(get_Error.get(0).getMobile());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
		try {
			editTexts[6].setText(get_Error.get(0).getCustName()+" ");
			editTexts[7].setText(get_Error.get(0).getCustAddr()+" ");
			editTexts[8].setText(get_Error.get(0).getContact()+" ");
			editTexts[9].setText(get_Error.get(0).getCMobile()+" ");
			editTexts[5].setText(get_Error.get(0).getTel()+" ");
			editTexts[11].setText(get_Error.get(0).getFax()+" ");
			editTexts[12].setText(get_Error.get(0).getEmail()+" ");

		} catch (Exception e) {
			e.printStackTrace();
		}

		

		imageView = (ImageView)findViewById(R.id.image_back);
		manyi = (Button)findViewById(R.id.b_enrolment);
		bumanyi = (Button)findViewById(R.id.b_back);
		
		


		try {
			if("1".equals(get_Error.get(0).getFlag())){
				
				manyi.setVisibility(View.VISIBLE);
				bumanyi.setVisibility(View.VISIBLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		imageView.setOnClickListener(this);
		manyi.setOnClickListener(this);
		bumanyi.setOnClickListener(this);
		editTexts[9].setEnabled(true);
		editTexts[9].setKeyListener(null);
		editTexts[9].setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
				Intent intent = new Intent();

				//系统默认的action，用来打开默认的电话界面
				intent.setAction(Intent.ACTION_CALL);

				//需要拨打的号码

				intent.setData(Uri.parse("tel:"+editTexts[9].getText().toString()));
				Error_information.this.startActivity(intent);
				editTexts[9].setFocusable(false);
				
				}
			}
		});
		editTexts[9].setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();

				//系统默认的action，用来打开默认的电话界面
				intent.setAction(Intent.ACTION_CALL);

				//需要拨打的号码

				intent.setData(Uri.parse("tel:"+editTexts[9].getText().toString()));
				Error_information.this.startActivity(intent);
			}
		});
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			if(Constant.b){
			Intent intent = new Intent(Error_information.this,
					Error_DemadFindActivity.class);
			startActivity(intent);
			}else{
				Intent intent = new Intent(Error_information.this,
						Error_findActivity.class);
				startActivity(intent);
			}
			this.finish();

			return true;
		}
		
		return false;

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_back:
			if(Constant.b){
			Intent intent = new Intent(Error_information.this,
					Error_DemadFindActivity.class);
			startActivity(intent);
			}else{
				Intent intent = new Intent(Error_information.this,
						Error_findActivity.class);
				startActivity(intent);
			}
			this.finish();
			break;
		case R.id.b_enrolment:  //满意
			
			new AsyncTask<Void, Void, String>(){

				@Override
				protected String doInBackground(Void... params) {
					
					
					String result = ContentWeb.getinstance().contetweb(
					IP_Address.WORKORDERSERVICE,
					"VisitDeal", 
					new String[]{"strGDNO","strAccount","strVisitFlag"},
					new String[]{GDNO,Constant.UserName,"1"},dialog_UI);
					
					return result;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
				Toast.makeText(Error_information.this, result, 0).show();
				}
				
			
				
			}.execute((Void)null);
			
			break;
		case R.id.b_back:		//不满意
			new AsyncTask<Void, Void, String>(){

				@Override
				protected String doInBackground(Void... params) {
					
					
					String result = ContentWeb.getinstance().contetweb(
					IP_Address.WORKORDERSERVICE,
					"VisitDeal", 
					new String[]{"strGDNO","strAccount","strVisitFlag"},
					new String[]{GDNO,Constant.UserName,"0"},dialog_UI);
					
					return result;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
				Toast.makeText(Error_information.this, result, 0).show();
				}
				
			}.execute((Void)null);
			break;
		default:
			break;
		}
	}
	
}

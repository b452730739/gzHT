package com.elegps.notebook;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Note_PaiGongbean;

public class Note_PaiGong extends Activity {
	
	private EditText[] editTexts = null;
	private Dialog_UI dialog_UI = null;
	private ArrayList<Note_PaiGongbean> paiGongbeans = null;
	private ImageView back = null;
	private Bundle bundle = null;
	private String GDNO = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.note_paigong);
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		
		bundle = getIntent().getExtras();
		
		if(bundle != null){
			
			GDNO = bundle.getString("GDNO");
		}

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				
				
				try {
					paiGongbeans = (ArrayList<Note_PaiGongbean>) new PullPersonService().note_paigong(
							ContentWeb.getinstance().contetweb(
							IP_Address.LOGSERVICE,
							"GetPGDetailInfo", 
							new String[]{"strGDNO"},
							new String[]{GDNO},dialog_UI));
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
		
		editTexts = new EditText[18];
		
		editTexts[0] = (EditText)findViewById(R.id.et_name);
		editTexts[1] = (EditText)findViewById(R.id.et_contactname);
		editTexts[2] = (EditText)findViewById(R.id.et_phone);
		editTexts[3] = (EditText)findViewById(R.id.et_swollen);
		editTexts[4] = (EditText)findViewById(R.id.et_password);
		editTexts[5] = (EditText)findViewById(R.id.et_confirm_password);
		editTexts[6] = (EditText)findViewById(R.id.postbox);
		editTexts[7] = (EditText)findViewById(R.id.fax);
		editTexts[8] = (EditText)findViewById(R.id.address);
		editTexts[9] = (EditText)findViewById(R.id.fahuoriqi);
		editTexts[10] = (EditText)findViewById(R.id.lianxifangshi);
		editTexts[11] = (EditText)findViewById(R.id.fahuoxiangqing);
		editTexts[12] = (EditText)findViewById(R.id.renwuleixing);
		editTexts[13] = (EditText)findViewById(R.id.lianxiren);
		editTexts[14] = (EditText)findViewById(R.id.dianhua);
		editTexts[15] = (EditText)findViewById(R.id.chuanjian);
		editTexts[16] = (EditText)findViewById(R.id.jiexianyuan);
		editTexts[17] = (EditText)findViewById(R.id.chanpin);
		
		back = (ImageView)findViewById(R.id.image_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Note_PaiGong.this,
						Note_paiG.class);
				
				startActivity(intent);
				Note_PaiGong.this.finish();
			}
		});
		try {
			editTexts[0].setText(paiGongbeans.get(0).getGDNO());
			editTexts[1].setText(paiGongbeans.get(0).getCustName());
			editTexts[2].setText(paiGongbeans.get(0).getCustAddr());
			editTexts[3].setText(paiGongbeans.get(0).getMobile());
			editTexts[4].setText(paiGongbeans.get(0).getReceiveUserName());
			editTexts[5].setText(paiGongbeans.get(0).getDeptName());
			editTexts[6].setText(paiGongbeans.get(0).getProductType());
			editTexts[7].setText(paiGongbeans.get(0).getContact());
			editTexts[8].setText(paiGongbeans.get(0).getRemark());
			editTexts[9].setText(paiGongbeans.get(0).getPGUserName());
			editTexts[10].setText(paiGongbeans.get(0).getPGDate());
			editTexts[11].setText(paiGongbeans.get(0).getPGContent());
			editTexts[12].setText(paiGongbeans.get(0).getTaskType());
			editTexts[13].setText(paiGongbeans.get(0).getContact());
			editTexts[14].setText(paiGongbeans.get(0).getTel());
			editTexts[15].setText(paiGongbeans.get(0).getDate());
			editTexts[16].setText(paiGongbeans.get(0).getCreateUser());
			editTexts[17].setText(paiGongbeans.get(0).getProductName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键

			Intent intent = new Intent(Note_PaiGong.this,
					Note_paiG.class);

			startActivity(intent);
			this.finish();

			return true;
		}

		return false;

	}
}

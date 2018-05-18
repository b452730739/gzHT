package com.elegps.warranty;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.GetBuyMessage;

public class BaoXiu_InfoActivity extends Activity {
	
	private Bundle bundle = null;
	private String info[] = null;
	private EditText[] editTexts = null;
	private ImageView back = null;
	private String item = null;
	private String GUID = null;

	private ImageView relpy = null;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baoxiu_info);
		
		bundle = getIntent().getExtras();
		if(bundle != null){
			info = bundle.getString("baoxiu_info").split("\\$\\$");	
			item = bundle.getString("item");
		}

		init();
	}
	private void init(){
		editTexts = new EditText[5];
		editTexts[0] = (EditText) findViewById(R.id.et_name);
		editTexts[1] = (EditText) findViewById(R.id.et_phone);
		editTexts[2] = (EditText) findViewById(R.id.et_swollen);
		editTexts[3] = (EditText) findViewById(R.id.et_password);
		editTexts[4] = (EditText) findViewById(R.id.et_confirm_password);
		relpy = (ImageView)findViewById(R.id.relpy);
		back = (ImageView)findViewById(R.id.image_back);
		try {
			editTexts[0].setText(info[0]);
			editTexts[1].setText(info[1]);
			editTexts[2].setText(info[2]);
			editTexts[3].setText(info[3]);
			editTexts[4].setText(info[4]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GUID = info[5];
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaoXiu_InfoActivity.this,
						BaoXiu_Activity.class);
				intent.putExtra("item", item);
				startActivity(intent);
				BaoXiu_InfoActivity.this.finish();
			}
		});
		relpy.setOnClickListener(new OnClickListener() {
			private List<GetBuyMessage> buyMessages = null;
			Dialog_UI dialog_UI = new Dialog_UI(BaoXiu_InfoActivity.this, "正在获取...");
			
			@Override
			public void onClick( final View v) {
				dialog_UI.show();
				new AsyncTask<Void, Void, List<GetBuyMessage>>() {
					@Override
					protected List<GetBuyMessage> doInBackground(Void... params) {
		
						try {
							buyMessages = 
									new PullPersonService().GetBuyMessage(ContentWeb.getinstance().contetweb(
									IP_Address.MAINTAINADVISESERVICE,
									"GetRepairsMaintainMessage", 
									new String[]{"strGUID"},
									new String[]{
											GUID+""},dialog_UI));
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						return buyMessages;
					}

					@Override
					protected void onPostExecute(List<GetBuyMessage> result) {
						super.onPostExecute(result);
						dialog_UI.dismiss();
						final Dialog builder = new Dialog(BaoXiu_InfoActivity.this);
						builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
						
						builder.setContentView(R.layout.reply_dialog);
						Button reply = (Button)builder.findViewById(R.id.b_enrolment);
						final EditText et = (EditText)builder.findViewById(R.id.editText1);
						reply.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
						
						final Dialog_UI dialog_UI = new Dialog_UI(BaoXiu_InfoActivity.this, "正在回复...");	
						dialog_UI.show();
					new AsyncTask<Void, Void, String>(){

						@Override
						protected String doInBackground(Void... params) {
							String value = "";
							String result = "";
							if(et.getText().toString() == null){
								value = " ";
							}else{
								value = et.getText().toString()+"";
							}
							try {
								result = 	ContentWeb.getinstance().contetweb(
										IP_Address.MAINTAINADVISESERVICE,
										"AddRepairsMaintainReturn", 
										new String[]{"strGUID","strUserName","strContent"},
										new String[]{
												GUID+"",
												Constant.DENGLUNAME," "+value},
												dialog_UI);
								
					
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							return result;
						}

						@Override
						protected void onPostExecute(String result) {
							super.onPostExecute(result);
							Toast.makeText(BaoXiu_InfoActivity.this, " "+result, 0).show();
							dialog_UI.dismiss();
							builder.dismiss();
						}
						
					}.execute((Void)null);
							}
						});
						Button dialog_back = (Button)builder.findViewById(R.id.btnConsult);
						dialog_back.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								builder.dismiss();
							}
						});
						String str = "";
						try {
							for(int i = 0;i<result.size();i++){
								
								str+=
										"<font color=#228ed9>"+result.get(i).getUserName()+"</font> " +
												"\t\t\t<font color=#787c85>"
								+result.get(i).getDate()+
										"</font> "
												+"<p></p>\t\t\t\t"+
												result.get(i).getTitle()+"<p></p>";
							}
						
						} catch (Exception e) {
							e.printStackTrace();
						}
						TextView textView = (TextView)builder.findViewById(R.id.textView2);

				textView.setText(Html.fromHtml(str)
						);
						builder		.show();					}
					
				}.execute((Void)null);
			}});

	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			Intent intent = new Intent(BaoXiu_InfoActivity.this,
					BaoXiu_Activity.class);
			intent.putExtra("item", item);

			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
}

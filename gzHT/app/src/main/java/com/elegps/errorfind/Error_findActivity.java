package com.elegps.errorfind;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Error_ListAdapter;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Get_Error;

public class Error_findActivity extends Activity implements OnClickListener{
	
	private ImageView back = null;
	private EditText[] et = null;
	private TextView [] btn = null;
	
    private final int DATE_BEGIN = 1; 
    private final int DATE_END= 2; 
	private int mYear;
	private int mMonth; 
	private int mDay;     
	
	private PullPersonService personService = null;
	private ListView listView = null;
	private Dialog_UI dialog_UI = null;
	
	private List<Get_Error> get_Errors = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.error_find);
		PublicWay.activityList.add(this);
		
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void init(){
		
		et = new EditText[5];
		btn = new TextView[4];
		
		back = (ImageView)findViewById(R.id.error_back);    //返回按鈕
		et[0] = (EditText)findViewById(R.id.error_num);     //工單查詢
		et[1] = (EditText)findViewById(R.id.error_begin);   //開始日期
		et[2] = (EditText)findViewById(R.id.error_end);     //結束日期
		et[3] = (EditText)findViewById(R.id.error_gongdan); //产品分类
		et[4] = (EditText)findViewById(R.id.kehu); //产品分类

		
		btn[0] = (TextView)findViewById(R.id.error_numfind);//開始工單查詢
		btn[1] = (TextView)findViewById(R.id.button2);  	//開始日期
		btn[2] = (TextView)findViewById(R.id.button3); 	 	//結束日期
		btn[3] = (TextView)findViewById(R.id.button4); 		//開始日期查詢
		
		listView = (ListView)findViewById(R.id.error_list1);
		
		dialog_UI = new Dialog_UI(Error_findActivity.this, "正在查询...");
		
		et[0].setOnClickListener(this);
		back.setOnClickListener(this);
		
		try 
		{
			listView.setAdapter(null);	
			listView.setAdapter(
					new Error_ListAdapter(Constant.errors_find, 
							Error_findActivity.this
					));			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0;i <btn.length; i++){
			
			btn[i].setOnClickListener(this);
		}
		//et[1].setOnClickListener(this);
		//et[2].setOnClickListener(this);
		et[0].setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				removeDialog(DATE_BEGIN);
				removeDialog(DATE_END);				
			}
		});
		et[3].setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				removeDialog(DATE_BEGIN);
				removeDialog(DATE_END);				
			}
		});
		et[1].setOnFocusChangeListener(new OnFocusChangeListener() {
			
					public void onFocusChange(View v, boolean hasFocus) {

						((EditText) v).setInputType(InputType.TYPE_NULL); // 关闭软键盘     
						removeDialog(DATE_BEGIN);
						removeDialog(DATE_END);
						
						if(hasFocus){
							showDialog(DATE_BEGIN);

						}
					
					}
				});
		et[2].setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				
				((EditText) v).setInputType(InputType.TYPE_NULL); // 关闭软键盘  
				removeDialog(DATE_BEGIN);
				removeDialog(DATE_END);
				showDialog(DATE_END);

			
			}
		});
		personService = new PullPersonService();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Constant.b = false;

				Intent intent = new Intent(Error_findActivity.this, Error_information.class);
				intent.putExtra("GDNO", Constant.errors_find.get(arg2).getGDNO());
				startActivity(intent);
				Error_findActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {

		case R.id.error_back:		//返回按鈕
		Intent intent = new Intent(Error_findActivity.this, Error_DemadFindActivity.class);
		startActivity(intent);
		this.finish();
		break;
			
		case R.id.button4:			//開始查詢
			if(new Hellper().getNetworkIsAvailable(Error_findActivity.this)){
			
			dialog_UI.show();
			new AsyncTask<Void, Void, String>() {

				@Override
				protected String doInBackground(Void... params) {
					
					String temp1 = null;
					try {
						temp1 = ContentWeb.getinstance().contetweb(
						IP_Address.WORKORDERSERVICE,
						"GetWorkOrderInfo", 
						new String[]{
								"strAccount","strAcctType","strGDNO",
								"strStartDate","strEndDate","strProductType","strCustomName"},
						new String[]{Constant.UserName,Constant.ISINSIDE,
								et[0].getText().toString(),
								et[1].getText().toString(),
								et[2].getText().toString(),
								/*et[3].getText().toString()*/"",et[4].getText().toString(),},dialog_UI);
					}  catch (Exception e) {
						e.printStackTrace();
					}
					return temp1;
				}
				
				@SuppressWarnings("static-access")
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					
					try {
						get_Errors = personService.Get_Errors(result);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					try {
						Constant.errors_find = get_Errors;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					dialog_UI.dismiss();
					try 
					{
						listView.setAdapter(null);	
						listView.setAdapter(
								new Error_ListAdapter(get_Errors, 
										Error_findActivity.this
								));			
						} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.execute((Void)null);
			}else{
				Toast.makeText(Error_findActivity.this, R.string.networkeerror, 0).show();
			}
			break;

		default:
			break;
		}
	}
	   protected Dialog onCreateDialog(int id) { 
	    	
	    	Calendar  calendar = Calendar.getInstance(); 
	        mYear = calendar.get(Calendar.YEAR);     
			mMonth = calendar.get(Calendar.MONTH);     
			mDay = calendar.get(Calendar.DAY_OF_MONTH);  
			//updateDisplay();
	        Dialog dialog = null; 
	        switch(id) { 
	            case DATE_BEGIN: 
	                DatePickerDialog.OnDateSetListener dateListener =  
	                    new DatePickerDialog.OnDateSetListener() { 
	                        @Override 
	                        public void onDateSet(DatePicker datePicker,int year, 
	                        		int month, int dayOfMonth) {
	                        	mYear=year;
	                        	mMonth = month;
	                        	mDay = dayOfMonth;
	                        	data_begin();

	                        } 
	                    }; 
	                dialog = new DatePickerDialog(this, 
	                		dateListener, 
	                        calendar.get(Calendar.YEAR), 
	                        calendar.get(Calendar.MONTH), 
	                        calendar.get(Calendar.DAY_OF_MONTH)); 
	                break; 
	            case DATE_END: 
	                DatePickerDialog.OnDateSetListener dateListener1 =  
	                    new DatePickerDialog.OnDateSetListener() { 
	                        @Override 
	                        public void onDateSet(DatePicker datePicker,int year, 
	                        		int month, int dayOfMonth) {
	                        	mYear=year;
	                        	mMonth = month;
	                        	mDay = dayOfMonth;
	                        	data_end();
	                        	
	                        } 
	                    }; 
	                dialog = new DatePickerDialog(this, 
	                        dateListener1, 
	                        calendar.get(Calendar.YEAR), 
	                        calendar.get(Calendar.MONTH), 
	                        calendar.get(Calendar.DAY_OF_MONTH)); 
	                break;
	            default: 
	                break; 
	        } 
	        return dialog; 
	    } 
		private void data_begin() {     
			et[1].setText(     
					new StringBuilder().append(mYear).append("-")  
					.append(mMonth+1).append("-")  
					.append(mDay).append(" ") 
					);
		}
		private void data_end() {     
			et[2].setText(     
					new StringBuilder().append(mYear).append("-")  
					.append(mMonth+1).append("-")  
					.append(mDay).append(" ") 
					);
		}
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				// 这里重写返回键

				Intent intent = new Intent(Error_findActivity.this, Error_DemadFindActivity.class);
				startActivity(intent);
				this.finish();
				
			return true;
			}
			
			return false;

		}
}

package com.elegps.notebook;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Toast_Creat;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;
import com.elegps.javabean.GetBuyMessage;
import com.elegps.javabean.Note_paiGBean;

public class Note_paiG extends Activity {
	
	private ListView listView = null;
	private Dialog_UI dialog_UI = null;
	private ArrayList<Note_paiGBean> paiGBeans = null;
	private ImageView back = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.note_paig);
		
		init();

	}
	private void set_Adapter(){
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					paiGBeans = (ArrayList<Note_paiGBean>) new PullPersonService().note_paig((
							ContentWeb.getinstance().contetweb(
							IP_Address.LOGSERVICE,
							"GetPGInfo", 
							new String[]{"strAccount","strUserName"},
							new String[]{Constant.UserName,Constant.DENGLUNAME},dialog_UI)));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				dialog_UI.dismiss();
				list_Adapter.notifyDataSetChanged();
			}
			
		}.execute((Void)null);
	}
	private void init(){
		
		listView = (ListView)findViewById(R.id.listView1);
		back = (ImageView)findViewById(R.id.note_back);
		listView.setDivider(getResources().getDrawable(R.drawable.note_horizontal));

		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Note_paiG.this,
						MainActivity.class);

				startActivity(intent);
				Note_paiG.this.finish();
			}
		});
		list_Adapter = new List_Adapter();
		listView.setAdapter(list_Adapter);

		set_Adapter();
		
	}
	List_Adapter list_Adapter = null;
	class List_Adapter extends BaseAdapter{
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			LinearLayout ly = (LinearLayout) ((Activity) Note_paiG.this)
					.getLayoutInflater().inflate(R.layout.baoxiu_item, null);
			
			TextView time = (TextView)ly.findViewById(R.id.textView2);
			time.setTextSize(20);
			time.setId(100);

			TextView tv =  (TextView)ly.findViewById(R.id.textView1);
			tv.setTextSize(20);
			tv.setTextColor(Color.BLACK);
			tv.setText(Html.fromHtml("<font >"+paiGBeans.get(position).getPGInfos()+"</font> " +
					"\t\t\t<font color=#787c85>("
					+paiGBeans.get(position).getDate()+
			")</font> "));
			
			tv.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					//定义一个粘贴板管理器
					final ClipboardManager clipBoard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					//在Menu事件呼叫的时候运行这一行代码 文字就复制到粘贴板了
					clipBoard.setText(((TextView)v).getText());		
					new Toast_Creat(Note_paiG.this, "复制成功", 2000).show_toast();
					return false;
				}
			});
			
			
			Button button1 = (Button)ly.findViewById(R.id.delete);
			button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {				
					is_delete(position);
				}	
				});	
	        Button button2 = (Button)ly.findViewById(R.id.imageView2);

			try {
				if(paiGBeans.get(position).getIsFinish().equals("1")){
					button2.setText("工 单 已 完 成");

				}else{
					button2.setText("回  复  记  录");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
					button2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							
							try {
						
									HuiFu_Info(position);
							} catch (Exception e) {
								e.printStackTrace();
							}
		
						}	
					});
					return ly;
		
		}
		
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public Object getItem(int position) {
			return position;
		}
		
		@Override
		public int getCount() {
			try {
				return paiGBeans.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
	
	}
	private void HuiFu_Info(final int position
){

		new AsyncTask<Void, Void, List<GetBuyMessage>>() {
			@Override
			protected List<GetBuyMessage> doInBackground(Void... params) {
				 List<GetBuyMessage> buyMessages = null;

				try {
					buyMessages = 
							new PullPersonService().GetBuyMessage(ContentWeb.getinstance().contetweb(
							IP_Address.LOGSERVICE,
							"GetPGMessage", 
							new String[]{"strPGNO"},
							new String[]{
							paiGBeans.get(position).getPGNO()},dialog_UI));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return buyMessages;
			}

			@Override
			protected void onPostExecute(List<GetBuyMessage> result) {
				super.onPostExecute(result);
				dialog_UI.dismiss();
				final Dialog builder = new Dialog(Note_paiG.this);
				builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
				
				builder.setContentView(R.layout.reply_dialog);
				Button reply = (Button)builder.findViewById(R.id.b_enrolment);
				final EditText et = (EditText)builder.findViewById(R.id.editText1);
				reply.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
				
				final Dialog_UI dialog_UI = new Dialog_UI(Note_paiG.this, "正在回复...");	
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
								IP_Address.LOGSERVICE,
								"PGReturn", 
								new String[]{"strGUID","strUserName","strContent","strUserID"},
								new String[]{
										paiGBeans.get(position).getPGNO(),
										Constant.DENGLUNAME," "+value,Constant.UserName},
										dialog_UI);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return result;
				}

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					Toast.makeText(Note_paiG.this, " "+result, 0).show();
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
				builder		.show();
			}
			
		}.execute((Void)null);

	
	
	}
	 /*
     * 确认是否删除
     * */
    public  void is_delete(final int position){
        //提示对话框
        AlertDialog.Builder builder=new Builder(Note_paiG.this);
        builder.setTitle("确定是否删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
        	@Override
            public void onClick(DialogInterface dialog, int which) {
				
				new AsyncTask<Void, Void, String>(){
					
					@Override
					protected String doInBackground(Void... params) {
						String str = "";
						try {
							str = ContentWeb.getinstance().contetweb(
									IP_Address.LOGSERVICE,
									"DelPGInfo", 
									new String[]{
											"strPGNO"
									},
									new String[]{
											paiGBeans.get(position).getPGNO()
									},null);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						return str;
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						new Toast_Creat(Note_paiG.this, result, 3000).show_toast();
						set_Adapter();
					}
					
				}.execute((Void)null);
			}
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
         
            }
        }).show();
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键

			Intent intent = new Intent(Note_paiG.this,
					MainActivity.class);

			startActivity(intent);
			this.finish();

			return true;
		}

		return false;

	}
	
}

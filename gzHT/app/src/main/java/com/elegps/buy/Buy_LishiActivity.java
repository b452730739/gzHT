package com.elegps.buy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.elegps.javabean.Buy_lishiBean;
import com.elegps.javabean.GetBuyMessage;

public class Buy_LishiActivity extends Activity{
	
	private ListView listView = null;
	private Dialog_UI dialog_UI = null;
	private List<Buy_lishiBean> buy_lishiBeans = null;
	private ImageView back = null;
	private ImageView add_buy = null;
	private Grid_Adapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buy_lishi);
		dialog_UI = new Dialog_UI(this, "正在加载...");
		dialog_UI.show();
		init();

	}
	
	private void init(){
		listView = (ListView)findViewById(R.id.error_list);
		back = (ImageView)findViewById(R.id.error_back);
		add_buy = (ImageView)findViewById(R.id.addnote);
		add_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Buy_LishiActivity.this, Buy_PeiJianActivity.class);
				startActivity(intent);
				Buy_LishiActivity.this.finish();	
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Buy_LishiActivity.this,
						MainActivity.class);
				startActivity(intent);
				Buy_LishiActivity.this.finish();
			}
		});
		adapter = new Grid_Adapter();
	
		listView.setAdapter(adapter);
		set_gridAdapter();
	}
	private void set_gridAdapter(){
		new AsyncTask<Void, Void, Void>() {
			
			@SuppressWarnings("static-access")
			@Override
			protected Void doInBackground(Void... params) {
				try {
					buy_lishiBeans = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				try {
					buy_lishiBeans = (ArrayList<Buy_lishiBean>) new PullPersonService().buy_lishi(
							ContentWeb.getinstance().contetweb(
							IP_Address.PRODUCTSERVICE,
							"GetBuyInfo", 
							new String[]{"strAccount"},
							new String[]{Constant.UserName},dialog_UI));
					
					Constant.buy_lishiBeans = buy_lishiBeans;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				adapter.notifyDataSetChanged();
				dialog_UI.dismiss();
			}
		}.execute((Void)null);
	}
	class Grid_Adapter extends BaseAdapter{
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			RelativeLayout ly = (RelativeLayout) (Buy_LishiActivity.this)
					.getLayoutInflater().inflate(R.layout.buy_lishi_item, null);
			TextView OrderDate = (TextView)ly.findViewById(R.id.error_num);
			TextView MateName = (TextView)ly.findViewById(R.id.tv1);
			
			TextView BuyInfo = (TextView)ly.findViewById(R.id.error_gongdan);
			TextView MachineNO = (TextView)ly.findViewById(R.id.error_begin1);
			
			Button delete = (Button)ly.findViewById(R.id.delete);
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					is_delete(position);
				}
			});

			
			Button goumai = (Button)ly.findViewById(R.id.imageView2);
			goumai.setOnClickListener(new OnClickListener() {
				private List<GetBuyMessage> buyMessages = null;
				Dialog_UI	dialog_UI1 = new Dialog_UI(Buy_LishiActivity.this, "正在获取...");
				@Override
				public void onClick( final View v) {
					dialog_UI1.show();
					
					new AsyncTask<Void, Void, List<GetBuyMessage>>() {
						@Override
						protected List<GetBuyMessage> doInBackground(Void... params) {
							try {
								buyMessages = 
										new PullPersonService().GetBuyMessage(ContentWeb.getinstance().contetweb(
										IP_Address.PRODUCTSERVICE,
										"GetBuyMessage", 
										new String[]{"strGUID"},
										new String[]{
												buy_lishiBeans.get(position).getOrderNO()},dialog_UI));
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							return buyMessages;
						}

						@Override
						protected void onPostExecute(List<GetBuyMessage> result) {
							super.onPostExecute(result);
							dialog_UI1.dismiss();
							final Dialog builder = new Dialog(Buy_LishiActivity.this);
							builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
							
							builder.setContentView(R.layout.reply_dialog);
							Button reply = (Button)builder.findViewById(R.id.b_enrolment);
							final EditText et = (EditText)builder.findViewById(R.id.editText1);
							reply.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
							
							final Dialog_UI dialog_UI = new Dialog_UI(Buy_LishiActivity.this, "正在回复...");	
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
											IP_Address.PRODUCTSERVICE,
											"AddBuyReturn", 
											new String[]{"strGUID","strUserName","strContent"},
											new String[]{
													buy_lishiBeans.get(position).getOrderNO()+"",
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
								Toast.makeText(Buy_LishiActivity.this, " "+result, 0).show();
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
				}});
							
			OrderDate.setText(buy_lishiBeans.get(position).getOrderDate());
			BuyInfo.setText(buy_lishiBeans.get(position).getBuyInfo());
			MachineNO.setText(buy_lishiBeans.get(position).getMachineNO());
			MateName.setText(buy_lishiBeans.get(position).getMateName());

			return ly;
		}
		
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		
		@Override
		public Object getItem(int arg0) {
			return arg0;
		}
		
		@Override
		public int getCount() {
			try {
				return buy_lishiBeans.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
	 /*
     * 确认是否删除
     * */
    public  void is_delete(final int position){
        //提示对话框
        AlertDialog.Builder builder=new Builder(Buy_LishiActivity.this);
        builder.setTitle("确定是否删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
        	@Override
            public void onClick(DialogInterface dialog, int which) {

				final Dialog_UI dialog_UI = new Dialog_UI(Buy_LishiActivity.this, "正在删除...");
				dialog_UI.show();
				new AsyncTask<Void, Void, String>(){

					@Override
					protected String doInBackground(Void... params) {
						
					String str = 	ContentWeb.getinstance().contetweb(
								IP_Address.PRODUCTSERVICE,
								"DelBuyProduct", 
								new String[]{"strOrderNO"},
								new String[]{
										buy_lishiBeans.get(position).getOrderNO()},dialog_UI);
						return str;
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						new Toast_Creat(Buy_LishiActivity.this, result, 3000).show_toast();
						dialog_UI.dismiss();
						set_gridAdapter();
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
			
			Intent intent = new Intent(Buy_LishiActivity.this,
					MainActivity.class);	
			startActivity(intent);
			this.finish();

			return true;
		}
		
		return false;
	}
}

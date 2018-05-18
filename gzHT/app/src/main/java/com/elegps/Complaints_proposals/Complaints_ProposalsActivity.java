package com.elegps.Complaints_proposals;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Toast_Creat;
import com.elegps.antkingXML.Complaints_proposals;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;
import com.elegps.javabean.GetBuyMessage;
import com.elegps.javabean.GetComplainAdviseInfo;
import com.elegps.warranty.BaoXiu_Activity;
import com.elegps.warranty.BaoXiu_InfoActivity;

public class Complaints_ProposalsActivity extends Activity implements OnClickListener{
	private ImageView add_note = null;
	private ImageView back = null;
	private ListView list = null;
	private List<GetComplainAdviseInfo> adviseInfos = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);

		setContentView(R.layout.tousu_jianyi);
		init();
	}
	private void init(){
		add_note = (ImageView) findViewById(R.id.addnote);
		list = (ListView) findViewById(R.id.listView1);
		list.setDivider(getResources().getDrawable(R.drawable.note_horizontal));
/*		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {}
		});*/
		back = (ImageView) findViewById(R.id.note_back);
		add_note.setOnClickListener(this);
		back.setOnClickListener(this);
		setList_Adapter();
	}
	private void setList_Adapter(){
		final Dialog_UI dialog_UI = new Dialog_UI(Complaints_ProposalsActivity.this, "正在添加...");
		dialog_UI.show();
		new AsyncTask<Void, Void, List<GetComplainAdviseInfo>>() {
			@Override
			protected List<GetComplainAdviseInfo> doInBackground(Void... params) {
						
				try {
					adviseInfos = null;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					adviseInfos = new Complaints_proposals().getcomplainadviseinfo(ContentWeb.getinstance().contetweb(
							IP_Address.LOGSERVICE,
							"GetPersonInfo", 
							new String[]{
							"strAccount","strUserName"
							},
							new String[]{
									Constant.UserName, 
									Constant.DENGLUNAME, 
							},null));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return adviseInfos;
			}
			@Override
			protected void onPostExecute(List<GetComplainAdviseInfo> result) {
				super.onPostExecute(result);
				dialog_UI.dismiss();
				list.setAdapter(new List_Adapter(result));
			}
			
		}.execute((Void)null);
	}
	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		switch (v.getId()) {

		case R.id.addnote:

			intent.setClass(Complaints_ProposalsActivity.this, Complaints_ProposalsAdd_Activity.class);

			break;
		case R.id.note_back:
			intent.setClass(Complaints_ProposalsActivity.this, MainActivity.class);
			break;
			
		default:
			break;
		}
		startActivity(intent);
		this.finish();
	}
	class List_Adapter extends BaseAdapter{
			private List<GetComplainAdviseInfo> adviseInfos = null;

		public List_Adapter(List<GetComplainAdviseInfo> adviseInfos) {
			this.adviseInfos = adviseInfos;
		}
		@Override
		public int getCount() {
			try {
				return adviseInfos.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			LinearLayout ly = (LinearLayout) ((Activity) Complaints_ProposalsActivity.this)
					.getLayoutInflater().inflate(R.layout.baoxiu_item, null);
			
			TextView time = (TextView)ly.findViewById(R.id.textView2);
			time.setTextSize(20);
			time.setId(100);
			time.setText(
					Html.fromHtml(
							"<font color=787c85>"+adviseInfos.get(position).getDate()+"</font> "));
		
			TextView tv =  (TextView)ly.findViewById(R.id.textView1);
			tv.setTextSize(20);
			tv.setTextColor(Color.BLACK);
			tv.setText(
					Html.fromHtml(adviseInfos.get(position).getContent()+" \t\t \t\t"));
			
			Button button1 = (Button)ly.findViewById(R.id.delete);
			button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					is_delete( position);
				}
			});
			Button button2 = (Button)ly.findViewById(R.id.imageView2);
			button2.setText("回  复  记  录");
			button2.setOnClickListener(new OnClickListener() {
				List<GetBuyMessage> buyMessages = null;

				@Override
				public void onClick(View v) {

					
					final Dialog_UI dialog_UI = new Dialog_UI(Complaints_ProposalsActivity.this, "正在获取...");	
					dialog_UI.show();
					new AsyncTask<Void, Void, List<GetBuyMessage>>() {
						@Override
						protected List<GetBuyMessage> doInBackground(Void... params) {
							
							try {
								buyMessages = 
										new PullPersonService().GetBuyMessage(ContentWeb.getinstance().contetweb(
										IP_Address.LOGSERVICE,
										"GetPersonInfoMessage", 
										new String[]{"strGUID"},
										new String[]{
												adviseInfos.get(position).getUUNO()},dialog_UI));
							} catch (Exception e) {
								e.printStackTrace();
							}
							return buyMessages;
						}
						@Override
						protected void onPostExecute(List<GetBuyMessage> result) {
							super.onPostExecute(result);
							dialog_UI.dismiss();
							final Dialog builder = new Dialog(Complaints_ProposalsActivity.this);
							builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
							
							builder.setContentView(R.layout.reply_dialog);
							Button reply = (Button)builder.findViewById(R.id.b_enrolment);
							final EditText et = (EditText)builder.findViewById(R.id.editText1);
							reply.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
							
							final Dialog_UI dialog_UI = new Dialog_UI(Complaints_ProposalsActivity.this, "正在回复...");	
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
											"AddPersonInfoReturn", 
											new String[]{"strGUID","strUserName","strContent"},
											new String[]{
													adviseInfos.get(position).getUUNO(),
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
								Toast.makeText(Complaints_ProposalsActivity.this, " "+result, 0).show();
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
									
									str+="<font color=#228ed9>"+result.get(i).getUserName()+"</font> " +
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
			});
			
			return ly;
		
		/*	RelativeLayout relativeLayout = new RelativeLayout(Complaints_ProposalsActivity.this);
			relativeLayout.setPadding(30, 20, 30, 20);
			TextView time = new TextView(Complaints_ProposalsActivity.this);
			time.setTextSize(20);
			time.setId(100);
			time.setText(
					Html.fromHtml(
							"<font color=787c85>"+adviseInfos.get(position).getDate()+"</font> "));
			RelativeLayout.LayoutParams time_params = new RelativeLayout.LayoutParams(-2, -2);
			time_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			time.setLayoutParams(time_params);
			
			TextView tv = new TextView(Complaints_ProposalsActivity.this);
			tv.setTextSize(20);
			tv.setTextColor(Color.BLACK);
			tv.setText(
					Html.fromHtml(adviseInfos.get(position).getContent()+" \t\t \t\t"));
			
			RelativeLayout.LayoutParams tv_params = new RelativeLayout.LayoutParams(-2, -2);
			tv_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			tv_params.addRule(RelativeLayout.LEFT_OF,time.getId());
			tv.setLayoutParams(tv_params);
			
			relativeLayout.addView(time);
			relativeLayout.addView(tv);
			
			return relativeLayout;*/
		
		}
		
	}
	 /*
     * 确认是否删除
     * */
    public  void is_delete(final int position){
        //提示对话框
        AlertDialog.Builder builder=new Builder(Complaints_ProposalsActivity.this);
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
									"DelPersonInfo", 
									new String[]{
											"strGUID"
									},
									new String[]{
											adviseInfos.get(position).getUUNO()
									},null);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						return str;
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						new Toast_Creat(Complaints_ProposalsActivity.this, result, 3000).show_toast();
						setList_Adapter( );
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
			Intent intent = new Intent(Complaints_ProposalsActivity.this,
					MainActivity.class);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		return false;

	}
	
}

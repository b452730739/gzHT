package com.elegps.warranty;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Toast_Creat;
import com.elegps.antkingXML.Complaints_proposals;
import com.elegps.buy.Buy_LishiActivity;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;
import com.elegps.javabean.GetComplainAdviseInfo;

public class BaoXiu_Activity extends Activity implements OnClickListener{
	private ImageView add_note = null;
	private ImageView back = null;
	private ListView list = null;
	private List<GetComplainAdviseInfo> adviseInfos = null;
	private GridView gridView = null;
	private Grid_Adapter grid_Adapter = null;
	private String item = "0";
	private Bundle bundle = null;
	private List_Adapter list_Adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PublicWay.activityList.add(this);
		bundle = getIntent().getExtras();
		if(bundle!=null){
			item = bundle.getString("item");
		}else{
			item = "0";
		}
		setContentView(R.layout.baoxiu);
		init();
	}
	private void init(){
		add_note = (ImageView) findViewById(R.id.addnote);
		list = (ListView) findViewById(R.id.listView1);
		back = (ImageView) findViewById(R.id.note_back);
		list.setDivider(getResources().getDrawable(R.drawable.note_horizontal));
/*		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(BaoXiu_Activity.this, BaoXiu_InfoActivity.class);
				intent.putExtra("baoxiu_info", 
						adviseInfos.get(arg2).getContent()+"$$"+
						adviseInfos.get(arg2).getDate()+"$$"+
						adviseInfos.get(arg2).getMachineNO()+"$$"+
						adviseInfos.get(arg2).getMachineModel()+"$$"+
						adviseInfos.get(arg2).getProductDate()+"$$"+
						adviseInfos.get(arg2).getGUID());
				intent.putExtra("item", item);
				startActivity(intent);
			}
		});*/
		gridView = (GridView)findViewById(R.id.gridview);
		gridView.setNumColumns(2);
		grid_Adapter = new Grid_Adapter(new String[]{"报    修","保    养"});
		grid_Adapter.setChecked(new Integer(item));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				item = arg2+"";
				grid_Adapter.setChecked(arg2);
				grid_Adapter.notifyDataSetChanged();
				setList_Adapter(arg2+"");
			}
		});
		gridView.setAdapter(grid_Adapter);
		add_note.setOnClickListener(this);
		back.setOnClickListener(this);
		
		//list_Adapter = new List_Adapter(adviseInfos);
		//list.setAdapter(list_Adapter);
		setList_Adapter(item);
	}
	private void setList_Adapter(final String item){
		final Dialog_UI dialog_UI = new Dialog_UI(BaoXiu_Activity.this, "正在加载...");
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
							IP_Address.MAINTAINADVISESERVICE,
							"GetRepairsMaintainInfo", 
							new String[]{
							"strAccount","strRepairType"
							},
							new String[]{
							Constant.UserName, item
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
				//list_Adapter.notifyDataSetChanged();
			}
			
		}.execute((Void)null);
	}
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();

		switch (v.getId()) {
		case R.id.addnote:
			intent.setClass(BaoXiu_Activity.this, BaiXiu_AddActivity.class);
			break;
		case R.id.note_back:
			intent.setClass(BaoXiu_Activity.this, MainActivity.class);
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
		
		
		LinearLayout ly = (LinearLayout) ((Activity) BaoXiu_Activity.this)
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
				is_delete(position);
			}
		});
		Button button2 = (Button)ly.findViewById(R.id.imageView2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaoXiu_Activity.this, BaoXiu_InfoActivity.class);
				intent.putExtra("baoxiu_info", 
						adviseInfos.get(position).getContent()+"$$"+
						adviseInfos.get(position).getDate()+"$$"+
						adviseInfos.get(position).getMachineNO()+"$$"+
						adviseInfos.get(position).getMachineModel()+"$$"+
						adviseInfos.get(position).getProductDate()+"$$"+
						adviseInfos.get(position).getGUID());
				intent.putExtra("item", item);
				startActivity(intent);
				BaoXiu_Activity.this.finish();
			}
		});
		

		return ly;
	}
}
	 /*
     * 确认是否删除
     * */
    public  void is_delete(final int position){
        //提示对话框
        AlertDialog.Builder builder=new Builder(BaoXiu_Activity.this);
        builder.setTitle("确定是否删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
        	@Override
            public void onClick(DialogInterface dialog, int which) {
				
				new AsyncTask<Void, Void, String>(){
					
					@Override
					protected String doInBackground(Void... params) {
						String str = "";
						try {
							str = ContentWeb.getinstance().contetweb(
									IP_Address.MAINTAINADVISESERVICE,
									"DelRepairsMaintainInfo", 
									new String[]{
											"strGUID"
									},
									new String[]{
											adviseInfos.get(position).getGUID()
									},null);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return str;
					}
					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						new Toast_Creat(BaoXiu_Activity.this, result, 3000).show_toast();
						setList_Adapter(item);
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
	class Grid_Adapter extends BaseAdapter{
		private String [] str = null;
		Grid_Adapter(String [] str){
			this.str = str;
		}
		private Integer item = 0;
		private void setChecked(int item){
			this.item = item;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(BaoXiu_Activity.this);
			tv.setText(str[position]+"");
			tv.setTextColor(Color.BLACK);
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(18);
			if(item == position){
			tv.setBackgroundResource(R.drawable.lingdao_checkbg);
			}else{
				tv.setBackgroundDrawable(null);
			}
			return tv;
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
			return str.length;
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			Intent intent = new Intent(BaoXiu_Activity.this,
					MainActivity.class);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
	
}

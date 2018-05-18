package com.elegps.gz_customerservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.elegps.service.StartService;

import com.constant.Constant;
import com.elegps.Complaints_proposals.Complaints_ProposalsActivity;
import com.elegps.UIManager.Toast_Creat;
import com.elegps.buy.Buy_LishiActivity;
import com.elegps.errorfind.Error_DemadFindActivity;
import com.elegps.getLocation.LocationOverlayDemo;
import com.elegps.help.ACache;
import com.elegps.help.BitmapCache;
import com.elegps.help.PublicWay;
import com.elegps.javabean.AppUserInfo;
import com.elegps.module.data_analyze.DataAnalyzeActivity;
import com.elegps.module.machine_stock_search.MachineStockSearchActivity;
import com.elegps.module.task_daiban_search.TaskDaiBanSearchActivity;
import com.elegps.module.task_search.TaskSearchActivity;
import com.elegps.module.work_hours.WorkHoursSearchActivity;
import com.elegps.notebook.Note_BookActivity;
import com.elegps.notebook.Note_PingTaiActivity;
import com.elegps.notebook.Note_paiG;
import com.elegps.photo.Photo_NativeActivity;
import com.elegps.vedio.EnterRoomBuffActivity;
import com.elegps.warranty.BaoXiu_Activity;

public class MainActivity extends Activity implements OnClickListener{

	private boolean b = false;// 如果是true就是本地上传，否则就是拍照上传
	private ImageView[] main_pages = null;
	private Bitmap photo = null;
	private File file = null;
	
	private ImageView imageView = null;
	private ImageView ivTaskSearch,ivMachineStockSearch,ivTaskDaiBanSearch,ivWorkHoursSearch,ivMyHours,ivDataAnalysis;

	private AppUserInfo appUserInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		Constant.screenWidth = dm.widthPixels;
		Constant.screenHeight = dm.heightPixels;
		PublicWay.activityList.add(this);

		init();
	}

	private void init() {

		appUserInfo = (AppUserInfo) ACache.getObject(Constant.APPUSERINFO);
		
		RelativeLayout ly = null;
			
			ly = (RelativeLayout) MainActivity.this
					.getLayoutInflater().inflate(R.layout.zhuye, null);



		ivTaskSearch = (ImageView)ly.findViewById(R.id.ivTaskSearch);
		ivTaskSearch.setOnClickListener(this);

		ivMachineStockSearch = (ImageView)ly.findViewById(R.id.ivMachineStockSearch);
		ivMachineStockSearch.setOnClickListener(this);

		ivTaskDaiBanSearch = (ImageView)ly.findViewById(R.id.ivTaskDaiBanSearch);
		ivTaskDaiBanSearch.setOnClickListener(this);

		ivWorkHoursSearch = (ImageView)ly.findViewById(R.id.ivWorkHoursSearch);
		ivWorkHoursSearch.setOnClickListener(this);

		ivMyHours = (ImageView)ly.findViewById(R.id.ivMyHours);
		ivMyHours.setOnClickListener(this);

		ivDataAnalysis = (ImageView)ly.findViewById(R.id.ivDataAnalysis);
		ivDataAnalysis.setOnClickListener(this);
		ivDataAnalysis.setVisibility(View.INVISIBLE);


		if(!TextUtils.isEmpty(appUserInfo.getRoleName())){
			if((appUserInfo.getRoleName().indexOf("公司领导")!=-1)||(appUserInfo.getRoleName().indexOf("车间主任")!=-1)){

				ivDataAnalysis.setVisibility(View.VISIBLE);
			}else{
				ivDataAnalysis.setVisibility(View.INVISIBLE);

			}

		}else{
			ivDataAnalysis.setVisibility(View.INVISIBLE);

		}


		main_pages = new ImageView[6];
		main_pages[0] = (ImageView) ly.findViewById(R.id.mainpage_video);
		main_pages[1] = (ImageView) ly.findViewById(R.id.mainpage_photo);
		main_pages[2] = (ImageView) ly.findViewById(R.id.mainpage_location);
		main_pages[3] = (ImageView) ly.findViewById(R.id.mainpage_error);
		main_pages[4] = (ImageView) ly.findViewById(R.id.mainpage_fitting);
		main_pages[5] = (ImageView) ly.findViewById(R.id.mainpage_note);
		
		imageView = (ImageView)ly.findViewById(R.id.aa);
		imageView.setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.aa, this));
		if("1".equals(Constant.ISINSIDE)){
			main_pages[0].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_vedio, this));
			main_pages[1].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_photo, this));
			main_pages[2].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_location, this));
			main_pages[3].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_error, this));
			main_pages[5].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_fitting, this));
			main_pages[4].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_note, this));
		}else{
			main_pages[0].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_vedio, this));
			main_pages[1].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_photo, this));
			main_pages[2].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_location, this));
			main_pages[3].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_baoxiu, this));
			main_pages[5].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_fitting, this));
			main_pages[4].setImageBitmap(BitmapCache.getInstance().getBitmapById(R.drawable.zhuye_tousu, this));
		}
		
		for (int i = 0; i < main_pages.length; i++) {

			main_pages[i].setOnClickListener(listener);
		}
		
		setContentView(ly);
		
		if("1".equals(Constant.ISINSIDE)){
			//开机启动后台服务
			  Intent intent = new Intent(MainActivity.this,StartService.class);
		/*	  intent.putExtra("strAccount",Constant.UserName);
			  intent.putExtra("strUserName",Constant.DENGLUNAME);*/
			  
			    startService(intent);			
			    }else{
			    }

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.mainpage_video: // 视频通话
				Intent intent4 = new Intent(MainActivity.this, EnterRoomBuffActivity.class);
				startActivity(intent4);
				MainActivity.this.finish();
				break;
			case R.id.mainpage_photo: // 拍照上传 
					
				showCustomMessage();
				
				break;
			case R.id.mainpage_location:// 配件购买
				Intent intent3 = new Intent(MainActivity.this,
						Buy_LishiActivity.class);
					startActivity(intent3);
					MainActivity.this.finish();
					
				break;
			case R.id.mainpage_error: // 故障查询
				
				if("1".equals(Constant.ISINSIDE)){
					Intent intent2 = new Intent(MainActivity.this,
							Error_DemadFindActivity.class);
					startActivity(intent2);
					MainActivity.this.finish();			
					//new Toast_Creat(ZhuYeActivity.this, "该功能正在完善中...", 2000).show_toast();
					}else{
						Intent intent2 = new Intent(MainActivity.this,
								BaoXiu_Activity.class);
						startActivity(intent2);
						MainActivity.this.finish();	
					}
				
				break;		
			case R.id.mainpage_fitting: // 日志管理
				if("1".equals(Constant.ISINSIDE)){
					note_manager();
					}else{
						Intent intent = new Intent(MainActivity.this,
								Note_PingTaiActivity.class);
						startActivity(intent);
						MainActivity.this.finish();
					}
				
				break;
			case R.id.mainpage_note: // 联系我们
				
				Intent intent13 = new Intent(MainActivity.this,
						LianXi_Activity.class);
				startActivity(intent13);
				MainActivity.this.finish();


//				Intent intent13 = new Intent(MainActivity.this,
//						TaskSearchActivity.class);
//				startActivity(intent13);

//				Intent intent13 = new Intent(MainActivity.this,
//						MachineStockSearchActivity.class);
//				startActivity(intent13);

//				Intent intent13 = new Intent(MainActivity.this,
//						TaskDaiBanSearchActivity.class);
//				startActivity(intent13);

//				Intent intent13 = new Intent(MainActivity.this,
//						WorkHoursSearchActivity.class);
//				startActivity(intent13);
				break;
			default:
				break;
			}
		}
	};
	//日志管理
	private void note_manager(){
		
		final Dialog lDialog = new Dialog(MainActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.note_manager);
		((TextView) lDialog.findViewById(R.id.dialog_title)).setText("提示");
		((Button) lDialog.findViewById(R.id.ok))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						Note_BookActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		((Button) lDialog.findViewById(R.id.ok1))
				.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								Note_paiG.class);
						startActivity(intent);
						MainActivity.this.finish();
					}
				});
		((Button) lDialog.findViewById(R.id.ok2))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					 
						lDialog.dismiss();
					}
				});		
		((Button) lDialog.findViewById(R.id.pingtai))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								Note_PingTaiActivity.class);
						startActivity(intent);
						MainActivity.this.finish();
					}					
				});
		((Button) lDialog.findViewById(R.id.jianyi))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,  //一般信息
						Complaints_ProposalsActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}					
		});
		lDialog.show();
	
	}
	private void showCustomMessage() {
		final Dialog lDialog = new Dialog(MainActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar);
		lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		lDialog.setContentView(R.layout.r_okcanceldialogview);

		if(TextUtils.isEmpty(Constant.ISINSIDE)){

			Toast.makeText(this, "当前账号角色为空!", Toast.LENGTH_SHORT).show();
			return;
		}

		if(Constant.ISINSIDE.equals("0")){
			((Button) lDialog.findViewById(R.id.dingwei)).setVisibility(View.GONE);
		}
		((TextView) lDialog.findViewById(R.id.dialog_title)).setText("提示");
		((Button) lDialog.findViewById(R.id.dingwei))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				b = true;
				Intent intent1 = new Intent(MainActivity.this,
						LocationOverlayDemo.class);
				startActivity(intent1);
			}
		});
		((Button) lDialog.findViewById(R.id.ok))
		.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				b = true;
				// 打开图库选择图片
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 1);
			}
		});
		((Button) lDialog.findViewById(R.id.ok1))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						photograph();
						b = false;
					}
				});
		((Button) lDialog.findViewById(R.id.lishi))
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PaiZhao_LiShi.class);
				startActivity(intent);
			}
		});
		((Button) lDialog.findViewById(R.id.ok2))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						lDialog.dismiss();
					}
				});
		lDialog.show();

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

				Intent intent = new Intent(MainActivity.this,
						Photo_NativeActivity.class);
				
				intent.putExtra("setPATH", path);
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
		File f = new File(Constant.photoPath+"/photos/");
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
		
		String	strMailID = dateFormat.format( now ); 
		destoryImage();
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			file = new File(f, /*Constant.photoName*/strMailID+".png");
			file.delete();
			if(!(f.isFile())){
				f.mkdirs();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					Toast.makeText(MainActivity.this, "文件不存在",	 
							Toast.LENGTH_LONG).show();
					return;
				}
			}
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			startActivityForResult(intent, 1);
		} else {
			Toast.makeText(MainActivity.this, "sdcard不存在", Toast.LENGTH_SHORT)
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
			
			exit_Activity();
	
			return true;
		}
		return false;
	}
	protected void exit_Activity() {

			
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		
		builder.setMessage("确认退出吗？");
		builder.setPositiveButton("退出",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						for(int i=0;i<PublicWay.activityList.size();i++){
							if (null != PublicWay.activityList.get(i)) {
								PublicWay.activityList.get(i).finish();
							}
						}
						MainActivity.this.finish();
						System.exit(0);
					}
				});

		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		builder.create().show();
	}
	protected void onResume() {
		super.onResume();
	}

	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public void onClick(View view) {


		Intent intent = new Intent();
		switch (view.getId()){

			case R.id.ivTaskSearch:  //生产任务查询
				intent.setClass(this,TaskSearchActivity.class);
				break;
			case R.id.ivMachineStockSearch://已入库机器查询
				intent.setClass(this,MachineStockSearchActivity.class);

				break;
			case R.id.ivTaskDaiBanSearch://机器进度
				intent.setClass(this,TaskDaiBanSearchActivity.class);

				break;
			case R.id.ivWorkHoursSearch://计算工时
				intent.setClass(this,WorkHoursSearchActivity.class);
				intent.putExtra(Constant.IS_MY_HOURS,false);

				break;
			case R.id.ivMyHours://我的工时
				intent.setClass(this,WorkHoursSearchActivity.class);
				intent.putExtra(Constant.IS_MY_HOURS,true);
				break;
			case R.id.ivDataAnalysis://数据分析
				intent.setClass(this,DataAnalyzeActivity.class);

				break;
		}

		startActivity(intent);
	}
}

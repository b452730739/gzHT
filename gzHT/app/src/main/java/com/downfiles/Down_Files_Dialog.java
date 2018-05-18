package com.downfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.Constant;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.gz_customerservice.R;
import com.elegps.javabean.Down_File;


public class Down_Files_Dialog extends Dialog {

	// 固定下载的资源路径，这里可以设置网络上的地址
	private static final String URL = "http://183.63.165.198/HTASFile/";
	// 固定存放下载文件路径：SD卡目录下
	//private static final String SD_PATH = "/mnt/sdcard/";
	// 存放各个下载器
	private Map<String, Download> downloaders = new HashMap<String, Download>();
	// 存放与下载器对应的进度条
	private Map<String, ProgressBar> ProgressBars = new HashMap<String, ProgressBar>();
	/**
	 * 利用消息处理机制适时更新进度条
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				String url = (String) msg.obj;
				int length = msg.arg1;
				ProgressBar bar = ProgressBars.get(url);
				if (bar != null) {
					// 设置进度条按读取的length长度更新
					bar.incrementProgressBy(length);
					if (bar.getProgress() == bar.getMax()) {
						LinearLayout layout = (LinearLayout) bar.getParent();
						final TextView resouceName = (TextView) layout
								.findViewById(R.id.tv_resouce_name);
						/*Toast.makeText(context,
								"[" + resouceName.getText() + "]下载完成！",
								Toast.LENGTH_SHORT).show();*/
						// 下载完成后清除进度条并将map中的数据清空
						
						layout.removeView(bar);
						ProgressBars.remove(url);
						downloaders.get(url).delete(url);
						downloaders.get(url).reset();
						downloaders.remove(url);
						final String txtTag = resouceName.getTag().toString().substring(
								resouceName.getTag().toString().lastIndexOf("/")+1,
								resouceName.getTag().toString().length());
						Button btn_start = (Button) layout
								.findViewById(R.id.btn_start);
						Button btn_pause = (Button) layout
								.findViewById(R.id.btn_pause);
						btn_pause.setText("查看");
						open_File( 
								Constant.User_Path+"/"+txtTag
								, null);
						btn_pause.setOnClickListener(new android.view.View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										try {
											open_File(
													Constant.User_Path+"/"+txtTag
													, null);
										} catch (Exception e) {
										Toast.makeText(context, "文件已删除或不存在", 0).show();
										}
									}
								});
					}
				}
			}
		}
	};

	private Context context = null;
	private String Value = null;

	private ListView listView = null;
	//private ArrayList<Down_File> arrayList = null;
	private Button close = null;
	public Down_Files_Dialog(Context context, String Value) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		this.Value = Value.replaceAll("E:\\\\GLOAFILE\\\\", "");
		this.setContentView(R.layout.down_file_dialog);
		showListView();
	}
	
	// 显示listView，这里可以随便添加
	private void showListView() {
		
		close = (Button)findViewById(R.id.dialog_back);
		close.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Down_Files_Dialog.this.dismiss();
			}
		});
		List<Map<String, Down_File>> data = new ArrayList<Map<String, Down_File>>();
			
		 try { 
			 for(int i = 0;i<Value.split("\\*").length;i++){
				  
				 String temp = Value.split("\\*")[i];
				  
				  Down_File down_File = new Down_File();
				  
				  down_File.setId(temp.split("\\|")[0]);
				  down_File.setName(temp.split("\\|")[1]);
				  down_File.setPath(temp.split("\\|")[3]); 
			

					Map<String, Down_File> map = new HashMap<String, Down_File>();
					map.put("name", down_File);
					data.add(map);
				  } 
			 }
				 catch (Exception e) { 
					 e.printStackTrace(); 
				 }
		
		 
		listView = (ListView) this.findViewById(R.id.listview);
		listView.setBackgroundResource(R.drawable.send_bg);
		listView.setDivider(context.getResources().getDrawable(R.drawable.note_horizontal));
		
		DownLoadAdapter adapter = new DownLoadAdapter(context, data);
		listView.setAdapter(adapter);

	}
	
	/**
	 * 响应开始下载按钮的点击事件
	 */
	public void startDownload(View v) {
		// 得到textView的内容
		LinearLayout layout = (LinearLayout) v.getParent();
		String resouceName = ((TextView) layout
				.findViewById(R.id.tv_resouce_name)).getTag().toString();
		String urlstr = URL + resouceName;
		String localfile = Constant.User_Path +"/"+
		resouceName.substring(resouceName.lastIndexOf("/")+1,
				resouceName.length());
		
		System.out.println(localfile);
		
		// 设置下载线程数，
		String threadcount = "10";
		DownloadTask downloadTask = new DownloadTask(v);
		downloadTask.execute(urlstr, localfile, threadcount);
		
	};

	class DownloadTask extends AsyncTask<String, Integer, LoadInfo> {
		Download downloader = null;
		View v = null;
		String urlstr = null;

		public DownloadTask(final View v) {
			this.v = v;
		}
		@Override
		protected void onPreExecute() {
			Button btn_start = (Button) ((View) v.getParent())
					.findViewById(R.id.btn_start);
			Button btn_pause = (Button) ((View) v.getParent())
					.findViewById(R.id.btn_pause);
			btn_start.setVisibility(View.GONE);
			btn_pause.setVisibility(View.VISIBLE);
		}
		
		@Override
		protected LoadInfo doInBackground(String... params) {
			urlstr = params[0];
			String localfile = params[1];
			int threadcount = Integer.parseInt(params[2]);
			// 初始化一个downloader下载器
			downloader = downloaders.get(urlstr);
			if (downloader == null) {
				downloader = new Download(urlstr, localfile, threadcount,
						context, mHandler);
				downloaders.put(urlstr, downloader);
			}
			if (downloader.isdownloading())
				return null;
			// 得到下载信息类的个数组成集合
			return downloader.getDownloaderInfors();
		}

		@Override
		protected void onPostExecute(LoadInfo loadInfo) {
			if (loadInfo != null) {
				// 显示进度条
				showProgress(loadInfo, urlstr, v);
				// 调用方法开始下载
				downloader.download();
			}
		}

	};

	/**
	 * 显示进度条
	 */
	private void showProgress(LoadInfo loadInfo, String url, View v) {
		ProgressBar bar = ProgressBars.get(url);
		if (bar == null) {
			bar = new ProgressBar(context, null,
					android.R.attr.progressBarStyleHorizontal);
			bar.setMax(loadInfo.getFileSize());
			bar.setProgress(loadInfo.getComplete());
			ProgressBars.put(url, bar);
			LinearLayout.LayoutParams params = new LayoutParams(
					LayoutParams.FILL_PARENT, 20);
			((LinearLayout) ((LinearLayout) v.getParent()).getParent())
					.addView(bar, params);
		}
	}

	/**
	 * 响应暂停下载按钮的点击事件
	 */
	public void pauseDownload(View v) {
		LinearLayout layout = (LinearLayout) v.getParent();
	/*	String resouceName = ((TextView) layout
				.findViewById(R.id.tv_resouce_name)).getText().toString();*/
		String urlstr = URL + ((TextView) layout
				.findViewById(R.id.tv_resouce_name)).getTag();
		downloaders.get(urlstr).pause();
		Button btn_start = (Button) ((View) v.getParent())
				.findViewById(R.id.btn_start);
		Button btn_pause = (Button) ((View) v.getParent())
				.findViewById(R.id.btn_pause);
		btn_pause.setVisibility(View.GONE);
		btn_start.setVisibility(View.VISIBLE);
	}
	private void open_File(String path,Dialog_UI dialog_UI){
		try {
			Intent intent = new File_Info()
			.openFile(path);
			
		      // Bundle bundle = new Bundle();
	           
               //  bundle.putString("UserName",/* Constant.userInfos.get(0).getUserName()*/"");
			//intent.putExtras(bundle);													
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "没有合适的应用程序打开该文件！", 
					0)
			.show();		
		}
	}
	class DownLoadAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<Map<String, Down_File>> data;
		private Context context;

		public DownLoadAdapter(Context context, List<Map<String, Down_File>> data) {
			this.context = context;
			mInflater = LayoutInflater.from(context);
			this.data = data;
		}

		public void refresh(List<Map<String, Down_File>> data) {
			this.data = data;
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final Map<String, Down_File> bean = data.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.down_list_item, null);
				holder = new ViewHolder();
				holder.resouceName = (TextView) convertView
						.findViewById(R.id.tv_resouce_name);
				final Button start = (Button) convertView
						.findViewById(R.id.btn_start);
				//start.setEnabled(true);
		/*		start.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						String state = Environment.getExternalStorageState();
						if (state.equals(Environment.MEDIA_MOUNTED)) {
							try {
								startDownload(v);
							} catch (Exception e) {
								e.printStackTrace();
							}
						
						} else {
							Toast.makeText(context, "sdcard不可用", Toast.LENGTH_SHORT)
									.show();
						}
			}
				});*/
				holder.startDownload = start;
				Button pause = (Button) convertView
						.findViewById(R.id.btn_pause);
				holder.pauseDownload = pause;
				pause.setOnClickListener(new android.view.View.OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							pauseDownload(v);
						} catch (Exception e) {
							e.printStackTrace();
						}
				

					}
				});
				convertView.setTag(holder);
				
				new AsyncTask<Void, Void, Void>() {
					
					
					
					boolean b = false;
					String filename =bean.get("name").getPath().substring(
							bean.get("name").getPath().lastIndexOf("/")+1, 
							bean.get("name").getPath().length()); 
					@Override
					protected Void doInBackground(Void... params) {
					/*	if(i == 0){
							b = false;
						}else{*/
							b = new File(Constant.User_Path+"/" + filename).isFile()&&
									GetDownloadInfos.getInstance(context).If_Complete(
											URL + bean.get("name").getPath()+"");
					//	}
					
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						start.setEnabled(true);
						if(b){
							
						/*	//**
							 * 如果该文件不在数据库中并且存在本地文件
							 *//*
*/						start.setText("查看");
						start.setOnClickListener(new android.view.View.OnClickListener() {
								
									@Override
									public void onClick(View v) {
										final Dialog_UI dialog_UI= new Dialog_UI(context, "正在查看...");
										dialog_UI.show();
										new AsyncTask<Void, Void, Void>() {

											@Override
											protected Void doInBackground(
													Void... params) {
												
												open_File(Constant.User_Path+"/"+ 
															filename, dialog_UI);
												return null;
											}
											
											@Override
											protected void onPostExecute(
													Void result) {
												super.onPostExecute(result);
												dialog_UI.dismiss();
											}
											
										}.execute((Void)null);
									
										}
								});
						}else{
							start.setOnClickListener(new android.view.View.OnClickListener() {

								@Override
								public void onClick(View v) {
									String state = Environment.getExternalStorageState();
									if (state.equals(Environment.MEDIA_MOUNTED)) {
										try {
											startDownload(v);
										} catch (Exception e) {
											e.printStackTrace();
										}
									
									} else {
										Toast.makeText(context, "sdcard不可用", Toast.LENGTH_SHORT)
												.show();
									}
						}
							});
						}
					}
				}.execute((Void)null);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.resouceName.setTag(bean.get("name").getPath()+"");

			holder.resouceName.setText(bean.get("name").getName());
			return convertView;
		}

		private class ViewHolder {
			public TextView resouceName;
			public Button startDownload;
			public Button pauseDownload;
		}
	}

}

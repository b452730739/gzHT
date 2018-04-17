package com.elegps.update;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.constant.IP_Address;
import com.elegps.gz_customerservice.R;


public class UpdateAPP extends AsyncTask<Void, Void, Void>{


	
	private String filename ="" ;
	private String TAG = "UpdateAPP";
	private AQuery aq;
	private String Client_version;
	private static final int DIALOG_SIMPLE = 2;
	private UpdateStruct update_beans = null;	
	public static  Boolean version_Judgment = false;

	private Context mContext = null;
	public UpdateAPP(Context mContext){
		this.mContext = mContext;
		filename = mContext.getResources().getString(R.string.app_name);
		Client_version = getAppVersionName(mContext);
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		aq = new AQuery(mContext);
		AjaxCallback<String> callback = new AjaxCallback<String>();
		callback.encoding("GB2312");

		callback.type(String.class).url(IP_Address.UPDATE);
		aq.sync(callback);
		String str = callback.getResult();
		
		try {
			update_beans =update(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
			 * 判断是否需要更新
			 */
		try {
			Log.e(TAG,Client_version+"<<<<<<<<<>>>>>>>>"+update_beans.getVersion());

			if ((Float.parseFloat(Client_version))>=(Float.parseFloat(update_beans.getVersion()))) {
				version_Judgment = false;
			} else {
				version_Judgment = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (version_Judgment == false) {
			
		} else {
				
			onCreateDialog(DIALOG_SIMPLE).show();
		}
		super.onPostExecute(result);
	}
	public  static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "  ";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SIMPLE:
			return new AlertDialog.Builder(mContext)
					.setTitle("软件有更新")
					.setIcon(R.drawable.lognr)
					.setMessage(update_beans.getName())
					.setPositiveButton("更新",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
//									try {
//										Down_Apk(update_beans.getUrl());
//									} catch (Exception e) {
//										e.printStackTrace();
//										Toast.makeText(mContext, "更新失败!", 0).show();
//									}
									startURL(update_beans.getUrl());
								};
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).create();
		}
		return null;
	}
	private void startURL(String url){
		 Intent intent = new Intent();        
	        intent.setAction("android.intent.action.VIEW");    
	        Uri content_url = Uri.parse(/*"http://a.app.qq.com/o/simple.jsp?pkgname=com.poctalk.taxi"*/url);   
	        intent.setData(content_url);  
	        mContext.startActivity(intent);
	}
	private void Down_Apk(String url){
	    DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
		Uri uri = Uri.parse(url);
		Request request = new Request(uri);
		request.setAllowedNetworkTypes(
				DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);  
		
		request.setVisibleInDownloadsUi(true);
		
	request.setDestinationInExternalFilesDir(mContext, null, filename);
	long id = downloadManager.enqueue(request);
	//TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
	}
	/**
	 * 从url获取的apk更新的信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  UpdateStruct update(String str) throws Exception {
		
		UpdateStruct users = null;

		XmlPullParser parser = Xml.newPullParser();
				InputStream is = new ByteArrayInputStream(str.getBytes()); 
				parser.setInput(is, "utf-8");
		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("update".equals(parser.getName())){
					
					users = new UpdateStruct();
					
				}else if ("version".equals(parser.getName())) {
					eventCode = parser.next();
					users.setVersion(parser.getText());
				}else if ("name".equals(parser.getName())) {
					eventCode = parser.next();
					users.setName(parser.getText());
				}else if ("url".equals(parser.getName())) {
					eventCode = parser.next();
					users.setUrl(parser.getText());
				}
				break;

			case XmlPullParser.END_TAG:

				
				if (parser.getName().equals("update")) {

		
				}


				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return users;
	}
}

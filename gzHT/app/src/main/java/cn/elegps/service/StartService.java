package cn.elegps.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.gz_customerservice.LogingActivity;
import com.elegps.gz_customerservice.R;

public class StartService extends Service{
	private Handler handler = null;
	private Runnable runnable = null;
	private String strAccount = "";
	private String strUserName = "";
 
 private void registerIntentReceiver(){
  //此处添加启动服务要执行的操作代码 
	handler = new Handler();
	runnable = new Runnable() {

		@Override
		public void run() {
			
			get_value();
			handler.postDelayed(this, 600000);
		}
	};
	handler.postDelayed(runnable, 600000);
 }
 public void onStart(Intent intent,int startId){
 	registerIntentReceiver();
 	
 }
    @Override 
    public void onCreate() {
    	String info = setAccountInfo("config");
    	strAccount = info.split("\\$")[0];
    	try {
			strUserName = info.split("\\$")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private void get_value(){
    	new AsyncTask<Void, Void, String>() {
    		
			@Override
			protected String doInBackground(Void... params) {
				String str = "";
				try {
					str = ContentWeb.getinstance().contetweb(
					IP_Address.LOGSERVICE,
					"GetPGAlertInfo", 
					new String[]{"strAccount","strUserName"},
					new String[]{strAccount,strUserName},null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return str;
			}
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				try {
					if(result.equals("")|result == null){
					}else{
						show_Notification(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}.execute((Void)null);
    }
    private void show_Notification(String str){
    	
    //String str = "你有一份新的消息..."	;
    	
    NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    Notification n = new Notification(R.drawable.lognr, str, System.currentTimeMillis());
    n.flags = Notification.FLAG_AUTO_CANCEL; 
    n.defaults=Notification.DEFAULT_SOUND;
    Intent i = new Intent(StartService.this, LogingActivity.class);
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent contentIntent = PendingIntent.getActivity(
    		StartService.this, 
   R.string.app_name, 
   i, 
PendingIntent.FLAG_UPDATE_CURRENT);
    n.setLatestEventInfo(
    		StartService.this,
    		/*strUserName*/"", 
        str, 
        contentIntent);
    nm.notify(R.string.app_name, n);
}
    private String setAccountInfo( String name/** 文件�?*/
    		) {
    			String str = "";
    			ObjectInputStream in = null;
    			String mAccount = null;
    			try {
    				InputStream is = openFileInput(name);
    				in = new ObjectInputStream(is);
    				mAccount = (String) in.readObject();

    				if (mAccount != null) {
    					str = mAccount;
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			} finally {
    				if (in != null) {
    					try {
    						in.close();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
    				}
    			}
				return str;

    		}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}}

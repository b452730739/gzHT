package com.elegps.update;

import java.io.File;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class UpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();  
		
	    if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {  
	    	Down_Over(context, intent);
	        return;
	    }
	}
    private DownloadManager downloadManager; 

    private void Down_Over( Context context, Intent intent){
  	  long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);                                                                                      //TODO 鍒ゆ柇杩欎釜id涓庝箣鍓嶇殑id鏄惁鐩哥瓑锛屽鏋滅浉绛夎鏄庢槸涔嬪墠鐨勯偅涓涓嬭浇鐨勬枃浠� 
        Query query = new Query();  
        query.setFilterById(id);  
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);  
        Cursor cursor = downloadManager.query(query);  
          
        int columnCount = cursor.getColumnCount();  
        String path = null;                                                                                                                                       //TODO 杩欓噷鎶婃墍鏈夌殑鍒楅兘鎵撳嵃涓�笅锛屾湁浠�箞闇�眰锛屽氨鎬庝箞澶勭悊,鏂囦欢鐨勬湰鍦拌矾寰勫氨鏄痯ath  
        while(cursor.moveToNext()) { 
 

                String columnName = cursor.getColumnName(0);  
                String string = cursor.getString(1);  
        		Log.e("action", string+"<<<<<<<<<<<<<文件名");

                if(columnName.equals("local_uri")) {  
                    path = string;  
                }  
                if(string != null) {  
        	      install(context, string);
                }else {  
                }  
        }  
        cursor.close();   
  }
	private void install( Context context,String fileName) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		intent.setDataAndType(Uri.fromFile(new File(fileName)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}

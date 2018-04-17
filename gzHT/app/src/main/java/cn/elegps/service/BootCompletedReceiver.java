package cn.elegps.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver{ 
 public void onReceive(Context context, Intent intent) {
  if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){

   Intent newIntent = new Intent(context,StartService.class);
   context.startService(newIntent);
  } 
 }
}
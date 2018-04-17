package com.elegps.vedio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.constant.Constant;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;

public class EnterRoomBuffActivity extends Activity implements AnyChatBaseEvent{
	private boolean bNeedRelease = false;
	private ConfigEntity configEntity;

	public AnyChatCoreSDK anychat;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PublicWay.activityList1.add(this);
		PublicWay.activityList.add(this);
		
		
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		
		TextView tv = new TextView(this);
		tv.setText("正在进入...");
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(27);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		tv.setLayoutParams(layoutParams);
		relativeLayout.addView(tv);
		
		setContentView(relativeLayout);
		
	     /*int style = DialogFragment.STYLE_NORMAL, theme = 0;  
	     setStyle(style,theme); */
	

		setDisPlayMetrics();
		configEntity = ConfigService.LoadConfig(this);
		InitialSDK();
		//InitialLayout();
		Login() ;

	}
	private void Login() {
		
		configEntity.IsSaveNameAndPw = true;
		configEntity.ip = Constant.ServerIP;
		configEntity.port = Constant.ServerPort;
		configEntity.name = Constant.UserName;
		configEntity.password = "321";
		
		ConfigService.SaveConfig(this, configEntity);

	this.anychat.Connect(configEntity.ip, configEntity.port);

	this.anychat.Login(configEntity.name,configEntity.password );

}
	private void setDisPlayMetrics() {
		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		ScreenInfo.WIDTH = dMetrics.widthPixels;
		ScreenInfo.HEIGHT = dMetrics.heightPixels;
	}
	private void InitialSDK() {
		ApplyVideoConfig();
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			anychat.SetBaseEvent(this);
			if (configEntity.useARMv6Lib != 0)
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_USEARMV6LIB, 1);
			anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			ApplyVideoConfig();
			
			bNeedRelease = true;
		}
	}
	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {

			Toast.makeText(this, "正在视频通话中,请稍后..." , Toast.LENGTH_LONG)
			.show();
			
			Intent intent = new Intent(EnterRoomBuffActivity.this, MainActivity.class);
			startActivity(intent);
			EnterRoomBuffActivity.this.finish();
			
		  	try {
		  		anychat.Release();	// 关闭SDK，不再返回登录界面
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {

		if(dwErrorCode == 0)
	    {
	    	if(dwRoomId == 1)
	    	{
				Intent intent = new Intent();  
				intent.putExtra("RoomID", dwRoomId);
				intent.setClass(EnterRoomBuffActivity.this, RoomActivity.class); 
				startActivity(intent);   
	    	}
	    
	    }
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
	/*	Toast.makeText(this, "连接关闭，error：" + dwErrorCode, Toast.LENGTH_SHORT)
				.show();*/
		Toast.makeText(this, "正在视频通话中,请稍后..." , Toast.LENGTH_LONG)
		.show();
		
		
		Intent intent = new Intent(EnterRoomBuffActivity.this, MainActivity.class);
		startActivity(intent);
		EnterRoomBuffActivity.this.finish();
		
	  	try {
	  		anychat.Release();	// 关闭SDK，不再返回登录界面
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			bNeedRelease = false;
	        anychat.EnterRoom(1, "");				

		} else {
			
			Toast.makeText(this, "正在视频通话中,请稍后..." , Toast.LENGTH_SHORT).show();

			/*Toast.makeText(this, "登录失败，错误代码：" + dwErrorCode, Toast.LENGTH_SHORT)
					.show();*/
			Intent intent = new Intent(EnterRoomBuffActivity.this, MainActivity.class);
			startActivity(intent);
			EnterRoomBuffActivity.this.finish();
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		
	}
	
	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		
	}
	protected void onDestroy() {

		System.out.println("onDestroy()");
		if (bNeedRelease) {
	/*	  	anychat.LeaveRoom(-1);
	    	anychat.Logout();*/
	    	anychat.Release();	// 关闭SDK，不再返回登录界面
			System.out.println("Release()");

		}
		super.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			
			Intent intent = new Intent(EnterRoomBuffActivity.this,MainActivity.class
					);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
	// 根据配置文件配置视频参数
	private void ApplyVideoConfig()
	{
		ConfigEntity configEntity = ConfigService.LoadConfig(this);
		if(configEntity.configMode == 1)		// 自定义视频参数配置
		{
			// 设置本地视频编码的码率（如果码率为0，则表示使用质量优先模式）
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL, configEntity.videoBitrate);
			if(configEntity.videoBitrate==0)
			{
				// 设置本地视频编码的质量
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL, configEntity.videoQuality);
			}
			// 设置本地视频编码的帧率
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL, configEntity.videoFps);
			// 设置本地视频编码的关键帧间隔
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL, configEntity.videoFps*4);
			// 设置本地视频采集分辨率
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL, configEntity.resolution_width);
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL, configEntity.resolution_height);
			// 设置视频编码预设参数（值越大，编码质量越高，占用CPU资源也会越高）
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL, configEntity.videoPreset);
		}
		// 让视频参数生效
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM, configEntity.configMode);
		// P2P设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC, configEntity.enableP2P);
		// 本地视频Overlay模式设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY, configEntity.videoOverlay);
		// 回音消除设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL, configEntity.enableAEC);
		// 平台硬件编码设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC, configEntity.useHWCodec);
		// 视频旋转模式设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL, configEntity.videorotatemode);
		// 视频采集驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER, configEntity.videoCapDriver);
		// 本地视频采集偏色修正设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA, configEntity.fixcolordeviation);
		// 视频显示驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL, configEntity.videoShowDriver);
		// 音频播放驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_PLAYDRVCTRL, configEntity.audioPlayDriver);
		// 音频采集驱动设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_RECORDDRVCTRL, configEntity.audioRecordDriver);
		// 视频GPU渲染设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER, configEntity.videoShowGPURender);
		// 本地视频自动旋转设置
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, configEntity.videoAutoRotation);
	}
}

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
		tv.setText("���ڽ���...");
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

			Toast.makeText(this, "������Ƶͨ����,���Ժ�..." , Toast.LENGTH_LONG)
			.show();
			
			Intent intent = new Intent(EnterRoomBuffActivity.this, MainActivity.class);
			startActivity(intent);
			EnterRoomBuffActivity.this.finish();
			
		  	try {
		  		anychat.Release();	// �ر�SDK�����ٷ��ص�¼����
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
	/*	Toast.makeText(this, "���ӹرգ�error��" + dwErrorCode, Toast.LENGTH_SHORT)
				.show();*/
		Toast.makeText(this, "������Ƶͨ����,���Ժ�..." , Toast.LENGTH_LONG)
		.show();
		
		
		Intent intent = new Intent(EnterRoomBuffActivity.this, MainActivity.class);
		startActivity(intent);
		EnterRoomBuffActivity.this.finish();
		
	  	try {
	  		anychat.Release();	// �ر�SDK�����ٷ��ص�¼����
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
			
			Toast.makeText(this, "������Ƶͨ����,���Ժ�..." , Toast.LENGTH_SHORT).show();

			/*Toast.makeText(this, "��¼ʧ�ܣ�������룺" + dwErrorCode, Toast.LENGTH_SHORT)
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
	    	anychat.Release();	// �ر�SDK�����ٷ��ص�¼����
			System.out.println("Release()");

		}
		super.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ������д���ؼ�
			
			Intent intent = new Intent(EnterRoomBuffActivity.this,MainActivity.class
					);
			startActivity(intent);
			this.finish();
			
			return true;
		}
		
		return false;

	}
	// ���������ļ�������Ƶ����
	private void ApplyVideoConfig()
	{
		ConfigEntity configEntity = ConfigService.LoadConfig(this);
		if(configEntity.configMode == 1)		// �Զ�����Ƶ��������
		{
			// ���ñ�����Ƶ��������ʣ��������Ϊ0�����ʾʹ����������ģʽ��
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_BITRATECTRL, configEntity.videoBitrate);
			if(configEntity.videoBitrate==0)
			{
				// ���ñ�����Ƶ���������
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_QUALITYCTRL, configEntity.videoQuality);
			}
			// ���ñ�����Ƶ�����֡��
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FPSCTRL, configEntity.videoFps);
			// ���ñ�����Ƶ����Ĺؼ�֡���
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_GOPCTRL, configEntity.videoFps*4);
			// ���ñ�����Ƶ�ɼ��ֱ���
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_WIDTHCTRL, configEntity.resolution_width);
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_HEIGHTCTRL, configEntity.resolution_height);
			// ������Ƶ����Ԥ�������ֵԽ�󣬱�������Խ�ߣ�ռ��CPU��ԴҲ��Խ�ߣ�
			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_PRESETCTRL, configEntity.videoPreset);
		}
		// ����Ƶ������Ч
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_APPLYPARAM, configEntity.configMode);
		// P2P����
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_NETWORK_P2PPOLITIC, configEntity.enableP2P);
		// ������ƵOverlayģʽ����
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_OVERLAY, configEntity.videoOverlay);
		// ������������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_ECHOCTRL, configEntity.enableAEC);
		// ƽ̨Ӳ����������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_USEHWCODEC, configEntity.useHWCodec);
		// ��Ƶ��תģʽ����
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_ROTATECTRL, configEntity.videorotatemode);
		// ��Ƶ�ɼ���������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER, configEntity.videoCapDriver);
		// ������Ƶ�ɼ�ƫɫ��������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_FIXCOLORDEVIA, configEntity.fixcolordeviation);
		// ��Ƶ��ʾ��������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL, configEntity.videoShowDriver);
		// ��Ƶ������������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_PLAYDRVCTRL, configEntity.audioPlayDriver);
		// ��Ƶ�ɼ���������
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_AUDIO_RECORDDRVCTRL, configEntity.audioRecordDriver);
		// ��ƵGPU��Ⱦ����
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_GPUDIRECTRENDER, configEntity.videoShowGPURender);
		// ������Ƶ�Զ���ת����
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, configEntity.videoAutoRotation);
	}
}

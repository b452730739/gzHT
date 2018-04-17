package com.elegps.vedio;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.elegps.gz_customerservice.R;

public class LiveVideoActivity extends Activity implements AnyChatBaseEvent {
	private SurfaceView videoView;
	ProgressBar volumeProgressBar;
	private ImageView mCameraSwitchImage; // ǰ������ͷ�л���ť

	public AnyChatCoreSDK anychat;
	int userID = -1;
	int otherVideoIndex = -1; // ��Ƶ��ʾ��ţ�Java��Ƶ��ʾ������Ҫ��
	private ConfigEntity configEntity;

	private boolean bVideoOpened = false; // ��Ƶ�Ƿ��Ѵ�
	private boolean bSuccessEnterRoom = false; // �Ƿ��ѳɹ����뷿��

	private final int WORK_MODE_DOWNSTREAM = 1; // ���ع���ģʽ
	private final int WORK_MODE_UPSTREAM = 2; // �ϴ�����ģʽ
	private int iWorkMode;
	private boolean bOnPaused = false;

	private Timer mTimer = new Timer(true);
	private TimerTask mTimerTask;
	private Handler handler = null;

	private Button quitBtn;
	private boolean bShowQuitBtn = true; // �Ƿ�����ʾ�˳���ť
	private Date mLastShowQuitBtnTime = new Date(); // ��ʼ��ʾ�˳���ť��ʱ��

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configEntity = ConfigService.LoadConfig(this);
		iWorkMode = getIntent().getIntExtra("mode", 0);
		if (iWorkMode == WORK_MODE_UPSTREAM) // ������ϴ�ģʽ��ֻ���Լ�����Ƶ
		{
			userID = -1;
		}

		InitialSDK();
		InitialLayout();

		mTimerTask = new TimerTask() {
			public void run() {
				if (handler == null)
					return;
				Message mesasge = new Message();
				handler.sendMessage(mesasge);
			}
		};
		mTimer.schedule(mTimerTask, 1000, 100);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				CheckVideoStatus();
				// ˢ��������
				if (LiveVideoActivity.this.bSuccessEnterRoom)
					LiveVideoActivity.this.volumeProgressBar
							.setProgress(LiveVideoActivity.this.anychat
									.GetUserSpeakVolume(userID));
				// �ж��Ƿ���Ҫ�����˳���ť
				Date now = new Date();
				long interval = now.getTime()
						- LiveVideoActivity.this.mLastShowQuitBtnTime.getTime();
				if (LiveVideoActivity.this.bShowQuitBtn && (interval > 2000)) {
					LiveVideoActivity.this.bShowQuitBtn = false;
					quitBtn.setVisibility(View.GONE);
				}
				super.handleMessage(msg);
			}
		};
	}

	// �ж���Ƶ��״̬�����û�д򿪣�����һ���ʺϵ��û�
	private void CheckVideoStatus() {
		if (bVideoOpened || !bSuccessEnterRoom || bOnPaused)
			return;
		if (iWorkMode == WORK_MODE_DOWNSTREAM) // �������˵���Ƶ
		{
			int[] userarray = anychat.GetOnlineUser();
			for (int i = 0; i < userarray.length; i++) {
				int uid = userarray[i];
				if (anychat.GetCameraState(uid) == 2
						&& anychat.GetUserVideoWidth(uid) != 0) {
					userID = uid;
					SurfaceHolder holder = videoView.getHolder();
					holder.setFormat(PixelFormat.RGB_565);
					holder.setFixedSize(anychat.GetUserVideoWidth(userID),
							anychat.GetUserVideoHeight(userID));
					Surface s = holder.getSurface();
					anychat.SetVideoPos(userID, s, 0, 0, 0, 0);
					anychat.UserCameraControl(userID, 1);
					anychat.UserSpeakControl(userID, 1);
					bVideoOpened = true;

					// ����ǲ���Java��Ƶ��ʾ������Ҫ����Ƶ�������û���������
					if (AnyChatCoreSDK
							.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
						anychat.mVideoHelper.SetVideoUser(otherVideoIndex,
								userID);
					}
					break;
				}
			}
		} else // �򿪱��ص���Ƶ�����뷿��ɹ��Ѿ������˴򿪲��������ֻ��Ҫ������ʾ
		{
			if (anychat.GetCameraState(userID) == 2
					&& anychat.GetUserVideoWidth(userID) != 0) {
				SurfaceHolder holder = videoView.getHolder();
				holder.setFormat(PixelFormat.RGB_565);
				holder.setFixedSize(anychat.GetUserVideoWidth(userID),
						anychat.GetUserVideoHeight(userID));
				Surface s = holder.getSurface();
				anychat.SetVideoPos(userID, s, 0, 0, 0, 0);
				bVideoOpened = true;
			}
		}

	}

	private void InitialSDK() {
		anychat = new AnyChatCoreSDK();
		anychat.SetBaseEvent(this);

		// ����AnyChat����������
		anychat.mSensorHelper.InitSensor(this);

		int RoomID = getIntent().getIntExtra("RoomID", 0);
		anychat.EnterRoom(RoomID, "");
	}

	private void InitialLayout() {
		this.setContentView(R.layout.live_video_room);
		if (iWorkMode == WORK_MODE_UPSTREAM)
			this.setTitle("������Ƶ�ϴ�");
		else
			this.setTitle("ʵʱ��ý��ֱ��");
		videoView = (SurfaceView) findViewById(R.id.surface_video);
		SurfaceHolder holder = videoView.getHolder();
		holder.setKeepScreenOn(true);
		videoView.setOnClickListener(listener);
		if (iWorkMode == WORK_MODE_UPSTREAM && configEntity.videoOverlay == 1)
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		if (iWorkMode == WORK_MODE_UPSTREAM) {
			// ����ǲ���Java��Ƶ�ɼ�������Ҫ����Surface��CallBack
			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA)
				videoView.getHolder().addCallback(AnyChatCoreSDK.mCameraHelper);
		} else {
			// ����ǲ���Java��Ƶ��ʾ������Ҫ����Surface��CallBack
			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
				otherVideoIndex = anychat.mVideoHelper.bindVideo(videoView
						.getHolder());
			}
		}

		mCameraSwitchImage = (ImageView) findViewById(R.id.image_switch_camera);
		mCameraSwitchImage.setOnClickListener(listener);
		quitBtn = (Button) findViewById(R.id.btn_back);
		quitBtn.setOnClickListener(listener);
		volumeProgressBar = (ProgressBar) findViewById(R.id.progress_volume);

		// �ж��Ƿ���ʾ��������ͷ�л�ͼ��
		if (iWorkMode == WORK_MODE_UPSTREAM) {
			if (AnyChatCoreSDK
					.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
				if (AnyChatCoreSDK.mCameraHelper.GetCameraNumber() > 1)
					mCameraSwitchImage.setVisibility(View.VISIBLE);
			} else {
				String[] strVideoCaptures = anychat.EnumVideoCapture();
				if (strVideoCaptures != null && strVideoCaptures.length > 1)
					mCameraSwitchImage.setVisibility(View.VISIBLE);
			}
		}
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			if (v == quitBtn) {
				mTimer.cancel();
				if (bVideoOpened) {
					anychat.UserCameraControl(userID, 0);
					anychat.UserSpeakControl(userID, 0);
					bVideoOpened = false;
				}
				finish();
			} else if (v == videoView) {
				bShowQuitBtn = true;
				mLastShowQuitBtnTime = new Date();
				quitBtn.setVisibility(View.VISIBLE);
			} else if (v == mCameraSwitchImage) // ǰ������ͷ�л��¼�
			{
				// ����ǲ���Java��Ƶ�ɼ�������Java���������ͷ�л�
				if (AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
					AnyChatCoreSDK.mCameraHelper.SwitchCamera();
					return;
				}

				String strVideoCaptures[] = anychat.EnumVideoCapture();
				if (strVideoCaptures == null)
					return;
				String temp = anychat.GetCurVideoCapture();
				for (int i = 0; i < strVideoCaptures.length; i++) {
					if (!temp.equals(strVideoCaptures[i])) {
						anychat.UserCameraControl(-1, 0);
						bVideoOpened = false;
						anychat.SelectVideoCapture(strVideoCaptures[i]);
						anychat.UserCameraControl(-1, 1);
						break;
					}
				}
			}
		}
	};

	protected void onRestart() {
		super.onRestart();
		anychat.UserCameraControl(userID, 1);
		anychat.UserSpeakControl(userID, 1);
		bOnPaused = false;
	}

	protected void onResume() {
		super.onResume();

	}

	protected void onPause() {
		super.onPause();
		bOnPaused = true;
		if (bVideoOpened) {
			anychat.UserCameraControl(userID, 0);
			anychat.UserSpeakControl(userID, 0);
			bVideoOpened = false;

		}
	}

	protected void onDestroy() {
		mTimer.cancel();
		anychat.LeaveRoom(-1);
		anychat.mSensorHelper.DestroySensor();
		super.onDestroy();
		finish();
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			bSuccessEnterRoom = true;
			if (iWorkMode == WORK_MODE_UPSTREAM) {
				anychat.UserCameraControl(userID, 1);
				anychat.UserSpeakControl(userID, 1);
			}
		}
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// �������ӶϿ�֮���ϲ���Ҫ�����ر��Ѿ��򿪵�����Ƶ�豸
		if (bVideoOpened) {
			if (iWorkMode == WORK_MODE_DOWNSTREAM) {
				anychat.UserCameraControl(userID, 0);
				anychat.UserSpeakControl(userID, 0);
			} else {
				anychat.UserCameraControl(-1, 0);
				anychat.UserSpeakControl(-1, 0);
			}
			bVideoOpened = false;
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {

	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {

	}
}

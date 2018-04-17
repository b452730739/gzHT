package com.elegps.vedio;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Spinner;

public class VideoConfigActivity extends Activity{
	private LinearLayout fullLayout;
	private LinearLayout mainLayout;
	private Button saveBtn;
	private ConfigEntity configEntity;
	
	private RadioGroup configModeRadioGroup;		// ����ģʽ��
	
	RadioButton configModeServerBtn;				// ����������
	RadioButton configModeCustomBtn;				// �Զ�������
	
	TextView resolutionLable;
	
	private CheckBox enableP2PBox;
	private CheckBox videoOverlayBox;
	private CheckBox useARMv6Box;
	private CheckBox useAECBox;
	private CheckBox useHWCodecBox;
	private CheckBox videoRotateBox;
	private CheckBox fixColorDeviation;
	private CheckBox videoShowGPURender;
	private CheckBox videoAutoRotation;
	private Spinner videoSizeSpinner;
	private Spinner videoBitrateSpinner;
	private Spinner videoFPSSpinner;
	private Spinner videoQualitySpinner;
	private Spinner videoPresetSpinner;
	private Spinner videoShowDriverSpinner;
	private Spinner audioPlayDriverSpinner;
	private Spinner audioRecordDriverSpinner;
	private Spinner videoCapDriverSpinner;

	private final String[] videoSizeString={"176 x 144", "320 x 240��Ĭ�ϣ�", "352 x 288", "640 x 480", "720 x 480", "1280 x 720"};
	private final int[] videoWidthValue={176,320,352,640, 720, 1280};
	private final int[] videoHeightValue={144,240,288,480, 480, 720};
	
	private final String[] videoBitrateString={"��������ģʽ", "60kbps��Ĭ�ϣ�", "80kbps", "100kbps", "150kbps", "200kbps", "300kbps", "500kbps", "800kbps", "1Mbps"};
	private final int[]	videoBitrateValue={0,60*1000,80*1000,100*1000,150*1000,200*1000,300*1000,500*1000,800*1000,1000*1000};
	
	private final String[] videofpsString={"2 FPS", "4 FPS", "6 FPS", "8 FPS", "10FPS��Ĭ�ϣ�", "15FPS", "20FPS", "25FPS"};
	private final int[]	videofpsValue={2,4,6,8,10,15,20,25};
	
	private final String[] videoQualityString={"��ͨ��Ƶ����", "�е���Ƶ������Ĭ�ϣ�", "�Ϻ���Ƶ����"};
	private final int[] videoQualityValue={2,3,4};
	
	private final String[] videoPresetString={"���Ч�ʣ��ϵ�����","�ϸ�Ч�ʣ��ϵ�����","���ܾ��⣨Ĭ�ϣ�","�ϸ��������ϵ�Ч��","����������ϵ�Ч��"};
	private final int[] videoPresetValue={1,2,3,4,5};
	
	private final String[] videoShowDriverString={"�ں���Ƶ��ʾ����", "Android 2.x����ģʽ", "Java��Ƶ��ʾ����"};
	private final int[]	videoShowDriverValue={0,4,5};
	
	private final String[] audioPlayDriverString={"�ں���Ƶ��������", "Java��Ƶ��������"};
	private final int[] audioPlayDriverValue={0,3};
	
	private final String[] audioRecordDriverString={"�ں���Ƶ�ɼ�����", "Java��Ƶ�ɼ�����"};
	private final int[] audioRecordDriverValue={0,3};	
	
	private final String[] videoCapDriverString={"�ں���Ƶ�ɼ�����", "Video4Linux����", "Java��Ƶ�ɼ�����"};
	private final int[] videoCapDriverValue={0,1,3};	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configEntity = ConfigService.LoadConfig(this);
        getScrennInfo();
        InitialLayout();
    }
    private void getScrennInfo()
    {
    	DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		ScreenInfo.WIDTH=dMetrics.widthPixels;
		ScreenInfo.HEIGHT=dMetrics.heightPixels;
    }
    private void InitialLayout()
    {   
        this.setTitle("����");
    	fullLayout =  new LinearLayout(this);
    	fullLayout.setBackgroundColor(Color.WHITE);
    	fullLayout.setOrientation(LinearLayout.VERTICAL);
    	fullLayout.setOnTouchListener(touchListener);
	    
    	
    	mainLayout =  new LinearLayout(this);
    	mainLayout.setBackgroundColor(Color.WHITE);
	    mainLayout.setOrientation(LinearLayout.VERTICAL);
	    mainLayout.setOnTouchListener(touchListener);
	    
	    enableP2PBox = new CheckBox(this);
	    enableP2PBox.setText("����P2P��������");
	    enableP2PBox.setTextColor(Color.BLACK);
	    mainLayout.addView(enableP2PBox,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    enableP2PBox.setChecked(configEntity.enableP2P != 0);
	    
	    videoOverlayBox = new CheckBox(this);
	    videoOverlayBox.setText("Overlay��Ƶģʽ");
	    videoOverlayBox.setTextColor(Color.BLACK);
	    mainLayout.addView(videoOverlayBox,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    videoOverlayBox.setChecked(configEntity.videoOverlay != 0);  
	    
	    videoRotateBox = new CheckBox(this);
	    videoRotateBox.setText("��ת��Ƶ");
	    videoRotateBox.setTextColor(Color.BLACK);
	    mainLayout.addView(videoRotateBox,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    videoRotateBox.setChecked(configEntity.videorotatemode != 0); 
	    
	    fixColorDeviation = new CheckBox(this);
	    fixColorDeviation.setText("������Ƶ�ɼ�ƫɫ����");
	    fixColorDeviation.setTextColor(Color.BLACK);
	    mainLayout.addView(fixColorDeviation,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    fixColorDeviation.setChecked(configEntity.fixcolordeviation!=0);  
	    
	    videoShowGPURender = new CheckBox(this);
	    videoShowGPURender.setText("������ƵGPU��Ⱦ");
	    videoShowGPURender.setTextColor(Color.BLACK);
	    mainLayout.addView(videoShowGPURender,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    videoShowGPURender.setChecked(configEntity.videoShowGPURender!=0); 	    
	    
	    videoAutoRotation = new CheckBox(this);
	    videoAutoRotation.setText("������Ƶ�����豸�Զ���ת");
	    videoAutoRotation.setTextColor(Color.BLACK);
	    mainLayout.addView(videoAutoRotation,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    videoAutoRotation.setChecked(configEntity.videoAutoRotation != 0);  	    
	    
	    useARMv6Box = new CheckBox(this);
	    useARMv6Box.setText("ǿ��ʹ��ARMv6ָ�����ȫģʽ��");
	    useARMv6Box.setTextColor(Color.BLACK);
	    mainLayout.addView(useARMv6Box,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    useARMv6Box.setChecked(configEntity.useARMv6Lib != 0); 
	    
	    useAECBox = new CheckBox(this);
	    useAECBox.setText("���û���������AEC��");
	    useAECBox.setTextColor(Color.BLACK);
	    mainLayout.addView(useAECBox,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    useAECBox.setChecked(configEntity.enableAEC != 0); 
	    
	    useHWCodecBox = new CheckBox(this);
	    useHWCodecBox.setText("����ƽ̨����Ӳ������루������Ӧ�ó���");
	    useHWCodecBox.setTextColor(Color.BLACK);
	    mainLayout.addView(useHWCodecBox,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	    useHWCodecBox.setChecked(configEntity.useHWCodec != 0);
	    
    	// ������Ƶ������������
    	audioPlayDriverSpinner = InsertSpinnerInterface("��Ƶ��������", audioPlayDriverString, audioPlayDriverValue, configEntity.audioPlayDriver);
    	// ������Ƶ�ɼ���������
    	audioRecordDriverSpinner = InsertSpinnerInterface("��Ƶ�ɼ�����", audioRecordDriverString, audioRecordDriverValue, configEntity.audioRecordDriver);
     	// ������Ƶ�ɼ���������
    	videoCapDriverSpinner = InsertSpinnerInterface("��Ƶ�ɼ�����", videoCapDriverString, videoCapDriverValue, configEntity.videoCapDriver);
    	//������Ƶ��ʾ��������
	    videoShowDriverSpinner = InsertSpinnerInterface("��Ƶ��ʾ����", videoShowDriverString, videoShowDriverValue, configEntity.videoShowDriver);
	    // ��������ģʽѡ����
    	TextView configModeLable = new TextView(this);
    	configModeLable.setTextColor(Color.BLACK);
    	configModeLable.setText("ѡ������ģʽ��");
    	mainLayout.addView(configModeLable,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    	
       	configModeRadioGroup = new RadioGroup(this);
    	configModeServerBtn = new RadioButton(this);
    	configModeCustomBtn = new RadioButton(this);
       	configModeServerBtn.setTextColor(Color.BLACK);
    	configModeCustomBtn.setTextColor(Color.BLACK);
    	configModeServerBtn.setText("���������ò���");
    	configModeCustomBtn.setText("�Զ������ò���");
    	configModeRadioGroup.addView(configModeServerBtn);
    	configModeRadioGroup.addView(configModeCustomBtn);
    	configModeServerBtn.setOnClickListener(listener);
    	configModeCustomBtn.setOnClickListener(listener);

    	if(configEntity.configMode == ConfigEntity.VIDEO_MODE_SERVERCONFIG)
    		configModeServerBtn.setChecked(true);
    	else
    		configModeCustomBtn.setChecked(true);
    	
    	mainLayout.addView(configModeRadioGroup,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    	    	
    	//������Ƶ�ֱ���
    	resolutionLable = new TextView(this);
    	resolutionLable.setTextColor(Color.BLACK);
    	resolutionLable.setText("ѡ����Ƶ�ֱ��ʣ�");
    	mainLayout.addView(resolutionLable,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    	
    	
    	videoSizeSpinner = new Spinner(this);
    	LinearLayout.LayoutParams videoSizeLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	videoSizeLP.gravity = Gravity.RIGHT;
    	mainLayout.addView(videoSizeSpinner,videoSizeLP);
    	ArrayAdapter<String> videoSizeAdapter; 
    	videoSizeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,videoSizeString);  
    	videoSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
    	videoSizeSpinner.setAdapter(videoSizeAdapter);  
    	videoSizeSpinner.setVisibility(View.VISIBLE);
    	int iSelectVideoSize = 0;
    	for(int i=0; i<videoWidthValue.length; i++)
    	{
    		if(videoWidthValue[i] == configEntity.resolution_width)
    		{
    			iSelectVideoSize = i;
    			break;
    		}
    	}
    	videoSizeSpinner.setSelection(iSelectVideoSize);

    	//��������
    	videoBitrateSpinner = InsertSpinnerInterface("ѡ����Ƶ����", videoBitrateString, videoBitrateValue, configEntity.videoBitrate);
    	//����֡��
    	videoFPSSpinner = InsertSpinnerInterface("ѡ����Ƶ֡��", videofpsString, videofpsValue, configEntity.videoFps); 	
    	//������Ƶ����
    	videoQualitySpinner = InsertSpinnerInterface("ѡ����Ƶ����", videoQualityString, videoQualityValue, configEntity.videoQuality);
    	// ������ƵԤ�����
    	videoPresetSpinner = InsertSpinnerInterface("ѡ����ƵԤ�����", videoPresetString, videoPresetValue, configEntity.videoPreset);
    	
    	// ��������ģʽ��ȷ���Ƿ���Ҫ��ʾ�Զ����������
    	CustomControlsShow(configEntity.configMode == 0 ? false : true);
    	
    	//����ײ���ť
    	LinearLayout btnLayout =  new LinearLayout(this);
    	btnLayout.setOrientation(LinearLayout.HORIZONTAL);
    	
    	saveBtn = new Button(this);
    	saveBtn.setText("��������");
    	btnLayout.addView(saveBtn,new LayoutParams(ScreenInfo.WIDTH,LayoutParams.WRAP_CONTENT));
    	saveBtn.setOnClickListener(listener);

    	
        ScrollView sv = new ScrollView(this);
        sv.addView(mainLayout);
    	fullLayout.addView(sv,new LayoutParams(ScreenInfo.WIDTH,ScreenInfo.HEIGHT*8/10));
    	
    	fullLayout.addView(btnLayout,new LayoutParams(ScreenInfo.WIDTH,ScreenInfo.HEIGHT/10));
    	this.setContentView(fullLayout);

    }
    
    OnClickListener listener = new OnClickListener()
    {       
		public void onClick(View v) 
		{
			if(v == saveBtn)
			{
				SaveConfig();
			}
			else if(v == configModeServerBtn)
			{
				CustomControlsShow(false);
			}
			else if(v == configModeCustomBtn)
			{
				CustomControlsShow(true);
			}
		}
    };
    
    private void CustomControlsShow(boolean bEnable)
    {
    	videoSizeSpinner.setEnabled(bEnable);
		videoBitrateSpinner.setEnabled(bEnable);
		videoFPSSpinner.setEnabled(bEnable);
		videoQualitySpinner.setEnabled(bEnable);
		videoPresetSpinner.setEnabled(bEnable);
    }
    
    private void SaveConfig()
    {
    	configEntity.configMode = configModeServerBtn.isChecked() ? ConfigEntity.VIDEO_MODE_SERVERCONFIG : ConfigEntity.VIDEO_MODE_CUSTOMCONFIG;
    	
    	configEntity.resolution_width = videoWidthValue[videoSizeSpinner.getSelectedItemPosition()];
    	configEntity.resolution_height = videoHeightValue[videoSizeSpinner.getSelectedItemPosition()];
    	configEntity.videoBitrate = videoBitrateValue[videoBitrateSpinner.getSelectedItemPosition()];
    	configEntity.videoFps = videofpsValue[videoFPSSpinner.getSelectedItemPosition()];    	
    	configEntity.videoQuality = videoQualityValue[videoQualitySpinner.getSelectedItemPosition()];  
    	configEntity.videoPreset = videoPresetValue[videoPresetSpinner.getSelectedItemPosition()]; 

    	configEntity.videoOverlay = videoOverlayBox.isChecked() ? 1 : 0;
    	configEntity.videorotatemode = videoRotateBox.isChecked() ? 1 : 0;
    	configEntity.fixcolordeviation = fixColorDeviation.isChecked() ? 1 : 0;
    	configEntity.videoShowGPURender = videoShowGPURender.isChecked() ? 1 : 0;
    	if(configEntity.videoCapDriver==1)			// Video4Linux������֧��Overlayģʽ
    		configEntity.videoOverlay = 0;

    	configEntity.videoAutoRotation = videoAutoRotation.isChecked() ? 1 : 0;
    	
    	configEntity.enableP2P = enableP2PBox.isChecked() ? 1 : 0;
    	configEntity.useARMv6Lib = useARMv6Box.isChecked() ? 1 : 0;
    	configEntity.enableAEC = useAECBox.isChecked() ? 1 : 0;
     	configEntity.useHWCodec = useHWCodecBox.isChecked() ? 1 : 0;
     	configEntity.videoShowDriver = videoShowDriverValue[videoShowDriverSpinner.getSelectedItemPosition()];
     	configEntity.audioPlayDriver = audioPlayDriverValue[audioPlayDriverSpinner.getSelectedItemPosition()];     	
     	configEntity.audioRecordDriver = audioRecordDriverValue[audioRecordDriverSpinner.getSelectedItemPosition()];
     	configEntity.videoCapDriver = videoCapDriverValue[videoCapDriverSpinner.getSelectedItemPosition()];     	
    	ConfigService.SaveConfig(this, configEntity);
		
		this.setResult(RESULT_OK);
		this.finish();
    }
    
    
    OnTouchListener touchListener =  new OnTouchListener()
    {
		@Override
		public boolean onTouch(View v, MotionEvent e) {
			// TODO Auto-generated method stub
	        switch (e.getAction()) 
	        {
	    		case MotionEvent.ACTION_DOWN:
	    			try
	    			{
	    				((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(VideoConfigActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
	    			}
	    			catch(Exception excp)
	    			{
	    				
	    			}
	    			break;
	    		case MotionEvent.ACTION_UP:

	    			break;
	        }
	        return false;
		}
    };
    
    private Spinner InsertSpinnerInterface(String caption, String[] context, int[] value, int select) {
    	TextView lable = new TextView(this);
    	lable.setTextColor(Color.BLACK);
    	lable.setText(caption+"��");
    	mainLayout.addView(lable,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
    	
    	Spinner spinner = new Spinner(this);
    	LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	LP.gravity = Gravity.RIGHT;
    	mainLayout.addView(spinner,LP);
    	ArrayAdapter<String> Adapter; 
    	Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, context);  
    	Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
    	spinner.setAdapter(Adapter);  
    	spinner.setVisibility(View.VISIBLE);
    	int offset = 0;
    	for(int i=0; i<value.length; i++)
    	{
    		if(value[i] == select)
    		{
    			offset = i;
    			break;
    		}
    	}
    	spinner.setSelection(offset);
    	return spinner;
    }

}

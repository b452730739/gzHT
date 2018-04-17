package com.elegps.getLocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.UIManager.Toast_Creat;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.Hellper;
import com.elegps.help.PublicWay;
import com.elegps.photo.Photo_NativeActivity;
/**
 * ��demo����չʾ��ν�϶�λSDKʵ�ֶ�λ����ʹ��MyLocationOverlay���ƶ�λλ��
 * ͬʱչʾ���ʹ���Զ���ͼ����Ʋ����ʱ��������
 *
 */
public class LocationOverlayDemo extends Activity {
	
	// ��λ���
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	private Button jietu = null;
	//��λͼ��
	locationOverlay myLocationOverlay = null;
	//��������ͼ��
	private PopupOverlay   pop  = null;//��������ͼ�㣬����ڵ�ʱʹ��
	private TextView  popupText = null;//����view
	private View viewCache = null;
	MKMapViewListener mMapListener = null;
	/**
	 * ���ڽػ�������
	 */
	//��ͼ��أ�ʹ�ü̳�MapView��MyLocationMapViewĿ������дtouch�¼�ʵ�����ݴ���
	//���������touch�¼���������̳У�ֱ��ʹ��MapView����
	MyLocationMapView mMapView = null;	// ��ͼView
	private MapController mMapController = null;
	private Dialog_UI dialog_UI = null;

	//UI���	
	OnCheckedChangeListener radioButtonListener = null;
	Button requestLocButton = null;
	boolean isRequest = false;//�Ƿ��ֶ���������λ
	boolean isFirstLoc = true;//�Ƿ��״ζ�λ
	private String ImageTemp = null;
    private String temp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

    	if(new Hellper().getNetworkIsAvailable(LocationOverlayDemo.this)){
     		
        if(isWifi(LocationOverlayDemo.this)){
            init();
        }else{
        	
        	new AlertDialog.Builder(this).setTitle("�����粻��wifi����").setIcon(
   			     android.R.drawable.ic_dialog_info).setPositiveButton("�˳�",
   			    		 new DialogInterface.OnClickListener() {
   							
   							@Override
   							public void onClick(DialogInterface dialog, int which) {
   								
   								Intent intent = new Intent(
   								LocationOverlayDemo.this, 
   								MainActivity.class);
   								startActivity(intent);
   								LocationOverlayDemo.this.finish();
   							}
   						})
   			     .setNegativeButton("����", 	 new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					}).show();
        			
        			init();

        }
    	}else{
			Toast.makeText(LocationOverlayDemo.this, R.string.networkeerror, 0).show();
		}
    }
    private void init(){  
        
        setContentView(R.layout.activity_locationoverlay);
        CharSequence titleLable="��λ����";
        setTitle(titleLable);
		PublicWay.activityList.add(this);

        requestLocButton = (Button)findViewById(R.id.button1);
        OnClickListener btnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�ֶ���λ����
				requestLocClick();
			}
		};
	    requestLocButton.setOnClickListener(btnClickListener);
	    
      /*  RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
        radioButtonListener = new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//
				if (checkedId == R.id.defaulticon){
					//����null�򣬻ָ�Ĭ��ͼ��
					modifyLocationOverlayIcon(null);
				}
				if (checkedId == R.id.customicon){
					//�޸�Ϊ�Զ���marker
					modifyLocationOverlayIcon(getResources().getDrawable(R.drawable.icon_geo));
				}
			}
		};
		group.setOnCheckedChangeListener(radioButtonListener);
        */
		//��ͼ��ʼ��
        mMapView = (MyLocationMapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
        
        dialog_UI = new Dialog_UI(LocationOverlayDemo.this, "�����ϴ�,���Ժ�...");
        dialog_UI.setCancelable(false);
      //���� ��������ͼ��
        createPaopao();
        mMapListener = new MKMapViewListener() {


			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ����
				 *  ���ڴ˱����ͼ���洢�豸
				 */
				File file = new File(Constant.jietuPath);
                FileOutputStream out;
                try{
                    out = new FileOutputStream(file);
                    if(b.compress(Bitmap.CompressFormat.PNG, 70, out)) 
                    {
                        out.flush();
                        out.close();
                    }
            /*        Toast.makeText(LocationOverlayDemo.this, 
                    	    "��Ļ��ͼ�ɹ���ͼƬ����: "+file.toString(),	
                    		 Toast.LENGTH_SHORT)
                         .show();*/
                } 
                catch (FileNotFoundException e) 
                {
                    e.printStackTrace();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace(); 
                }
                new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
			              try {

			        			ImageTemp =  new Hellper().bitmaptoString(
			    						new Hellper().readInputStream(Constant.jietuPath),
			    						LocationOverlayDemo.this);
			    			} catch (Exception e) {
			    				e.printStackTrace();
			    			}
			    			
			    			try {
			    				temp = ContentWeb.getinstance().contetweb(
			    			
			    						 IP_Address.PICTURESERVICE,				
			    						 "UploadMapPicture", 
			    						new String[]{
			    								"strAccount","strPicture"},
			    						new String[]{
			    								 Constant.UserName,ImageTemp},null);
			    			} catch (Exception e) {
			    				e.printStackTrace();
								Toast.makeText(LocationOverlayDemo.this, "�ϴ�ʧ��", 0).show();
	
			    			}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						dialog_UI.dismiss();
						//Toast.makeText(LocationOverlayDemo.this, temp, 0).show();
		    			
						new Toast_Creat(LocationOverlayDemo.this, temp, 4000).show_toast();

		    			Intent intent = new Intent(LocationOverlayDemo.this, MainActivity.class);
		    			startActivity(intent);
		    			LocationOverlayDemo.this.finish();
					}
					
				}.execute((Void)null);
  
    
           /*     Intent intent = new Intent(
                		LocationOverlayDemo.this,
                		ZhuYeActivity.class);
                startActivity(intent);*/
			}


			@Override
			public void onMapLoadFinish() {
				
			}


			@Override
			public void onClickMapPoi(MapPoi arg0) {
				
			}


			@Override
			public void onMapAnimationFinish() {
				
			}


			@Override
			public void onMapMoveFinish() {
				
			}
		};
		mMapView.regMapViewListener(DemoApplication.getInstance().mBMapManager, mMapListener);

        jietu = (Button)findViewById(R.id.jietu);	
        jietu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if("1".equals(Constant.ISINSIDE)){
					
					//��ͼ����MKMapViewListener�б���ͼƬ
	                dialog_UI.show();

				     mMapView.getCurrentMap();	
				/*     Toast.makeText(LocationOverlayDemo.this, 
				    		 "���ڽ�ͼ�ϴ�...", 
				    		 Toast.LENGTH_LONG ).show();*/
				}else{
					
				     Toast.makeText(LocationOverlayDemo.this, 
				    		 "�����˺�Ϊ�ⲿ�˺�,���ϴ�Ȩ��...", 
				    		 Toast.LENGTH_SHORT ).show();
				}
				
			          
			}
		});
        //��λ��ʼ��
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//��gps
        option.setCoorType("bd09ll");     //������������
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //��λͼ���ʼ��
		myLocationOverlay = new locationOverlay(mMapView);
		//���ö�λ����
	    myLocationOverlay.setData(locData);
	    //��Ӷ�λͼ��
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//�޸Ķ�λ���ݺ�ˢ��ͼ����Ч
		mMapView.refresh();
		
    
    }
	private static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
    /**
     * �ֶ�����һ�ζ�λ����
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(LocationOverlayDemo.this, "���ڶ�λ����", Toast.LENGTH_SHORT).show();
    }
    /**
     * �޸�λ��ͼ��
     * @param marker
     */
    public void modifyLocationOverlayIcon(Drawable marker){
    	//������markerΪnullʱ��ʹ��Ĭ��ͼ�����
    	myLocationOverlay.setMarker(marker);
    	//�޸�ͼ�㣬��Ҫˢ��MapView��Ч
    	mMapView.refresh();
    }
    /**
	 * ������������ͼ��
	 */
	public void createPaopao(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        //���ݵ����Ӧ�ص�
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
        MyLocationMapView.pop = pop;
	}
	/**
     * ��λSDK��������
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //�������ʾ��λ����Ȧ����accuracy��ֵΪ0����
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            //���¶�λ����
            myLocationOverlay.setData(locData);
            //����ͼ������ִ��ˢ�º���Ч
            mMapView.refresh();
            //���ֶ�����������״ζ�λʱ���ƶ�����λ��
            if (isRequest || isFirstLoc){
            	//�ƶ���ͼ����λ��
                try {
					mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                isRequest = false;
            }
            //�״ζ�λ���
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    //�̳�MyLocationOverlay��дdispatchTapʵ�ֵ������
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		@Override
  		protected boolean dispatchTap() {
  			//�������¼�,��������
  			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("�ҵ�λ��");
			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
					8);
  			return true;
  		}
  		
  	}

    @Override
    protected void onPause() {
        try {
			mMapView.onPause();
		} catch (Exception e) {
			e.printStackTrace();
		}
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        try {
			mMapView.onResume();
		} catch (Exception e) {
			e.printStackTrace();
		}
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
    	System.out.println("onDestroy()");
    	try {
			//�˳�ʱ���ٶ�λ
			if (mLocClient != null)
			    mLocClient.stop();
			mMapView.destroy();
			mMapListener = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// ������д���ؼ�
			
			Intent intent = new Intent(LocationOverlayDemo.this, MainActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}

}
/**
 * �̳�MapView��дonTouchEventʵ�����ݴ������
 * @author hejin
 *
 */
class MyLocationMapView extends MapView{
	static PopupOverlay   pop  = null;//��������ͼ�㣬���ͼ��ʹ��
	public MyLocationMapView(Context context) {
		super(context);
	}
	public MyLocationMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			//��������
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}
}


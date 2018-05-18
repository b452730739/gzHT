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
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置
 * 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 *
 */
public class LocationOverlayDemo extends Activity {
	
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	private Button jietu = null;
	//定位图层
	locationOverlay myLocationOverlay = null;
	//弹出泡泡图层
	private PopupOverlay   pop  = null;//弹出泡泡图层，浏览节点时使用
	private TextView  popupText = null;//泡泡view
	private View viewCache = null;
	MKMapViewListener mMapListener = null;
	/**
	 * 用于截获屏坐标
	 */
	//地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
	//如果不处理touch事件，则无需继承，直接使用MapView即可
	MyLocationMapView mMapView = null;	// 地图View
	private MapController mMapController = null;
	private Dialog_UI dialog_UI = null;

	//UI相关	
	OnCheckedChangeListener radioButtonListener = null;
	Button requestLocButton = null;
	boolean isRequest = false;//是否手动触发请求定位
	boolean isFirstLoc = true;//是否首次定位
	private String ImageTemp = null;
    private String temp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);

    	if(new Hellper().getNetworkIsAvailable(LocationOverlayDemo.this)){
     		
        if(isWifi(LocationOverlayDemo.this)){
            init();
        }else{
        	
        	new AlertDialog.Builder(this).setTitle("该网络不是wifi网络").setIcon(
   			     android.R.drawable.ic_dialog_info).setPositiveButton("退出",
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
   			     .setNegativeButton("继续", 	 new DialogInterface.OnClickListener() {
						
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
        CharSequence titleLable="定位功能";
        setTitle(titleLable);
		PublicWay.activityList.add(this);

        requestLocButton = (Button)findViewById(R.id.button1);
        OnClickListener btnClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//手动定位请求
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
					//传入null则，恢复默认图标
					modifyLocationOverlayIcon(null);
				}
				if (checkedId == R.id.customicon){
					//修改为自定义marker
					modifyLocationOverlayIcon(getResources().getDrawable(R.drawable.icon_geo));
				}
			}
		};
		group.setOnCheckedChangeListener(radioButtonListener);
        */
		//地图初始化
        mMapView = (MyLocationMapView)findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
        
        dialog_UI = new Dialog_UI(LocationOverlayDemo.this, "正在上传,请稍后...");
        dialog_UI.setCancelable(false);
      //创建 弹出泡泡图层
        createPaopao();
        mMapListener = new MKMapViewListener() {


			@Override
			public void onGetCurrentMap(Bitmap b) {
				/**
				 *  当调用过 mMapView.getCurrentMap()后，此回调会被触发
				 *  可在此保存截图至存储设备
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
                    	    "屏幕截图成功，图片存在: "+file.toString(),	
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
								Toast.makeText(LocationOverlayDemo.this, "上传失败", 0).show();
	
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
					
					//截图，在MKMapViewListener中保存图片
	                dialog_UI.show();

				     mMapView.getCurrentMap();	
				/*     Toast.makeText(LocationOverlayDemo.this, 
				    		 "正在截图上传...", 
				    		 Toast.LENGTH_LONG ).show();*/
				}else{
					
				     Toast.makeText(LocationOverlayDemo.this, 
				    		 "您的账号为外部账号,无上传权限...", 
				    		 Toast.LENGTH_SHORT ).show();
				}
				
			          
			}
		});
        //定位初始化
        mLocClient = new LocationClient( this );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
        //定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		//设置定位数据
	    myLocationOverlay.setData(locData);
	    //添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		//修改定位数据后刷新图层生效
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
     * 手动触发一次定位请求
     */
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(LocationOverlayDemo.this, "正在定位……", Toast.LENGTH_SHORT).show();
    }
    /**
     * 修改位置图标
     * @param marker
     */
    public void modifyLocationOverlayIcon(Drawable marker){
    	//当传入marker为null时，使用默认图标绘制
    	myLocationOverlay.setMarker(marker);
    	//修改图层，需要刷新MapView生效
    	mMapView.refresh();
    }
    /**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao(){
		viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        //泡泡点击响应回调
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
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            //如果不显示定位精度圈，将accuracy赋值为0即可
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            //更新定位数据
            myLocationOverlay.setData(locData);
            //更新图层数据执行刷新后生效
            mMapView.refresh();
            //是手动触发请求或首次定位时，移动到定位点
            if (isRequest || isFirstLoc){
            	//移动地图到定位点
                try {
					mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                isRequest = false;
            }
            //首次定位完成
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    //继承MyLocationOverlay重写dispatchTap实现点击处理
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		@Override
  		protected boolean dispatchTap() {
  			//处理点击事件,弹出泡泡
  			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("我的位置");
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
			//退出时销毁定位
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
			// 这里重写返回键
			
			Intent intent = new Intent(LocationOverlayDemo.this, MainActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}

}
/**
 * 继承MapView重写onTouchEvent实现泡泡处理操作
 * @author hejin
 *
 */
class MyLocationMapView extends MapView{
	static PopupOverlay   pop  = null;//弹出泡泡图层，点击图标使用
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
			//消隐泡泡
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}
}


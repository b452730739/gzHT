package com.elegps.help;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.constant.Constant;

public class Hellper {

	@SuppressWarnings("unused")
	private static Bitmap bitmap = null;
	BitmapFactory.Options options = new BitmapFactory.Options();

	

	public Boolean getNetworkIsAvailable(Context context){
		//此处写网络检测的代码
		//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
		ConnectivityManager conManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null ){
             return networkInfo.isAvailable();
        }
        return true ;
	}
	public static byte[] readInputStream1(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			is = null;
			baos.close();
			baos = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
	public static Bitmap getBitmapById1(int resId, Context context) {
		Bitmap bmp = null;

		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (bmp == null || bmp.isRecycled()) {
			// 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
			// 无需再使用java层的createBitmap，从而节省了java层的空间。
			try {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPurgeable = true;
				bmp = BitmapFactory.decodeStream(context.getResources()
						.openRawResource(resId), null, option);

			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return bmp;
	}
	public static String bitmaptoString(Bitmap bitmap, Context context) {
		String string = null;

		
		  //Bitmap bitmap = readInputStream(path);
		  
		  // 将Bitmap转换成字符串
		  String str = null; 
		  ByteArrayOutputStream bStream = new ByteArrayOutputStream(); 
		  bitmap.compress(CompressFormat.PNG,1, bStream);
		  byte[] bytes = bStream.toByteArray();
		 
		try {
			string = Base64.encodeToString(bytes,
					Base64.DEFAULT);
		} catch (OutOfMemoryError e) {
			
			Toast.makeText(context, "图片过大,请重新选择。", 0).show();
			return null;
		}
		return string;
	}


	// 从这里获取的图片不会内存溢出
	@SuppressWarnings("unused")
	public static Bitmap getImage(byte[] bt) {
		/* 这里是解决OOM */
		BitmapFactory.Options cwj = new BitmapFactory.Options();
		cwj.inTempStorage = new byte[1024];
		cwj.inPurgeable = true;
		//cwj.inSampleSize = 153600/bt.length; //图片的大小为153600/1024=150kb
		cwj.inSampleSize = bt.length/200000; //除的数值越小，图片越小
		/*	if(bt.length > (2*1000000) && bt.length <= (4*1000000)){
			
			cwj.inSampleSize = 2;

		}else if(bt.length > (5*1000000)){
			
			cwj.inSampleSize = 4;

		}*/
		if (null != bt) {
			return bitmap = BitmapFactory
					.decodeByteArray(bt, 0, bt.length, cwj);
		}
		return null;
	}

	public static Bitmap readInputStream(String path) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		try {
			while ((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			baos.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		try {
			is.close();
			is = null;
			baos.close();
			baos = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getImage(data);
	}
	public Hellper() {
		hashRefs = new Hashtable<String, MySoftRef>();
		q = new ReferenceQueue<Bitmap>();
	}
	/** 用于Chche内容的存储 */
	private  Hashtable<String, MySoftRef> hashRefs;
	/** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
	private ReferenceQueue<Bitmap> q;

	/**
	 * 继承SoftReference，使得每一个实例都具有可识别的标识。
	 */
	private class MySoftRef extends SoftReference<Bitmap> {
		private String _key = "";
		public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, String key) {
			super(bmp, q);
			_key = key;
		}
	}
	/**
	 * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
	 */
	private void addCacheBitmap(Bitmap bmp, String key) {
		cleanCache();// 清除垃圾引用
		MySoftRef ref = new MySoftRef(bmp, q, key);
		hashRefs.put(key, ref);
	}
/*	*//**
	 * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
	 *//*
	public  Bitmap getBitmapById(int resId, Context context) {
		Bitmap bmp = null;
		// 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
		if (hashRefs.containsKey("" + resId)) {
			MySoftRef ref = (MySoftRef) hashRefs.get("" + resId);
			bmp = (Bitmap) ref.get();
			
			System.out.println("111111111111111111");
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (bmp == null || bmp.isRecycled()) {
			// 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
			// 无需再使用java层的createBitmap，从而节省了java层的空间。
			System.out.println("2222222222");
			
			try{
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPurgeable = true;
				//option.inJustDecodeBounds = true;
				bmp = BitmapFactory.decodeStream(context.getResources().
						openRawResource(resId),null,option);
				Matrix matrix = new Matrix();
				int width = bmp.getWidth();// 获取资fe位图的宽
				int height = bmp.getHeight();// 获取资源位图的高
				if(Constant.screenHeight != 800 || Constant.screenWidth != 480){
					float w = ((float)Constant.screenHeight + 1)/ (float)800;
					float h = ((float)Constant.screenWidth + 1)/ (float)480;
					matrix.postScale(w, h);// 获取缩放比例
					
				}
				// 根据缩放比例获取新的位图
				bmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);	
				this.addCacheBitmap(bmp, "" + resId);
				
			}catch(OutOfMemoryError e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return bmp;
	}*/
	private void cleanCache() {
		MySoftRef ref = null;
		while ((ref = (MySoftRef) q.poll()) != null) {
			hashRefs.remove(ref._key);
		}
	}

	/**
	 * 清除Cache内的全部内容
	 */
	public void clearCache() {
		cleanCache();
		hashRefs.clear();
		System.gc();
	}
	
}

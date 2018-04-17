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
		//�˴�д������Ĵ���
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

		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
		if (bmp == null || bmp.isRecycled()) {
			// ��˵decodeStreamֱ�ӵ���JNI>>nativeDecodeAsset()�����decode��
			// ������ʹ��java���createBitmap���Ӷ���ʡ��java��Ŀռ䡣
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
		  
		  // ��Bitmapת�����ַ���
		  String str = null; 
		  ByteArrayOutputStream bStream = new ByteArrayOutputStream(); 
		  bitmap.compress(CompressFormat.PNG,1, bStream);
		  byte[] bytes = bStream.toByteArray();
		 
		try {
			string = Base64.encodeToString(bytes,
					Base64.DEFAULT);
		} catch (OutOfMemoryError e) {
			
			Toast.makeText(context, "ͼƬ����,������ѡ��", 0).show();
			return null;
		}
		return string;
	}


	// �������ȡ��ͼƬ�����ڴ����
	@SuppressWarnings("unused")
	public static Bitmap getImage(byte[] bt) {
		/* �����ǽ��OOM */
		BitmapFactory.Options cwj = new BitmapFactory.Options();
		cwj.inTempStorage = new byte[1024];
		cwj.inPurgeable = true;
		//cwj.inSampleSize = 153600/bt.length; //ͼƬ�Ĵ�СΪ153600/1024=150kb
		cwj.inSampleSize = bt.length/200000; //������ֵԽС��ͼƬԽС
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
	/** ����Chche���ݵĴ洢 */
	private  Hashtable<String, MySoftRef> hashRefs;
	/** ����Reference�Ķ��У������õĶ����Ѿ������գ��򽫸����ô�������У� */
	private ReferenceQueue<Bitmap> q;

	/**
	 * �̳�SoftReference��ʹ��ÿһ��ʵ�������п�ʶ��ı�ʶ��
	 */
	private class MySoftRef extends SoftReference<Bitmap> {
		private String _key = "";
		public MySoftRef(Bitmap bmp, ReferenceQueue<Bitmap> q, String key) {
			super(bmp, q);
			_key = key;
		}
	}
	/**
	 * �������õķ�ʽ��һ��Bitmap�����ʵ���������ò����������
	 */
	private void addCacheBitmap(Bitmap bmp, String key) {
		cleanCache();// �����������
		MySoftRef ref = new MySoftRef(bmp, q, key);
		hashRefs.put(key, ref);
	}
/*	*//**
	 * ������ָ����drawable�µ�ͼƬ��ԴID�ţ����Ը����Լ�����Ҫ������򱾵�path�»�ȡ�������»�ȡ��ӦBitmap�����ʵ��
	 *//*
	public  Bitmap getBitmapById(int resId, Context context) {
		Bitmap bmp = null;
		// �������Ƿ��и�Bitmapʵ���������ã�����У�����������ȡ�á�
		if (hashRefs.containsKey("" + resId)) {
			MySoftRef ref = (MySoftRef) hashRefs.get("" + resId);
			bmp = (Bitmap) ref.get();
			
			System.out.println("111111111111111111");
		}
		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
		if (bmp == null || bmp.isRecycled()) {
			// ��˵decodeStreamֱ�ӵ���JNI>>nativeDecodeAsset()�����decode��
			// ������ʹ��java���createBitmap���Ӷ���ʡ��java��Ŀռ䡣
			System.out.println("2222222222");
			
			try{
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPurgeable = true;
				//option.inJustDecodeBounds = true;
				bmp = BitmapFactory.decodeStream(context.getResources().
						openRawResource(resId),null,option);
				Matrix matrix = new Matrix();
				int width = bmp.getWidth();// ��ȡ��feλͼ�Ŀ�
				int height = bmp.getHeight();// ��ȡ��Դλͼ�ĸ�
				if(Constant.screenHeight != 800 || Constant.screenWidth != 480){
					float w = ((float)Constant.screenHeight + 1)/ (float)800;
					float h = ((float)Constant.screenWidth + 1)/ (float)480;
					matrix.postScale(w, h);// ��ȡ���ű���
					
				}
				// �������ű�����ȡ�µ�λͼ
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
	 * ���Cache�ڵ�ȫ������
	 */
	public void clearCache() {
		cleanCache();
		hashRefs.clear();
		System.gc();
	}
	
}

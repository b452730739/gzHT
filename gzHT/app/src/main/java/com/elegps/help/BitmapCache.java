package com.elegps.help;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.constant.Constant;

public class BitmapCache {
	private static BitmapCache cache;

	/** ����Chche���ݵĴ洢 */
	private Hashtable<String, MySoftRef> hashRefs;
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
	
	private BitmapCache() {
		hashRefs = new Hashtable<String, MySoftRef>();
		q = new ReferenceQueue<Bitmap>();
	}
	/**
	 * ȡ�û�����ʵ��
	 */
	public static BitmapCache getInstance() {
		if (cache == null) {
			cache = new BitmapCache();
		}
		return cache;
	}

	/**
	 * �������õķ�ʽ��һ��Bitmap�����ʵ���������ò����������
	 */
	private void addCacheBitmap(Bitmap bmp, String key) {
		cleanCache();// �����������
		MySoftRef ref = new MySoftRef(bmp, q, key);
		hashRefs.put(key, ref);
	}


	
	/**
	 * ������ָ����drawable�µ�ͼƬ��ԴID�ţ����Ը����Լ�����Ҫ������򱾵�path�»�ȡ�������»�ȡ��ӦBitmap�����ʵ��
	 */
	public Bitmap getBitmapById(int resId, Context context) {
		Bitmap bmp = null;
		// �������Ƿ��и�Bitmapʵ���������ã�����У�����������ȡ�á�
		if (hashRefs.containsKey("" + resId)) {
			MySoftRef ref = (MySoftRef) hashRefs.get("" + resId);
			bmp = (Bitmap) ref.get();
			
		}
		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
		if (bmp == null || bmp.isRecycled()) {
			// ��˵decodeStreamֱ�ӵ���JNI>>nativeDecodeAsset()�����decode��
			// ������ʹ��java���createBitmap���Ӷ���ʡ��java��Ŀռ䡣

			try{
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPurgeable = true;
				bmp = BitmapFactory.decodeStream(context.getResources().openRawResource(resId),null,option);
				
				Matrix matrix = new Matrix();
				int width = bmp.getWidth();// ��ȡ��feλͼ�Ŀ�
				int height = bmp.getHeight();// ��ȡ��Դλͼ�ĸ�
				if(Constant.screenHeight != 800 || Constant.screenWidth != 480){
					float w = ((float)Constant.screenHeight + 1)/ (float)860;
					float h = ((float)Constant.screenWidth + 1)/ (float)480;
					matrix.postScale(w, h);// ��ȡ���ű���
				}
				// �������ű�����ȡ�µ�λͼ
				try {
					bmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true);	
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.addCacheBitmap(bmp, "" + resId);

			}catch(OutOfMemoryError e){
				e.printStackTrace();
			}
		}
		return bmp;
	}
	
	
	
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

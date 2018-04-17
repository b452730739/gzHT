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

	/** 用于Chche内容的存储 */
	private Hashtable<String, MySoftRef> hashRefs;
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
	
	private BitmapCache() {
		hashRefs = new Hashtable<String, MySoftRef>();
		q = new ReferenceQueue<Bitmap>();
	}
	/**
	 * 取得缓存器实例
	 */
	public static BitmapCache getInstance() {
		if (cache == null) {
			cache = new BitmapCache();
		}
		return cache;
	}

	/**
	 * 以软引用的方式对一个Bitmap对象的实例进行引用并保存该引用
	 */
	private void addCacheBitmap(Bitmap bmp, String key) {
		cleanCache();// 清除垃圾引用
		MySoftRef ref = new MySoftRef(bmp, q, key);
		hashRefs.put(key, ref);
	}


	
	/**
	 * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
	 */
	public Bitmap getBitmapById(int resId, Context context) {
		Bitmap bmp = null;
		// 缓存中是否有该Bitmap实例的软引用，如果有，从软引用中取得。
		if (hashRefs.containsKey("" + resId)) {
			MySoftRef ref = (MySoftRef) hashRefs.get("" + resId);
			bmp = (Bitmap) ref.get();
			
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (bmp == null || bmp.isRecycled()) {
			// 传说decodeStream直接调用JNI>>nativeDecodeAsset()来完成decode，
			// 无需再使用java层的createBitmap，从而节省了java层的空间。

			try{
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inPurgeable = true;
				bmp = BitmapFactory.decodeStream(context.getResources().openRawResource(resId),null,option);
				
				Matrix matrix = new Matrix();
				int width = bmp.getWidth();// 获取资fe位图的宽
				int height = bmp.getHeight();// 获取资源位图的高
				if(Constant.screenHeight != 800 || Constant.screenWidth != 480){
					float w = ((float)Constant.screenHeight + 1)/ (float)860;
					float h = ((float)Constant.screenWidth + 1)/ (float)480;
					matrix.postScale(w, h);// 获取缩放比例
				}
				// 根据缩放比例获取新的位图
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
	 * 清除Cache内的全部内容
	 */
	public void clearCache() {
		cleanCache();
		hashRefs.clear();
		System.gc();
	}

}

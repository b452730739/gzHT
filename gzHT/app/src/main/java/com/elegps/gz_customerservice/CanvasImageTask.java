package com.elegps.gz_customerservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class CanvasImageTask extends AsyncTask<ImageView, Void, Bitmap> {
	private ImageView gView;
	BitmapFactory.Options options = new BitmapFactory.Options();

	protected Bitmap doInBackground(ImageView... views) {

		options.inSampleSize = 15;
		Bitmap bmp = null;
		ImageView view = views[0];

		if (view.getTag() != null) {
			try {
				bmp = BitmapFactory.decodeFile(view.getTag().toString(),options);
			} catch (Exception e) {

				return null;
			}
		}
		this.gView = view;
		return bmp;
	}

	protected void onPostExecute(Bitmap bm) {
		if (bm != null) {
			// 此处更新图片
			this.gView.setImageBitmap(bm);
			this.gView = null;
		}
	}
}
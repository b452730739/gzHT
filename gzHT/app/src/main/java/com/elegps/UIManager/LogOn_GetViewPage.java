package com.elegps.UIManager;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.constant.Constant;
import com.elegps.gz_customerservice.R;
import com.elegps.help.BitmapCache;
import com.elegps.help.Hellper;

public class LogOn_GetViewPage extends RelativeLayout {

	private Context context = null;
	private NextView nextView = null;
	private int j = 0;
	private int temp = Constant.MainPage_Computer.length;
	private ViewPager vp = null;
	private Handler handler = null;
	private Runnable runnable = null;
	private int screenWidth = 0;
	private int screenHeight = 0;
	public LogOn_GetViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		this.context = context;
		this.setLayoutParams(new LinearLayout.LayoutParams(-1, Constant.screenHeight * 250/ 800));

		init();
	}

	private void init() {

		vp = new ViewPager(context);

		ArrayList<View> arrayList = new ArrayList<View>();
		RelativeLayout.LayoutParams vpParams = null;
			
			vpParams = new RelativeLayout.LayoutParams(
					screenWidth * 350/ 480 , screenHeight * 350/ 800 );


		for (int i = 0; i < Constant.pageID.length; i++) {
			
			ImageView iv = new ImageView(context);
			//iv.setImageResource(Constant.pageID[i]);
			iv.setImageBitmap(BitmapCache.getInstance().getBitmapById(Constant.pageID[i], context));
			arrayList.add(iv);
		}
		vp.setAdapter(new LogOn_ViewPageAdapter(arrayList));

		vpParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		vpParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		vp.setLayoutParams(vpParams);
		this.addView(vp);
		nextView = new NextView(context, Constant.pageID.length);

		this.addView(nextView);
		handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() {

				if (j < Constant.MainPage_Computer.length) {

					j++;
					vp.setCurrentItem(j);
					
				} else if (j == Constant.MainPage_Computer.length) {
					
					vp.setCurrentItem(temp);
					if (temp == 0) {
						j = 0;
						temp = Constant.MainPage_Computer.length;
					}
					temp--;
				}

				handler.postDelayed(this, 3000);
			}
		};
		handler.postDelayed(runnable, 3000);

/*		vp.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					
					handler.removeCallbacks(runnable);

					break;

				}
				return false;
			}
		});*/
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setCurPage(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	/**
	 * 更新当前页码
	 */
	public void setCurPage(int page) {
		nextView.setSelect(page);
	}

	/**
	 * 左右滑动的点点
	 * 
	 * @author lichendong
	 * 
	 */
	class NextView extends View {
		int size = 0;
		int select = 0;

		public NextView(Context context, int size) {
			super(context);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					-1, -2);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);

			setLayoutParams(params);
			this.size = size;
		}

		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Paint paint = new Paint();
			paint.setColor(Color.BLUE); // 设置画笔颜色
			paint.setStyle(Paint.Style.FILL);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(2);
			paint.setDither(true);
			for (int i = 0; i < size; i++) {
				if (i == select) {
					paint.setStyle(Paint.Style.FILL);
					canvas.drawBitmap(
							Hellper.getBitmapById1(R.drawable.checked, context),
							(screenWidth - size * 30) / 2 + i * 30, 0, paint);
				} else {
					paint.setStyle(Paint.Style.STROKE);
					canvas.drawBitmap(Hellper.getBitmapById1(
							R.drawable.nochecked, context),
							(screenWidth - size * 30) / 2 + i * 30, 0, paint);
				}
			}
			invalidate();
		}

		public void setSelect(int select) {
			this.select = select;
			invalidate();
		}
	}
}

package com.elegps.UIManager;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LogOn_ViewPageAdapter extends PagerAdapter{

	ArrayList<View> list ;
	
	//构造方法
	public LogOn_ViewPageAdapter(ArrayList<View> list) {
		this.list = list;
	}
	
	public int getCount() {
		return list.size();
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}


	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {
		try {
			if(list.size() > arg1)
				((ViewPager) arg0).removeView((View)list.get(arg1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView((View)list.get(arg1));
		return list.get(arg1);
	}
	

}

package com.elegps.gz_customerservice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.constant.Constant;
import com.elegps.photo.Photo_LookActivity;

public class PaiZhao_LiShi extends Activity {

	private GridView gridView = null;
	private ArrayList<String> arrayList = null;
	private Grid_Adapter grid_Adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init(){
		
	
		gridView = new GridView(PaiZhao_LiShi.this);
		gridView.setBackgroundColor(Color.WHITE);
		gridView.setNumColumns(2);
		gridView.setVerticalSpacing(15);
		gridView.setHorizontalSpacing(10);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(PaiZhao_LiShi.this, Photo_LookActivity.class);
				intent.putExtra("PhotoPath", arrayList.get(arg2));
				startActivity(intent);				
			}
		});
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				is_delete(arg2);		
				return false;
			}
		});
		
		set_Adapter();
		setContentView(gridView);
	}
	private void set_Adapter(){
	arrayList = new ArrayList<String>();
		File f = new File(Constant.photoPath+"/photos/");
		if (f.isDirectory()) {
	        String[] children = f.list();
	        //递归删除目录中的子目录下
	        for (int i=0; i<children.length; i++) {
	        	if(children[i].endsWith("png")|children[i].endsWith("jpg")){
		        	arrayList.add(Constant.photoPath+"/photos/"+children[i]);
	        	}
	        }
		}
		//System.out.println(arrayList.size()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

		grid_Adapter = new Grid_Adapter(arrayList);
		gridView.setAdapter(grid_Adapter);
		
	}
	 /*
     * 确认是否删除
     * */
    public  void is_delete(final int position){
        //提示对话框
        AlertDialog.Builder builder=new Builder(PaiZhao_LiShi.this);
        builder.setTitle("确定是否删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
        	@Override
            public void onClick(DialogInterface dialog, int which) {
        		new File(arrayList.get(position)).delete();
        		set_Adapter();
        		
        	}
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	dialog.dismiss();
         
            }
        }).show();
    }
	class Grid_Adapter extends BaseAdapter{
		ArrayList<String> arrayList = null;
		Grid_Adapter(ArrayList<String> arrayList){
			this.arrayList = arrayList;
		}
		@Override
		public int getCount() {
			try {
				return arrayList.size();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(PaiZhao_LiShi.this);
			
			//imageView.setImageBitmap(getBitmap(arrayList.get(position)));
			imageView.setTag(arrayList.get(position));
			new CanvasImageTask().execute(imageView);
			return imageView;
		}
		
	}
	/**
	 * 依据所指定的drawable下的图片资源ID号（可以根据自己的需要从网络或本地path下获取），重新获取相应Bitmap对象的实例
	 */
	public Bitmap getBitmap(String filePath) {
		Bitmap bmp = null;
		try {

			if (bmp == null) {
				try{
				
					byte[] bt = null;
		
						try {
							bt = readInputStream(new FileInputStream(new File(filePath)));
						} catch (Exception e) {
							e.printStackTrace();
						}
					//}

						BitmapFactory.Options option = new BitmapFactory.Options();
					option.inPurgeable = true;
					
					option.inTempStorage = new byte[1024];
					option.inPurgeable = true;
					//cwj.inSampleSize = 153600/bt.length; //图片的大小为153600/1024=150kb
					option.inSampleSize = bt.length/200000;
					
					if(bt != null){
						bmp = BitmapFactory.decodeByteArray(bt, 0, bt.length,option);
					}else{
					}
					// 根据缩放比例获取新的位图
					bmp = Bitmap.createBitmap(bmp, 0, 0, 500, 500, null, true);	
					//this.addCacheBitmap(bmp, filePath);
				}catch(OutOfMemoryError e){
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}
	public static byte[] readInputStream(InputStream is) {
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
}

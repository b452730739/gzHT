package com.elegps.UIManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.database.NoteService;

import com.elegps.gz_customerservice.R;

public class NoteBooK_ListAdapter extends BaseAdapter {

	private Context mContext = null;
	private NoteService noteService = null;

	public NoteBooK_ListAdapter(Context mContext ,NoteService noteService){
		
	
		this.mContext = mContext;
		this.noteService = noteService;
	}
	
	@Override
	public int getCount() {
		try {
			return (int) noteService.getCount();
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
		
/*		LinearLayout ly = new LinearLayout(mContext);
		ly.setLayoutParams(new ListView.LayoutParams(-1,-2));
		ly.setOrientation(1);
		
		TextView time = new TextView(mContext);
		time.setTextSize(15);
		time.setTextColor(Color.GRAY);
		time.setText("   "+noteService.getScrollData().get(noteService.getCount()-position-1).getTime());
		
		TextView title = new TextView(mContext);
		title.setTextSize(17);
		title.setTextColor(Color.BLACK);
		title.setText("   "+noteService.getScrollData().get(noteService.getCount()-position-1).getTitle()
			);
		
		TextView tv = new TextView(mContext);
		tv.setTextSize(5);
		
		ly.addView(time);
		ly.addView(title);
		ly.addView(tv);*/
		LinearLayout ly = (LinearLayout) ((Activity) mContext)
				.getLayoutInflater().inflate(R.layout.baoxiu_item, null);
		
		TextView time = (TextView)ly.findViewById(R.id.textView2);
		time.setTextSize(20);
		time.setId(100);
	/*	time.setText(
				Html.fromHtml(
						"<font color=787c85>"+
				noteService.getScrollData().get(noteService.getCount()-position-1).getTime()+"</font> "));*/
	
		TextView tv =  (TextView)ly.findViewById(R.id.textView1);
		tv.setTextSize(20);
		tv.setTextColor(Color.BLACK);
		tv.setText(
				Html.fromHtml(noteService.getScrollData().get(noteService.getCount()-position-1).getTitle()+"("+
						"<font color=787c85>"+
				noteService.getScrollData().get(noteService.getCount()-position-1).getTime()+"</font> "+")"));
		
		Button button1 = (Button)ly.findViewById(R.id.delete);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		Button button2 = (Button)ly.findViewById(R.id.imageView2);
		button2.setText("Ïê  Ï¸  ÄÚ  ÈÝ");
		return ly;
	}

}

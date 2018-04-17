package com.elegps.notebook;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.database.Note;
import cn.database.NoteService;

import com.elegps.Complaints_proposals.Complaints_ProposalsActivity;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;

public class Note_BookActivity extends Activity implements OnClickListener {

	private ImageView add_note = null;
	private ImageView back = null;
	private ListView list = null;
	private AlertDialog.Builder builder = null;// 对话框
	private List<Note> lists = null;// 获得的全部信息
	private NoteService noteService = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_book);
		PublicWay.activityList.add(this);

		add_note = (ImageView) findViewById(R.id.addnote);
		list = (ListView) findViewById(R.id.listView1);
		back = (ImageView) findViewById(R.id.note_back);

		noteService = new NoteService(this);
		lists = noteService.getScrollData();

		back.setOnClickListener(this);
		add_note.setOnClickListener(this);

		list.setAdapter(new NoteBooK_ListAdapter(this, noteService));
		list.setDivider(getResources().getDrawable(R.drawable.note_horizontal));

/*		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(Note_BookActivity.this,
						Add_NoteActivity.class);
				intent.putExtra("Noteid", lists.get(lists.size() - arg2 - 1)
						.getId());

				startActivity(intent);
				Note_BookActivity.this.finish();

			}
		});*/
		builder = new AlertDialog.Builder(Note_BookActivity.this);

		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				builder.setItems(new String[] { "删除" },
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								new NoteService(Note_BookActivity.this)
										.delete(lists.get(
												lists.size() - arg2 - 1)
												.getId());

								lists = noteService.getScrollData();
								list.setAdapter(new NoteBooK_ListAdapter(
										Note_BookActivity.this, noteService));

							}
						}).show();

				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		switch (v.getId()) {

		case R.id.addnote:

			intent.setClass(Note_BookActivity.this, Add_NoteActivity.class);

			break;
		case R.id.note_back:

			intent.setClass(Note_BookActivity.this, MainActivity.class);

			break;

		default:
			break;
		}

		startActivity(intent);
		this.finish();

	}
	class NoteBooK_ListAdapter extends BaseAdapter {

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
		public View getView(final int position, View convertView, ViewGroup parent) {
			
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
			
			/*	time.setText(
			Html.fromHtml(
					"<font color=787c85>"+
			noteService.getScrollData().get(noteService.getCount()-position-1).getTime()+"</font> "));*/

			LinearLayout ly = (LinearLayout) ((Activity) mContext)
					.getLayoutInflater().inflate(R.layout.baoxiu_item, null);
			
			TextView time = (TextView)ly.findViewById(R.id.textView2);
			time.setTextSize(20);
			time.setId(100);

			TextView tv =  (TextView)ly.findViewById(R.id.textView1);
			tv.setTextSize(20);
			tv.setTextColor(Color.BLACK);
			
			tv.setText(Html.fromHtml("<font >"+
			noteService.getScrollData().get(noteService.getCount()-position-1).getTitle()+"</font> " +
					"\t\t\t<font color=#787c85>("
	+noteService.getScrollData().get(noteService.getCount()-position-1).getTime()+
			")</font> ")
/*					Html.fromHtml(noteService.getScrollData().get(noteService.getCount()-position-1).getTitle()+"("+
							"<font color=787c85>"+
					noteService.getScrollData().get(noteService.getCount()-position-1).getTime()+"</font> "+")")*/
					);
			
			Button button1 = (Button)ly.findViewById(R.id.delete);
			button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				     //提示对话框
			        AlertDialog.Builder builder=new Builder(Note_BookActivity.this);
			        builder.setTitle("确定是否删除?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
			        	@Override
			            public void onClick(DialogInterface dialog, int which) {
			        		new NoteService(Note_BookActivity.this)
							.delete(lists.get(
									lists.size() - position - 1)
									.getId());

					lists = noteService.getScrollData();
					list.setAdapter(new NoteBooK_ListAdapter(
							Note_BookActivity.this, noteService));
			        	}
			        	}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			                
			                @Override
			                public void onClick(DialogInterface dialog, int which) {
			                	dialog.dismiss();
			             
			                }
			            }).show();
					
					/*builder.setItems(new String[] { "删除" },
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									new NoteService(Note_BookActivity.this)
											.delete(lists.get(
													lists.size() - position - 1)
													.getId());

									lists = noteService.getScrollData();
									list.setAdapter(new NoteBooK_ListAdapter(
											Note_BookActivity.this, noteService));

								}
							}).show();*/
				}
			});
			Button button2 = (Button)ly.findViewById(R.id.imageView2);
			button2.setText("详  细  内  容");
			button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Note_BookActivity.this,
							Add_NoteActivity.class);
					intent.putExtra("Noteid", lists.get(lists.size() - position - 1)
							.getId());

					startActivity(intent);
					Note_BookActivity.this.finish();					
				}
			});
			return ly;
		}

	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键

			Intent intent = new Intent(Note_BookActivity.this,
					MainActivity.class);

			startActivity(intent);
			this.finish();

			return true;
		}

		return false;

	}
}

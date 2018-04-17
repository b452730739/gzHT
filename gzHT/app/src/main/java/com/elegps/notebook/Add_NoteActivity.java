package com.elegps.notebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.database.Note;
import cn.database.NoteService;

import com.constant.Constant;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.UIManager.Dialog_UI;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Note_paiGBean;


public class Add_NoteActivity extends Activity implements OnClickListener{

	private EditText[] add_note = null;
	private ImageView add = null;
	private TextView time = null;
	private Bundle bundle = null;
	private NoteService service = null;
	private int getID = -1; //如果是-1就是添加状态，否则就是修改或者查看状态
	private ImageView image_back = null;
	private Dialog_UI dialog_UI = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.add_notebook);
		PublicWay.activityList.add(this);

		service = new NoteService(this);
		
		init();
		
		bundle = getIntent().getExtras();
		
  		if(bundle!=null){
  			
  			getID = bundle.getInt("Noteid");
  			ischange(getID);

		}else{
			
		};
		
		
		
	}
	private void init(){
		
		add_note = new EditText[6];		
		
		add_note[0] = (EditText)findViewById(R.id.note_title);
		add_note[1] = (EditText)findViewById(R.id.note_name);
		add_note[2] = (EditText)findViewById(R.id.note_contactname);
		add_note[3] = (EditText)findViewById(R.id.note_phone);
		add_note[4] = (EditText)findViewById(R.id.note_error);
		add_note[5] = (EditText)findViewById(R.id.note_suggestion);
		
		add = (ImageView)findViewById(R.id.save);
		time = (TextView)findViewById(R.id.time);
		image_back = (ImageView)findViewById(R.id.note_addback);
		image_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Add_NoteActivity.this, Note_BookActivity.class);
				startActivity(intent);
				Add_NoteActivity.this.finish();
			}
		});
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日  "
				+ "HH:mm:ss  ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);		
		time.setText(str);		
		add.setOnClickListener(this);		
	}	
	private void ischange(int id){		
		
		Note note = service.find(id);		
		add_note[0].setText(note.getTitle());
		add_note[1].setText(note.getEt_name());
		add_note[2].setText(note.getEt_people());
		add_note[3].setText(note.getEt_phone());
		add_note[4].setText(note.getError());
		add_note[5].setText(note.getSuggestion());
		time.setText(note.getTime());
		
	}
	private String result = null;
	@Override
	public void onClick(View v) {
		
		if(getID == -1){
			
			dialog_UI = new Dialog_UI(this, "正在上传...");
			dialog_UI.show();
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					
					service.save(
							new Note
							(0, add_note[0].getText().toString(), add_note[1].getText().toString(), 
								add_note[2].getText().toString(), add_note[3].getText().toString(),
								time.getText().toString(),add_note[4].getText().toString(),
								add_note[5].getText().toString())
							);
					
					
					
					try {
					if("1".equals(Constant.ISINSIDE)){
							result =	ContentWeb.getinstance().contetweb(
								IP_Address.LOGSERVICE,
								"AddLog", 
								new String[]{
								"strTitle","strCustName","strContact","strMobile",
								"strContent","strOperate","strAccount","strUserName"},
								
								new String[]{
										add_note[0].getText().toString(), 
										add_note[1].getText().toString(), 
										add_note[2].getText().toString(), 
										add_note[3].getText().toString(),
										add_note[4].getText().toString(),
										add_note[5].getText().toString(),
										Constant.DENGLUNAME,
										Constant.UserName},dialog_UI);
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
				
					return null;
				}

				@Override
				protected void onPostExecute(Void result1) {
					super.onPostExecute(result1);
					dialog_UI.dismiss();
					if("1".equals(Constant.ISINSIDE)){
					Toast.makeText(Add_NoteActivity.this, result, 0).show();
				}}
				
			}.execute((Void)null);
			
			
		}else{
			
			service.update(
					new Note
					(getID, add_note[0].getText().toString(), add_note[1].getText().toString(), 
						add_note[2].getText().toString(), add_note[3].getText().toString(),
						time.getText().toString(),add_note[4].getText().toString(),
						add_note[5].getText().toString())
					);
		}

		Intent intent = new Intent(Add_NoteActivity.this , Note_BookActivity.class);
		startActivity(intent);
		this.finish();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
			
			Intent intent = new Intent(Add_NoteActivity.this, Note_BookActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}
}

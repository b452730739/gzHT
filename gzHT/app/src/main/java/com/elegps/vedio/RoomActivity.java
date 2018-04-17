package com.elegps.vedio;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatTextMsgEvent;
import com.constant.IP_Address;
import com.content.webservice.ContentWeb;
import com.elegps.antkingXML.PullPersonService;
import com.elegps.gz_customerservice.R;
import com.elegps.gz_customerservice.MainActivity;
import com.elegps.help.PublicWay;
import com.elegps.javabean.Video_users;

public class RoomActivity extends Activity implements AnyChatBaseEvent,AnyChatTextMsgEvent{
	private LinearLayout fullLayout;
	private LinearLayout mainLayout;
	private ImageView sendBtn;
	
	private ListView userListView;
	private MessageListView messageListView;
	private BaseAdapter userListAdapter;
	
	public AnyChatCoreSDK anychat;
	
	private ArrayList<String> idList = new ArrayList<String> ();
	private ArrayList<String> userList = new ArrayList<String> ();
	private ArrayList<String> messageList = new ArrayList<String> ();
	
	private EditText messageEditText;
	List<Video_users> video_users = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userListAdapter=new UserListListAdapter(this);
        
		//setContentView(R.layout.video);
        InitialSDK();
        Intent intent = getIntent();
        intent.getIntExtra("RoomID",0);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InitialLayout();
        
    }
    
    private void InitialSDK()
    {
        anychat = new AnyChatCoreSDK();
        anychat.SetBaseEvent(this);
        anychat.SetTextMessageEvent(this);
    }
    private void InitialLayout()
    {   
    	
    	RelativeLayout relativeLayout = new RelativeLayout(this);
    	relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    	relativeLayout.setBackgroundResource(R.drawable.note_titlebg);
    	
    	ImageView back = new ImageView(this);
    	back.setImageResource(R.drawable.note_back);
    	RelativeLayout.LayoutParams backParams = new RelativeLayout.LayoutParams(-2, -2);
    	backParams.addRule(RelativeLayout.CENTER_VERTICAL);
    	backParams.setMargins(10, 0, 0, 0);
    	back.setLayoutParams(backParams);
    	back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					anychat.LeaveRoom(-1);
					anychat.Logout();
					anychat.Release();	// 关闭SDK，不再返回登录界面
			    	PublicWay.activityList1.get(0).finish();

				} catch (Exception e) {
					e.printStackTrace();
				}

					Intent intent = new Intent(RoomActivity.this, MainActivity.class);
					
					startActivity(intent);
					RoomActivity.this.finish();
			}
		});
    	TextView title = new TextView(this);
    	title.setText("视频聊天");
    	title.setTextColor(Color.WHITE);
    	title.setTextSize(20);
    	RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(-2, -2);
    	titleParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    	title.setLayoutParams(titleParams);
    	
    	relativeLayout.addView(back);
    	relativeLayout.addView(title);
    	
    	fullLayout =  new LinearLayout(this);
    	fullLayout.setBackgroundResource(R.drawable.gary_bg);
    	fullLayout.setOrientation(LinearLayout.VERTICAL);	    
    	
    	fullLayout.addView(relativeLayout); //添加Title
    	
    	TextView documental = new TextView(this);
    	documental.setText("消息记录");
    	documental.setTextSize(20);
    	documental.setTextColor(Color.BLACK);
    	documental.setBackgroundResource(R.drawable.shibeijin);
    	documental.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    	
    	
    	mainLayout =  new LinearLayout(this);
    	mainLayout.setBackgroundColor(Color.TRANSPARENT);
	    mainLayout.setOrientation(LinearLayout.VERTICAL);
	    
    	RelativeLayout sendLayout =  new RelativeLayout(this);
    	
    	sendLayout.setBackgroundResource(R.drawable.note_titlebg);
    	
		messageEditText = new EditText(this);
		messageEditText.setTextSize(15);
		
	    sendBtn  = new ImageView(this);
	    sendBtn.setBackgroundResource(R.drawable.video_send);
		sendBtn.setOnClickListener(listener);
		RelativeLayout.LayoutParams sendBtnParams = new RelativeLayout.LayoutParams(
				(int) (ScreenInfo.WIDTH-120),-2);
		sendBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
		sendBtnParams.addRule(RelativeLayout.RIGHT_OF);
		sendBtnParams.setMargins(10, 0, 0, 0);
		
		RelativeLayout.LayoutParams messageParams = new RelativeLayout.LayoutParams(-2, -2);
		messageParams.addRule(RelativeLayout.CENTER_VERTICAL);
		messageParams.setMargins((int) (ScreenInfo.WIDTH-100), 0, 0, 0);
		
		sendLayout.addView(messageEditText,sendBtnParams);
		sendLayout.addView(sendBtn,messageParams);
		
		messageListView = new MessageListView(this);
		messageListView.SetFileList(messageList);
		
		userListView = new ListView(this);
		userListView.setCacheColorHint(0);
		userListView.setBackgroundColor(Color.TRANSPARENT);
		userListView.setDivider(getResources().getDrawable(R.drawable.note_horizontal));
		userListView.setAdapter(userListAdapter);
		userListView.setOnItemClickListener(itemClickListener);
		
    	TextView Onlinepeople = new TextView(this);
    	Onlinepeople.setText("在线人员");
    	Onlinepeople.setTextSize(20);
    	Onlinepeople.setTextColor(Color.BLACK);
    	Onlinepeople.setBackgroundResource(R.drawable.shibeijin);
    	Onlinepeople.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    	
    	fullLayout.addView(Onlinepeople); //在线人员
		
    	fullLayout.addView(userListView,new LinearLayout.LayoutParams(
				-1,(int) (ScreenInfo.HEIGHT*4/25))); //在线人员
    	
    	fullLayout.addView(documental);       //消息记录
    	
		fullLayout.addView(messageListView,new LinearLayout.LayoutParams(
				ScreenInfo.WIDTH,ScreenInfo.HEIGHT*430/800));  //消息
		
    	fullLayout.addView(sendLayout,new LinearLayout.LayoutParams(-1,-2));
    	this.setContentView(fullLayout);
    }
    
    OnClickListener listener = new OnClickListener()
    {       
		public void onClick(View v) 
		{
			if(v == sendBtn)
			{
				SendMessage();
			}		
		}
    };
    OnItemClickListener itemClickListener=new OnItemClickListener() {
    	
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			StartVideoChat(arg2);
		}
	};
    public class UserListListAdapter extends BaseAdapter 
	{
		private Context context;

		public UserListListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return userList.size();
		}

		@Override
		public Object getItem(int position) {
			return userList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			LinearLayout ly = (LinearLayout) ((Activity) context)
					.getLayoutInflater().inflate(R.layout.vedio_uses, null);
			
			//ImageView imageView = (ImageView)ly.findViewById(R.id.imageView1);
			final TextView textView1 = (TextView)ly.findViewById(R.id.textView1);
			final TextView textView2 = (TextView)ly.findViewById(R.id.textView2);
			final TextView textView3 = (TextView)ly.findViewById(R.id.textView3);
			
			
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					String temp1 = null;
					try {
						temp1 = ContentWeb
								.getinstance()
								.contetweb(
										IP_Address.MEMBERSERVICE,
										"GetUserInfo", 
										new String[] { "strAccount" },
										new String[] {userList.get(position) },null);
					}  catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						video_users = new PullPersonService()
						.pullReadXml(temp1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					try {
						textView1.setText(" "+video_users.get(0).getMobile());
						textView2.setText(" "+video_users.get(0).getCustName());
						textView3.setText(" "+video_users.get(0).getEmplNO());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}.execute((Void)null);

	
			
			return ly;
		}
	}
    
    private void SendMessage()
    {
		anychat.SendTextMessage(-1, 0, messageEditText.getText().toString());
		messageList.add("我说: "+ messageEditText.getText().toString());
		//messageListView.setStackFromBottom(true);
		messageListView.SetFileList(messageList);
		messageEditText.setText("");
		messageListView.setSelection(messageListView.getAdapter().getCount()-1);		
		
    }
    
    public void StartVideoChat(int position)
    {		
		   Intent intent=new Intent();
		   intent.putExtra("UserID", idList.get(position));
		   intent.setClass(RoomActivity.this, VideoActivity.class);
		   startActivity(intent);
    }
 
    
    protected void onDestroy(){
		Log.e("******RoomActivity***********", "RoomActivity  onDestroy");	
 	   anychat.LeaveRoom(-1);
    	super.onDestroy();
    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// 这里重写返回键
	    	try {
				anychat.LeaveRoom(-1);
				anychat.Logout();
				anychat.Release();	// 关闭SDK，不再返回登录界面
				
				for(int i = 0 ;i<PublicWay.activityList1.size();i++){
					
			    	PublicWay.activityList1.get(i).finish();
			    	
				}
		    	
			} catch (Exception e) {
				e.printStackTrace();
			}

			Intent intent = new Intent(RoomActivity.this, MainActivity.class);
			
			startActivity(intent);
			this.finish();
			
		return true;
		}     
		
		return false;

	}
    protected void onResume() {
        anychat.SetBaseEvent(this);
        idList.clear();
        userList.clear();
        int[] userID = anychat.GetOnlineUser();
    	for(int i=0; i<userID.length ; i++)
    	{
    		idList.add(""+userID[i]);
    	}
    	for(int i=0; i<userID.length ; i++)
    	{
    		
    		if((anychat.GetUserName(userID[i])).startsWith("admin")){
    		userList.add(anychat.GetUserName(userID[i]));
    		}
    	}
    	userListAdapter.notifyDataSetChanged();
        super.onResume();
    }
    
	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.e("********RoomActivity*********", "OnAnyChatEnterRoomMessage");	

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		Log.e("********RoomActivity*********", "OnAnyChatOnlineUserMessage   "+dwUserNum);	

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		if(bEnter)
		{
			if((anychat.GetUserName(dwUserId)).startsWith("admin")){
				
				
				idList.add(""+dwUserId);
	    		userList.add(anychat.GetUserName(dwUserId));
	    		userListAdapter.notifyDataSetChanged();
			}
			

		}
		else
		{
			for(int i=0;i<idList.size();i++)
			{
				if(idList.get(i).equals(""+dwUserId))
				{
					try {
						idList.remove(i);
						userList.remove(i);
						userListAdapter.notifyDataSetChanged();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	/**
	 * 需修改
	 */
	@Override
	public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid,
			boolean bSecret, String message) {
		if((anychat.GetUserName(dwFromUserid)).startsWith("admin")){
		
		messageList.add(anychat.GetUserName(dwFromUserid)+"说: "+message);
		//messageListView.setStackFromBottom(true);
		messageListView.SetFileList(messageList);
		messageListView.setSelection(messageListView.getAdapter().getCount()-1);
		//messageListView.scrollTo(0, Integer.MAX_VALUE);
		
		}
	}

}

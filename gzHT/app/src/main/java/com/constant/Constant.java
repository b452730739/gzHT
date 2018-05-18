package com.constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import com.elegps.gz_customerservice.R;
import com.elegps.javabean.Buy_fitingOne;
import com.elegps.javabean.Buy_lishiBean;
import com.elegps.javabean.Get_Error;
import com.elegps.javabean.Note_paiGBean;
import com.elegps.javabean.Video_users;

public class Constant {
public static final Integer [] pageID = {R.drawable.one,R.drawable.two,
	R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six
	,R.drawable.seven
}; 															//前面的viewpager的图片

public static Integer screenWidth = 0; 						//屏幕的宽
public static Integer screenHeight = 0;						//高

public static final Integer [] MainPage_Computer = {
	R.drawable.zhuye_fitting,R.drawable.zhuye_photo,R.drawable.zhuye_location,
	R.drawable.zhuye_error,R.drawable.zhuye_vedio,R.drawable.zhuye_note,};
															//主页面的图片
public static File User_Path = null; 						//个人文件的路径
public static String jietuPath = null; 						//截取地图的临时文件
public static String photoPath = null;						//照相存取的临时文件
public static final String photoName = "photo.png";       	//照相存取的临时文件名
public static final String ServerIP = "183.63.165.198";  	//服务器的IP
public static final int ServerPort = 8906/*8906*/;         	//服务器的端口号
public static String UserName = "";          				//用户名
public static String TableName = "";        			 	//数据库名
public static String ISINSIDE = null;         				//值为1为内部账号，否则外部账号
public static String BUY_TEMP  = null;						//存储的购买时的临时变量
public static List<Get_Error> errors = null; 				//存储的一个月之内的故障信息
public static List<Get_Error> errors_find = null; 				//存储的查询的故障信息

public static Buy_fitingOne buy_fitingOnes = null;			//购买页面的父一级目录
public static List<Buy_fitingOne> ones = null;				//购买的顶级目录//58.67.147.209
public static List<Video_users> users = null;            	//获取登录人的个人信息

public static String DENGLUNAME = null;						//登录的真实名字
public static ArrayList<Note_paiGBean> paiGBeans = null;	//存储的派工信息
public static String Buy_PeiJian = null;					//存储的配件信息字符串
public static List<Buy_lishiBean> buy_lishiBeans = null;	//存储的购
public static boolean b = true;								//判断故障详细信息跳转到哪个页面


//2018-4-29
	public static final String APPUSERINFO = "AppUserInfo";       	//用户信息
	public static final String TASKINFO = "TaskInfo";
	public static final String TASK_DAIBAN_INFO = "TaskDaibanInfo";

	public static final String MACHINESTOCK = "MachineStock";
	public static final String IS_MY_HOURS = "isMyHours";

}

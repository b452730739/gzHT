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
}; 															//ǰ���viewpager��ͼƬ

public static Integer screenWidth = 0; 						//��Ļ�Ŀ�
public static Integer screenHeight = 0;						//��

public static final Integer [] MainPage_Computer = {
	R.drawable.zhuye_fitting,R.drawable.zhuye_photo,R.drawable.zhuye_location,
	R.drawable.zhuye_error,R.drawable.zhuye_vedio,R.drawable.zhuye_note,};
															//��ҳ���ͼƬ
public static File User_Path = null; 						//�����ļ���·��
public static String jietuPath = null; 						//��ȡ��ͼ����ʱ�ļ�
public static String photoPath = null;						//�����ȡ����ʱ�ļ�
public static final String photoName = "photo.png";       	//�����ȡ����ʱ�ļ���
public static final String ServerIP = "183.63.165.198";  	//��������IP
public static final int ServerPort = 8906/*8906*/;         	//�������Ķ˿ں�
public static String UserName = "";          				//�û���
public static String TableName = "";        			 	//���ݿ���
public static String ISINSIDE = null;         				//ֵΪ1Ϊ�ڲ��˺ţ������ⲿ�˺�
public static String BUY_TEMP  = null;						//�洢�Ĺ���ʱ����ʱ����
public static List<Get_Error> errors = null; 				//�洢��һ����֮�ڵĹ�����Ϣ
public static List<Get_Error> errors_find = null; 				//�洢�Ĳ�ѯ�Ĺ�����Ϣ

public static Buy_fitingOne buy_fitingOnes = null;			//����ҳ��ĸ�һ��Ŀ¼
public static List<Buy_fitingOne> ones = null;				//����Ķ���Ŀ¼//58.67.147.209
public static List<Video_users> users = null;            	//��ȡ��¼�˵ĸ�����Ϣ

public static String DENGLUNAME = null;						//��¼����ʵ����
public static ArrayList<Note_paiGBean> paiGBeans = null;	//�洢���ɹ���Ϣ
public static String Buy_PeiJian = null;					//�洢�������Ϣ�ַ���
public static List<Buy_lishiBean> buy_lishiBeans = null;	//�洢�Ĺ�
public static boolean b = true;								//�жϹ�����ϸ��Ϣ��ת���ĸ�ҳ��
}

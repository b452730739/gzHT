package com.constant;

public class IP_Address {

	/**
	 * �������Ի���
	 */
	public static final String IP = "14.23.94.10:5001";
	private static final String PROJECT_NAME="/HTAndroidWebService/HTAndroidWebService";
	/**
	 * ��ʽ����
	 */
//	public static final String IP = "183.63.165.198";
//	private static final String PROJECT_NAME="/HTAndroidWebService";

	/**
	 * apk���µ�ַ
	 * 183.63.165.198��������
	 * ע���ļ����ص�IP����Ƶͨ����IP
	 * http://183.63.165.198/HTAndroidWebService/version.xml
	 */


	/**
	 * �����ķ���
	 */
	public static final String AppMemberService = "http://"+IP+PROJECT_NAME+"/AppMemberService.asmx";

public static final String UPDATE = "http://"+IP+PROJECT_NAME+"/version.xml";
	/**
	 * �����ռ�
	 */
public static final String NAMESPACE = "http://HTAftersales.com/";
	/**
	 * �˺Ź���
	 */
public static final String MEMBERSERVICE = "http://"+IP+PROJECT_NAME+"/MemberService.asmx";
	/**
	 * ���ϲ�ѯ
	 */
public static final String WORKORDERSERVICE = "http://"+IP+PROJECT_NAME+"/WorkOrderService.asmx";
	/**
	 * �������
	 */
public static final String PRODUCTSERVICE = "http://"+IP+PROJECT_NAME+"/ProductService.asmx";
	/**
	 * ͼƬ�ϴ�
	 */
public static final String PICTURESERVICE = "http://"+IP+PROJECT_NAME+"/PictureService.asmx";
	/**
	 * ��־�������
	 */
public static final String LOGSERVICE = "http://"+IP+PROJECT_NAME+"/LogService.asmx";
	/**
	 * Ͷ�߽���
	 */
public static final String MAINTAINADVISESERVICE = "http://"+IP+PROJECT_NAME+"/MaintainAdviseService.asmx";
}

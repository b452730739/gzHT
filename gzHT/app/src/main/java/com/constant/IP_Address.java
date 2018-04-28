package com.constant;

public class IP_Address {

	/**
	 * 外网测试环境
	 */
	public static final String IP = "14.23.94.10:5001";
	private static final String PROJECT_NAME="/HTAndroidWebService/HTAndroidWebService";
	/**
	 * 正式环境
	 */
//	public static final String IP = "183.63.165.198";
//	private static final String PROJECT_NAME="/HTAndroidWebService";

	/**
	 * apk更新地址
	 * 183.63.165.198广州外网
	 * 注意文件下载的IP和视频通话的IP
	 * http://183.63.165.198/HTAndroidWebService/version.xml
	 */


	/**
	 * 新增的服务
	 */
	public static final String AppMemberService = "http://"+IP+PROJECT_NAME+"/AppMemberService.asmx";

public static final String UPDATE = "http://"+IP+PROJECT_NAME+"/version.xml";
	/**
	 * 命名空间
	 */
public static final String NAMESPACE = "http://HTAftersales.com/";
	/**
	 * 账号管理
	 */
public static final String MEMBERSERVICE = "http://"+IP+PROJECT_NAME+"/MemberService.asmx";
	/**
	 * 故障查询
	 */
public static final String WORKORDERSERVICE = "http://"+IP+PROJECT_NAME+"/WorkOrderService.asmx";
	/**
	 * 配件购买
	 */
public static final String PRODUCTSERVICE = "http://"+IP+PROJECT_NAME+"/ProductService.asmx";
	/**
	 * 图片上传
	 */
public static final String PICTURESERVICE = "http://"+IP+PROJECT_NAME+"/PictureService.asmx";
	/**
	 * 日志操作相关
	 */
public static final String LOGSERVICE = "http://"+IP+PROJECT_NAME+"/LogService.asmx";
	/**
	 * 投诉建议
	 */
public static final String MAINTAINADVISESERVICE = "http://"+IP+PROJECT_NAME+"/MaintainAdviseService.asmx";
}

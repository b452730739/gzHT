package com.elegps.antkingXML;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.elegps.javabean.GetComplainAdviseInfo;

public class Complaints_proposals {
	
	/**
	 * 从xml文件中读取数据,读取投诉建议内容
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  List<GetComplainAdviseInfo> getcomplainadviseinfo(String str) throws Exception {
		
		List<GetComplainAdviseInfo> video_users = null;
		GetComplainAdviseInfo users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<GetComplainAdviseInfo>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("Row".equals(parser.getName())){
					
					users = new GetComplainAdviseInfo();
					
				}else if ("Content".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setContent(" ");
					}else
					users.setContent(parser.getText());
				}else if ("Date".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setDate(" ");
					}else
					users.setDate(parser.getText());
				}else if ("MachineNO".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setMachineNO(" ");
					}else
					users.setMachineNO(parser.getText());
				}else if ("MachineModel".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setMachineModel(" ");
					}else
					users.setMachineModel(parser.getText());
				}else if ("ProductDate".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setProductDate(" ");
					}else
					users.setProductDate(parser.getText());
				}else if ("UUNO".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setUUNO(" ");
					}else
					users.setUUNO(parser.getText());
				}else if ("GUID".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setGUID(" ");
					}else
					users.setGUID(parser.getText());
				}
				break;

			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {

					video_users.add(users);
					users = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return video_users;
	}
	/**
	 * 从xml文件中读取数据,读取报修保养
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  List<GetComplainAdviseInfo> getbaoxiu_baoyang(String str) throws Exception {
		
		List<GetComplainAdviseInfo> video_users = null;
		GetComplainAdviseInfo users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<GetComplainAdviseInfo>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("RepairsMaintains".equals(parser.getName())){
					
					users = new GetComplainAdviseInfo();
					
				}else if ("Content".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setContent(" ");
					}else
					users.setContent(parser.getText());
				}else if ("Date".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
						users.setDate(" ");
					}else
					users.setDate(parser.getText());
				}
				break;

			case XmlPullParser.END_TAG:

				
				if (parser.getName().equals("RepairsMaintains")) {

					video_users.add(users);
					users = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return video_users;
	}
}

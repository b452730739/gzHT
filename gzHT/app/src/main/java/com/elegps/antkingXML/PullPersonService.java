package com.elegps.antkingXML;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.elegps.javabean.Buy_fitingOne;
import com.elegps.javabean.Buy_lishiBean;
import com.elegps.javabean.Creat_Person;
import com.elegps.javabean.Error_info;
import com.elegps.javabean.GetBuyMessage;
import com.elegps.javabean.Get_Error;
import com.elegps.javabean.Get_ErrorXiangXI;
import com.elegps.javabean.Note_PaiGongbean;
import com.elegps.javabean.Note_PingTaiBean;
import com.elegps.javabean.Note_paiGBean;
import com.elegps.javabean.Update_bean;
import com.elegps.javabean.Video_users;

/**
 * 解析XML
 * @author lichendong
 *
 */
public class PullPersonService {
	public static String PERSON = "person";
	public static String NAME = "name";
	public static String AGE = "age";
	public static String PERSONS = "person";
	/**
	 * 从url获取的apk更新的信息
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  List<Update_bean> update(String str) throws Exception {
		
		List<Update_bean> video_users = null;
		Update_bean users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<Update_bean>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("update".equals(parser.getName())){
					
					users = new Update_bean();
					
				}else if ("version".equals(parser.getName())) {
					eventCode = parser.next();
					users.setVersion(parser.getText());
				}else if ("name".equals(parser.getName())) {
					eventCode = parser.next();
					users.setName(parser.getText());
				}else if ("url".equals(parser.getName())) {
					eventCode = parser.next();
					users.setUrl(parser.getText());
				}
				break;

			case XmlPullParser.END_TAG:

				
				if (parser.getName().equals("update")) {

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
	 * 从xml文件中读取数据,判断是否为内部账号1为内部账号
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  List<GetBuyMessage> GetBuyMessage(String str) throws Exception {
		
		List<GetBuyMessage> video_users = null;
		GetBuyMessage users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<GetBuyMessage>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("Row".equals(parser.getName())){
					
					users = new GetBuyMessage();
					
				}else if ("Title".equals(parser.getName())) {
					eventCode = parser.next();
					users.setTitle(parser.getText());
				}else if ("Date".equals(parser.getName())) {
					eventCode = parser.next();
					users.setDate(parser.getText());
				}else if ("UserName".equals(parser.getName())) {
					eventCode = parser.next();
					users.setUserName(parser.getText());
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
	 * 从xml文件中读取数据,判断是否为内部账号1为内部账号
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public  List<Note_PingTaiBean> Note_PingTai(String str) throws Exception {
		
		List<Note_PingTaiBean> video_users = null;
		Note_PingTaiBean users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<Note_PingTaiBean>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("Row".equals(parser.getName())){
					
					users = new Note_PingTaiBean();
					
				}else if ("Title".equals(parser.getName())) {
					eventCode = parser.next();
					users.setTitle(parser.getText());
				}else if ("Date".equals(parser.getName())) {
					eventCode = parser.next();
					users.setTime(parser.getText());
				}else if ("FileInfo".equals(parser.getName())) {
					eventCode = parser.next();
					users.setFileInfo(parser.getText());
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
	 * 从xml文件中读取数据,判断是否为内部账号1为内部账号
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Video_users> pullReadXml(String str) throws Exception {
		
		List<Video_users> video_users = null;
		Video_users users = null;
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				
				video_users = new ArrayList<Video_users>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if("person".equals(parser.getName())){
					
					users = new Video_users();
				}else if ("AcctType".equals(parser.getName())) {
					eventCode = parser.next();
					users.setAcctType(parser.getText());
				}else if ("Contact".equals(parser.getName())) {
					eventCode = parser.next();
					users.setContact(parser.getText());
				}else if ("Mobile".equals(parser.getName())) {
					eventCode = parser.next();
					//users.setMobile(parser.getText());
					if(parser.getText() == null){
						users.setMobile(" ");
						}else
						users.setMobile(parser.getText());
				}else if ("EmplNO".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
					users.setEmplNO(" ");
					}else
					users.setEmplNO(parser.getText());
				}else if ("CustName".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
					users.setCustName(" ");
					}else
					users.setCustName(parser.getText());
				}else if ("Email".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
					users.setEmail(" ");
					}else
					users.setEmail(parser.getText());
				}else if ("Fax".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
					users.setFax(" ");
					}else
					users.setFax(parser.getText());
				}else if ("CustAddr".equals(parser.getName())) {
					eventCode = parser.next();
					if(parser.getText() == null){
					users.setCustAddr(" ");
					}else
					users.setCustAddr(parser.getText());
				}
				break;
				
			case XmlPullParser.END_TAG:

				if (parser.getName().equals("person")) {

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
	 * 从xml文件中读取数据,派工信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Buy_lishiBean> buy_lishi(String str) throws Exception {
		List<Buy_lishiBean> Buy_List = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Buy_lishiBean buy_fitingOne = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				Buy_List = new ArrayList<Buy_lishiBean>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("Row".equals(parser.getName())) {

					buy_fitingOne = new Buy_lishiBean();

				} else if ("OrderDate".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setOrderDate(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("BuyInfo".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setBuyInfo(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if ("ReturnInfo".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setReturnInfo(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("OrderNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setOrderNO(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("BuyFlag".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setBuyFlag(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("MachineNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setMachineNO(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("MateName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setMateName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("Row")) {

					Buy_List.add(buy_fitingOne);
					buy_fitingOne = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return Buy_List;
	}
	/**
	 * 从xml文件中读取数据,派工详细信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Note_PaiGongbean> note_paigong(String str) throws Exception {
		List<Note_PaiGongbean> Buy_List = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Note_PaiGongbean buy_fitingOne = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				Buy_List = new ArrayList<Note_PaiGongbean>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("PGInfo".equals(parser.getName())) {

					buy_fitingOne = new Note_PaiGongbean();

				} else if ("GDNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setGDNO(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Date".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setDate(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("CreateUser".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setCreateUser(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("TaskType".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTaskType(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("CustName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setCustName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Contact".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setContact(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("CustAddr".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setCustAddr(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Mobile".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setMobile(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Tel".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTel(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("ReceiveUserName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setReceiveUserName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("DeptName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setDeptName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("ProductType".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setProductType(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("ProductName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setProductName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Content".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setContent(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Remark".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setRemark(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("PGUserName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setPGUserName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("PGDate".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setPGDate(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("PGContent".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setPGContent(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("PGInfo")) {

					Buy_List.add(buy_fitingOne);
					buy_fitingOne = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return Buy_List;
	}
	/**
	 * 从xml文件中读取数据,派工信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Note_paiGBean> note_paig(String str) throws Exception {
		List<Note_paiGBean> Buy_List = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Note_paiGBean buy_fitingOne = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				Buy_List = new ArrayList<Note_paiGBean>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("Row".equals(parser.getName())) {

					buy_fitingOne = new Note_paiGBean();

				} else if ("Content".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setPGInfos(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Date".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setDate(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if ("GDNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setGDNO(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("PGNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setPGNO(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("IsFinish".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setIsFinish(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("Row")) {

					Buy_List.add(buy_fitingOne);
					buy_fitingOne = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			try {
				eventCode = parser.next();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Buy_List;
	}
	/**
	 * 从xml文件中读取数据,配件购买的子级目录的配件信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Buy_fitingOne> getBuy_Info(String str) throws Exception {
		List<Buy_fitingOne> Buy_List = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Buy_fitingOne buy_fitingOne = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				Buy_List = new ArrayList<Buy_fitingOne>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("Row".equals(parser.getName())) {

					buy_fitingOne = new Buy_fitingOne();

				} else if ("ProductID".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTypeID(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("ProductName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTypeName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("Row")) {

					Buy_List.add(buy_fitingOne);
					buy_fitingOne = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return Buy_List;
	}

	/**
	 * 从xml文件中读取数据,配件购买的一级目录
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Buy_fitingOne> getBuy_List(String str) throws Exception {
		List<Buy_fitingOne> Buy_List = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Buy_fitingOne buy_fitingOne = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				Buy_List = new ArrayList<Buy_fitingOne>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("Row".equals(parser.getName())) {

					buy_fitingOne = new Buy_fitingOne();

				} else if ("TypeID".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTypeID(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("TypeName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						buy_fitingOne.setTypeName(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("Row")) {

					Buy_List.add(buy_fitingOne);
					buy_fitingOne = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return Buy_List;
	}
	/**
	 * 从xml文件中读取数据,故障查询
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Get_Error> Get_Errors(String str) throws Exception {
		List<Get_Error> personList = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Get_Error get_Error = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				personList = new ArrayList<Get_Error>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("Row".equals(parser.getName())) {

					get_Error = new Get_Error();

				} else if ("GDNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setGDNO(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Date".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setDate(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("Content".equals(parser.getName())) {
					try {

						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setContent(" ");

						} else {
							get_Error.setContent(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("Flag".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setFlag(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("Row")) {

					personList.add(get_Error);
					get_Error = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return personList;
	}
	/**
	 * 从xml文件中读取数据,故障查询
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Get_ErrorXiangXI> pullReadXml1(String str) throws Exception {
		List<Get_ErrorXiangXI> personList = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Get_ErrorXiangXI get_Error = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				personList = new ArrayList<Get_ErrorXiangXI>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("WorkOrder".equals(parser.getName())) {

					get_Error = new Get_ErrorXiangXI();
					
				} else if ("GDNO".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setGDNO(parser.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("Date".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setDate(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("Content".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setContent(" ");

						} else {
							get_Error.setContent(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("Flag".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						get_Error.setFlag(parser.getText());

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Operate".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setOperate(" ");
						} else {
							get_Error.setOperate(parser.getText());
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("PGUserName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setPGUserName(" ");

						} else {
							get_Error.setPGUserName(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Mobile".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setCMobile(" ");

						} else {
							get_Error.setCMobile(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("CustName".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setCustName(" ");

						} else {
							get_Error.setCustName(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("CustAddr".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setCustAddr(" ");

						} else {
							get_Error.setCustAddr(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Contact".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setContact(" ");

						} else {
							get_Error.setContact(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("CMobile".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setCMobile(" ");

						} else {
							get_Error.setCMobile(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Tel".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setTel(" ");

						} else {
							get_Error.setTel(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Fax".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setFax(" ");

						} else {
							get_Error.setFax(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Email".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						
						if (parser.getText() == null) {
							get_Error.setEmail(" ");

						} else {
							get_Error.setEmail(parser.getText());
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:

				if (parser.getName().equals("WorkOrder")) {

					personList.add(get_Error);
					get_Error = null;
				}

				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return personList;
	}
	/**
	 * 从xml文件中读取数据,故障查询的详细信息
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static List<Error_info> pullReadXml11(String str) throws Exception {
		List<Error_info> personList = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(str));
		Error_info get_Error = null;

		int eventCode = parser.getEventType();
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT:
				personList = new ArrayList<Error_info>();
				break;
			case XmlPullParser.START_TAG:
				// 判断开始标签元素是否是person
				if ("SendGoods".equals(parser.getName())) {

					get_Error = new Error_info();

				} else if ("OrderNo".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if(parser.getText() == null){
							get_Error.setFahuodan(" ");

						}else{
						get_Error.setFahuodan(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if ("SendDate".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if(parser.getText() == null){
							get_Error.setFahuoriqi(" ");

						}else{
						get_Error.setFahuoriqi(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("ReceiverMan".equals(parser.getName())) {
					try {

						eventCode = parser.next();
						if (parser.getText() == null) {
							get_Error.setFahuoren(" ");

						} else {
							get_Error.setFahuoren(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if ("ContractWay".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if(parser.getText() == null){
							get_Error.setLianxifangshi(" ");

						}else{
						get_Error.setLianxifangshi(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if ("Remark".equals(parser.getName())) {
					try {
						eventCode = parser.next();
						if(parser.getText() == null){
						get_Error.setBeizhu(" ");
						}else{
							get_Error.setBeizhu(parser.getText());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				break;

			case XmlPullParser.END_TAG:
				
				if (parser.getName().equals("SendGoods")) {
					
					personList.add(get_Error);
					get_Error = null;
				}
				break;
			}
			// 进入下一个元素并触发相应事件
			eventCode = parser.next();
		}
		return personList;
	}
	// 将数据写入xml文件
	public static String writeToXml(List<Creat_Person> personList)
			throws Exception {
		XmlSerializer serializer = Xml.newSerializer();

		StringWriter stringWriter = new StringWriter();
		serializer.setOutput(stringWriter);
		serializer.startDocument("UTF-8", true);
		serializer.startTag(null, PERSONS);

		for (Creat_Person person : personList) {

			serializer.startTag(null, "et_name");
			serializer.text(person.getEt_name());
			serializer.endTag(null, "et_name");

			serializer.startTag(null, "et_address");
			serializer.text(person.getEt_address());
			serializer.endTag(null, "et_address");

			serializer.startTag(null, "et_zhizhao");
			serializer.text(person.getEt_zhizhao());
			serializer.endTag(null, "et_zhizhao");

			serializer.startTag(null, "et_swollen");
			serializer.text(person.getEt_swollen());
			serializer.endTag(null, "et_swollen");

			serializer.startTag(null, "et_password");
			serializer.text(person.getEt_password());
			serializer.endTag(null, "et_password");

			serializer.startTag(null, "et_confirm_password");
			serializer.text(person.getEt_confirm_password());
			serializer.endTag(null, "et_confirm_password");

			serializer.startTag(null, "postbox");
			serializer.text(person.getPostbox());
			serializer.endTag(null, "postbox");

			serializer.startTag(null, "fax");
			serializer.text(person.getFax());
			serializer.endTag(null, "fax");

			serializer.startTag(null, "zijin");
			serializer.text(person.getZijin());
			serializer.endTag(null, "zijin");
			serializer.startTag(null, "guoshui");
			serializer.text(person.getGuoshui());
			serializer.endTag(null, "guoshui");	
			serializer.startTag(null, "dishui");
			serializer.text(person.getDishui());
			serializer.endTag(null, "dishui");	
			serializer.startTag(null, "daima");
			serializer.text(person.getDaima());
			serializer.endTag(null, "daima");	
			serializer.startTag(null, "sex");
			serializer.text(person.getSex());
			serializer.endTag(null, "sex");
			serializer.startTag(null, "bumen");
			serializer.text(person.getBumen());
			serializer.endTag(null, "bumen");	
			serializer.startTag(null, "bumen_zhiwu");
			serializer.text(person.getBumen_zhiwu());
			serializer.endTag(null, "bumen_zhiwu");
			serializer.startTag(null, "et_mobile");
			serializer.text(person.getEt_phone());
			serializer.endTag(null, "et_mobile");
			serializer.startTag(null, "et_tel");
			serializer.text(person.getEt_tel());
			serializer.endTag(null, "et_tel");
			
			serializer.startTag(null, "et_contactname");
			serializer.text(person.getEt_contactname());
			serializer.endTag(null, "et_contactname");
		}
		serializer.endTag(null, PERSONS);
		serializer.endDocument();

		return stringWriter.toString();
	}
}

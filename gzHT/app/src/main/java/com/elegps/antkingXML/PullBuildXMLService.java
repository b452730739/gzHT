package com.elegps.antkingXML;

import java.io.StringWriter;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.elegps.javabean.Buy_enrolment;
import com.elegps.javabean.Creat_Person;
import com.elegps.javabean.Find_password;


/**采用pull 生成xml文件
 * 
 * @author 
 *
 */
public class PullBuildXMLService {
	
	
	/**
	 * 登记配件购买信息表
	 * @param persons
	 * @return
	 * @throws Exception
	 */
	public StringWriter build_BuyXML(List<Buy_enrolment> produces)throws Exception{
		StringWriter stringWriter = new StringWriter();
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(stringWriter);
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "produces");
		for(Buy_enrolment produce:produces){
			serializer.startTag(null, "produce");
			
			serializer.startTag(null, "TypeName");
			serializer.text(produce.getTypeName());
			serializer.endTag(null, "TypeName");
						
			serializer.startTag(null, "TypeNum");
			serializer.text(produce.getTypeNum());
			serializer.endTag(null, "TypeNum");
		
			serializer.startTag(null, "Type");
			serializer.text(produce.getType());
			serializer.endTag(null, "Type");
			
			serializer.startTag(null, "TypeId");
			serializer.text(produce.getTypeId());
			serializer.endTag(null, "TypeId");
			
			serializer.startTag(null, "ID");
			
			serializer.text(produce.getID());
			serializer.endTag(null, "ID");
			
			serializer.endTag(null, "produce");
		}
		serializer.endTag(null, "produces");
		serializer.endDocument();
		return stringWriter;
	}
	
	public StringWriter getFind_passwordXML(List<Find_password> find_password) throws Exception{
		StringWriter stringWriter = new StringWriter();
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(stringWriter);
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "produces");
		for(Find_password find_passwords:find_password){
			
			serializer.startTag(null, "find_name");
			serializer.text(find_passwords.getFind_name());
			serializer.endTag(null, "find_name");
						
			serializer.startTag(null, "find_contactname");
			serializer.text(find_passwords.getFind_contactname());
			serializer.endTag(null, "find_contactname");
			
			serializer.startTag(null, "find_phone");
			serializer.text(find_passwords.getFind_phone());
			serializer.endTag(null, "find_phone");
			
			serializer.startTag(null, "find_swollen");
			serializer.text(find_passwords.getFind_swollen());
			serializer.endTag(null, "find_swollen");
			
		}
		serializer.endTag(null, "produces");
		serializer.endDocument();
		return stringWriter;
		
	}
}

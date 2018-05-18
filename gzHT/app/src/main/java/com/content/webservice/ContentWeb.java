package com.content.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.constant.IP_Address;
import com.elegps.UIManager.Dialog_UI;

public class ContentWeb{
	
	public static ContentWeb getinstance(){
		
		ContentWeb contentWeb = null ;
		if(contentWeb == null){
			
			contentWeb = new ContentWeb();
		}
		return contentWeb;
	}


	
	public String contetweb(String url,String methodName,String[] KEY,
			String[] value,Dialog_UI dialog_UI)
					{
		
			SoapPrimitive result = null;
		
    		SoapObject rpc = new SoapObject(IP_Address.NAMESPACE, methodName);
    		
    		try {
				for(int i = 0 ; i < KEY.length;i++){
					
					rpc.addProperty(KEY[i], value[i]);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    		try {	
			HttpTransportSE ht = new HttpTransportSE(url,3000);
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			
			envelope.dotNet = true; // 表示不用rpc方式

			envelope.setOutputSoapObject(rpc);
			
			envelope.bodyOut = rpc;
			
			ht.call(IP_Address.NAMESPACE + methodName, envelope);
			result = (SoapPrimitive) envelope.getResponse();
			
		}catch (XmlPullParserException e) {
			e.printStackTrace();
			System.out.println("XmlPullParserException连接异常...");
	
		} catch (Exception e) {
			e.printStackTrace();
		/*	try {
				dialog_UI.dismiss();
			} catch (Exception e1) {
				e1.printStackTrace();
			}*/
			
			System.out.println("WebService连接异常...");
			return /*"网络异常..."*/null;
		}
		return result.toString();
	}
	
}

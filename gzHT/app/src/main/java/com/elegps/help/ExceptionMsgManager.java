package com.elegps.help;

import org.apache.http.HttpException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class ExceptionMsgManager {


    public static String onExceptionToMsg(Throwable e) {
        if (e instanceof ConnectException) {
            return "网络连接失败，请检查网络设置";
        } else if (e instanceof SocketTimeoutException) {
            return "网络连接超时，请稍后重试";
        }else if (e instanceof IOException) {
            return "解析数据失败，请稍后重试";
        }else if (e instanceof SQLException) {
            return "获取数据失败，请稍后重试";
        }else if (e instanceof HttpException) {
            return "服务器出错了，请稍后重试";
        }else if (e instanceof XmlPullParserException) {
            return "解析XML数据失败，请稍后重试";
        }else if (e instanceof UnknownHostException) {
            return "找不到服务器了，请检查网络是否连接或稍后重试";
        }else if (e instanceof Exception) {
            return "服务器报错了,请稍后重试";
        }
        return "";
    }

}
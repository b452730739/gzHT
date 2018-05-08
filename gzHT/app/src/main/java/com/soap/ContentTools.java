package com.lcd.elegps.soap;

import android.support.annotation.Nullable;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/10.
 */

public class ContentTools {

    private static String TAG = "ContentTools";

    public static void getResult(String service,String nameSpace,String[] values,StackTraceElement[] stackTraceElements,Class classz,Subscriber<String> s){
        ContentWeb.getResult(service,getMethodName(stackTraceElements), getMethodParameterNames(getMethodName(stackTraceElements),classz,values.length), values, nameSpace,s);
    }

    public static String getMethodName(StackTraceElement[] stackTraceElements){
        return stackTraceElements[2].getMethodName();
    }
    @Nullable
    public static String[] getMethodParameterNames(String methodName, Class classz, int length) {

        int len = 1;//由于最后一个不为后台参数名,所以去掉
        Method method = null;
        Class[] classes = new Class[length+len];

        for (int i = 0 ;i < classes.length ;i++){
            if(i < (classes.length-len)){
                classes[i] = String.class;
            }else{
                classes[i] = Subscriber.class;
            }
        }
        try {
            method = classz.getDeclaredMethod(methodName, classes);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{};
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        String[] parameterNames = new String[parameterAnnotations.length-len];
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Param) {
                    Param param = (Param) annotation;
                    parameterNames[i++] = param.value();
                }
            }
        }

        return parameterNames;
    }
}

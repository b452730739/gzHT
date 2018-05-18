package com.soap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ContentWeb {

    private static int TIME_OUT = 1000 * 5;

    public static String contetweb(String url, String methodName, String[] key,  String[] value, String nameSpace) throws IOException, XmlPullParserException {

        SoapPrimitive result = null;
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        if(key!=null){
            for (int i = 0; i < key.length; i++) {
                rpc.addProperty(key[i], value[i]);
            }
        }

        HttpTransportSE ht = new HttpTransportSE(url, TIME_OUT);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true; // 表示不用rpc方式
        envelope.setOutputSoapObject(rpc);
        envelope.bodyOut = rpc;
        ht.call(nameSpace + methodName, envelope);
        result = (SoapPrimitive) envelope.getResponse();

        return result.toString();
    }

    public static void getResult(final String url, final String methodName, final String[] key, final String[] value, final String nameSpace, Subscriber<String> s) {

        Observable o = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String result = "";
                try {
                    result = contetweb(url, methodName, key, value, nameSpace);
                } catch (IOException e) {
                    subscriber.onError(e);
                } catch (XmlPullParserException e) {
                    subscriber.onError(e);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
        toSubscribe(o, s);
    }

    public static void toSubscribe(Observable o, Subscriber s) {
        o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s);

    }
}
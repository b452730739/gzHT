package com.lcd.shipping.dh.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class ParseJsonTools {
    private static String TAG = "ParseJsonTools";



    public static <T> List<T> fromJsonArray(String json, Class<T> clazz)  {
        List<T> lst =  new ArrayList<T>();


        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter());
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        Gson gson = builder.create();
        for(final JsonElement elem : array){
            lst.add(gson.fromJson(elem, clazz));
        }

        return lst;
    }

}

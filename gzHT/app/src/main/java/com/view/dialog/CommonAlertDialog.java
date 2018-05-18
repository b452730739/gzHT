package com.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Liqinging on 2016/9/23.
 * Email:Liqingqingqingwen@gemail.com
 */
public class CommonAlertDialog {


    public static void  show(Context context, String title, String text, DialogInterface.OnClickListener positiveListener , DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setPositiveButton("确认", positiveListener);
        builder.setNegativeButton("取消", negativeListener);
        builder.show();
    }
    public static void  showUpdatedialog(Context context, String title, String text, DialogInterface.OnClickListener positiveListener , DialogInterface.OnClickListener negativeListener, int isForce){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setPositiveButton("确认", positiveListener);

        if(isForce != 1){
            builder.setNegativeButton("取消", negativeListener);
        }
        builder.show();



    }

}

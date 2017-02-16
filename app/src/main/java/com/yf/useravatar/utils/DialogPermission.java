package com.yf.useravatar.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

/**
 * 权限提示对话框
 * Created by yf on 2016/7/25.
 */
public class DialogPermission {
    Context mContext;
    String mNotice;
    public DialogPermission(Context context, String notice){
        mContext=context;
        mNotice=notice;
        showDialog();
    }

    private void showDialog() {
        new AlertDialog.Builder(mContext).setTitle("系统提示")//设置对话框标题

                .setMessage(mNotice)//设置显示的内容

                .setPositiveButton("设置",new DialogInterface.OnClickListener() {//添加确定按钮

                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
                        mContext.startActivity(intent);
                    }

                }).setNegativeButton("放弃",new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件

                    Log.i("DialogPermission","Dialog关闭");
            }
        }).show();//在按键响应事件中显示此对话框

    }

}

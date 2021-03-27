package com.example.lenovo.jiyiji.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;
    public static void showMsg(Context mContext,String msg){
        if(mToast==null){
            mToast=Toast.makeText(mContext,msg,Toast.LENGTH_LONG);
        }else{
            mToast.setText(msg);
        }
        mToast.show();

    }
}

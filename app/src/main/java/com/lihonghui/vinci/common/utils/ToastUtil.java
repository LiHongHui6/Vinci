package com.lihonghui.vinci.common.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by yq05481 on 2016/11/10.
 */

public class ToastUtil {
    public static int DURATION_LONG = 5000;
    public static int DURATION_SHORT = 3000;

    private static Toast toast = null;

    private static Handler mHandler = new Handler();

    private static Runnable r = new Runnable() {
        public void run() {
            if(toast!=null){
                toast.cancel();
                toast=null;//toast隐藏后，将其置为null
            }
        }
    };

    public static void showToast(Context context,String hint,int DURATION){
        if (toast == null){
            toast = Toast.makeText(context,hint,Toast.LENGTH_SHORT);
            mHandler.postDelayed(r, DURATION);
            toast.show();
        }else{
            toast.setText(hint);
        }
    }

}

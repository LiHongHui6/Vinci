package com.lihonghui.vinci.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yq05481 on 2016/11/29.
 */

public class SharePreferencesUtil {
    public static final String NAME_TOKEN = "nameToken";
    public static final String KEY_TOKEN = "keyToken";

    public static final String NAME_USER_INFO = "nameUserInfo";
    public static final String KEY_USER_INFO = "keyUserInfo";
    private Context mContext;
    private static SharePreferencesUtil mSelf;

    private SharePreferencesUtil(Context context) {
        this.mContext = context;
    }

    public static SharePreferencesUtil getIntance(Context context){
        if(mSelf == null){
            synchronized (SharePreferencesUtil.class){
                if(mSelf == null){
                    mSelf = new SharePreferencesUtil(context);
                }
            }
        }
        return mSelf;
    }

    public String getDataFronSharePreferences(String name, String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public void saveDataToSharePreferences(String name, String key,String value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();
    }
}

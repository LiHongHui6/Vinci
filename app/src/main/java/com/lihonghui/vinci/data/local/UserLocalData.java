package com.lihonghui.vinci.data.local;

import com.google.gson.Gson;
import com.lihonghui.vinci.App;
import com.lihonghui.vinci.common.utils.SharePreferencesUtil;
import com.lihonghui.vinci.data.Entity.User;

/**
 * Created by yq05481 on 2016/11/29.
 */

public class UserLocalData {
    public static User getUserInfo(){
        String userInfoStr = SharePreferencesUtil.getIntance(App.getContext()).getDataFronSharePreferences(SharePreferencesUtil.NAME_USER_INFO, SharePreferencesUtil.KEY_USER_INFO);
        Gson gson = new Gson();
        User user = gson.fromJson(userInfoStr, User.class);
        return user;
    }

    public static void saveUserInfo(User user){
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        SharePreferencesUtil.getIntance(App.getContext()).saveDataToSharePreferences(SharePreferencesUtil.NAME_USER_INFO,SharePreferencesUtil.KEY_USER_INFO,userStr);
    }
}

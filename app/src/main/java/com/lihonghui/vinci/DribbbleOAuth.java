package com.lihonghui.vinci;

import com.lihonghui.vinci.common.utils.SharePreferencesUtil;

/**
 * Created by yq05481 on 2016/10/27.
 */

public class DribbbleOAuth {
    public static final String Client_ID = "3be4eec7a5e84d6a07f60f73257c768eca6b4def61e7b61789467926fe206b87";
    public static final String Client_Secret = "1037f3f461cd2699b4ea939cf8fda11049894ccb8484ed8707aeeaf748459686";
    public static final String Default_Client_Access_Token = "ce02d370f81f9da88f3c2778ecf5ae4f98a9999188b0cf1fc1a1b94116f1ddce";
    public static final String Redirect_URL = "vinci://phone-callback";
    public static final String Scope_Parameter = "public+write+comment+upload";
    public static final String Login_Url = "https://dribbble.com/oauth/authorize?client_id=" + Client_ID + "&redirect_uri=" + Redirect_URL + "&scope=" + Scope_Parameter;
    public static  String Client_Access_Token = Default_Client_Access_Token;

    public static void saveToken(String token){
        Client_Access_Token = token;
        SharePreferencesUtil.getIntance(App.getContext()).saveDataToSharePreferences(SharePreferencesUtil.NAME_TOKEN,SharePreferencesUtil.KEY_TOKEN,token);
    }
    public static String getToken(){
        String token = SharePreferencesUtil.getIntance(App.getContext()).getDataFronSharePreferences(SharePreferencesUtil.NAME_TOKEN,SharePreferencesUtil.KEY_TOKEN);
        if("".equals(token)){
            Client_Access_Token = Default_Client_Access_Token;
        }else{
            Client_Access_Token = token;
        }

        return Client_Access_Token;
    }

    public static boolean isUserLogin(){
        Client_Access_Token = getToken();
        if(Default_Client_Access_Token.equals(Client_Access_Token)){
            return false;
        }else{
            return true;
        }
    }

    public static void logout(){
        saveToken("");
        Client_Access_Token = Default_Client_Access_Token;
    }
}

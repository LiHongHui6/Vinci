package com.lihonghui.vinci.user.login;

/**
 * Created by yq05481 on 2016/11/29.
 */

public interface LoginContact {
    interface View {
        void onLoginStart();
        void onLoginSuccess();
        void onNetworkException();
    }

    interface Presenter{
        /**
         * 根据scheme判断是Dribbble是否返回获取Token所需的Url
         * @param scheme
         * @return
         */
        boolean checkLoginExecute(String scheme);
        void getOAuthUserInfo(String url);
    }
}

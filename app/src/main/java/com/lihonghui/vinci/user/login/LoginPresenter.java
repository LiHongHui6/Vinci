package com.lihonghui.vinci.user.login;

import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.common.RxBus.RxBus;
import com.lihonghui.vinci.common.RxBus.event.UserLoginEvent;
import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.User;

import rx.Subscriber;

public class LoginPresenter implements LoginContact.Presenter {
    private LoginContact.View mView;
    private DataManager mDataManager;

    public LoginPresenter(LoginContact.View view) {
        this.mView = view;
        this.mDataManager = DataManager.getInstance();
    }

    @Override
    public boolean checkLoginExecute(String scheme) {
        return DribbbleOAuth.Redirect_URL.contains(scheme);
    }

    @Override
    public void getOAuthUserInfo(String url) {
        String requestCode = url.split("=")[1];
        //LogUtil.printLog("requestCode: "+requestCode);
        mDataManager.getOAuthUserInfo(requestCode,new Subscriber<User>() {
            @Override
            public void onCompleted() {
               // LogUtil.printLog("onCompleted "+getThreadInfo());
                mView.onLoginSuccess();
            }

            @Override
            public void onError(Throwable e) {
               // LogUtil.printLog("onStart "+getThreadInfo());
                mView.onNetworkException();
            }

            @Override
            public void onNext(User user) {
               // LogUtil.printLog("onNext user:"+user.getName()+" "+user.getUsername()+"  "+getThreadInfo());
                UserLoginEvent userLoginEvent = new UserLoginEvent(user);
                RxBus.getDefault().postSticky(userLoginEvent);

                mDataManager.saveOAuthUserInfo(user);
            }

            @Override
            public void onStart() {
               // LogUtil.printLog("onStart "+getThreadInfo());
                mView.onLoginStart();
            }
        });
    }

    public String getThreadInfo(){
        return Thread.currentThread().getName()+ " "+Thread.currentThread().getId();
    }

}
